import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.HTTPHeader;
import utils.MimeMapper;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;

/**
 * 处理HTTP请求的Runnable类.
 * <p>
 * 创建时间: 2021/5/11 15:43
 *
 * @author KevinHwang
 */
public class RequestProcessor implements Runnable {
    /**
     * 日志工具
     */
    private final static Logger logger = LogManager.getLogger("RequestProcessorLog");
    /**
     * 网站根目录
     */
    private final File rootDirectory;
    /**
     * 网站主页
     */
    private String indexFileName = "index.html";
    /**
     * 要处理的Socket
     */
    private final Socket connection;
    /**
     * 使用的MimeMapper
     */
    private final static MimeMapper MIME_MAPPER = MimeMapper.getMapper();

    /**
     * 构造方法.
     *
     * @param rootDirectory 网站根目录
     * @param indexFileName 网站主页文件名
     * @param connection    要处理的Socket
     */
    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }
        try {
            // 将 rootDirectory 转成绝对路径
            rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ignored) {
        }
        this.rootDirectory = rootDirectory;
        if (indexFileName != null) {
            this.indexFileName = indexFileName;
        }
        this.connection = connection;
    }

    @Override
    public void run() {
        //安全检查
        String root = rootDirectory.getPath();
        try {
            //底层输出流，用于输出二进制文件
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            //用于输出文本
            Writer out = new OutputStreamWriter(raw);
            //接收HTTP输入
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

            StringBuilder sb = new StringBuilder();
            //读请求头
            for (int read; (read = inputStream.read()) != -1; ) {
                sb.append((char) read);
                if (sb.toString().endsWith("\r\n\r\n")) {
                    break;
                }
            }
            //请求头
            HTTPHeader header = new HTTPHeader(sb.toString());
            //请求行
            String requestLine = header.getRequestLine();
            logger.info(connection.getRemoteSocketAddress() + " \"" + requestLine + "\"");
            //请求方法
            String method = header.getRequestMethod();
            //协议版本
            String version = "";
            /*处理GET或者HEAD方法*/
            if ("GET".equals(method) || "HEAD".equals(method)) {
                //请求文件名。对含有参数的url抛弃参数只返回'?'之前的文件路径
                String fileName = header.getRequestURI().split("\\?")[0];
                if (fileName.endsWith("/")) {
                    fileName += indexFileName;
                }
                String[] strs = fileName.split("\\.");
                String contentType = MIME_MAPPER.get(strs[strs.length - 1]);
                if (header.getRequestVersion() != null) {
                    version = header.getRequestVersion();
                }
                //substring(1)去掉第一个'/'，找到客户端请求的文件的完整路径
                File theFile = new File(rootDirectory, fileName.substring(1));
                if (theFile.canRead()
                        //不要让客户端超出文档根之外
                        && theFile.getCanonicalFile().toString().startsWith(root)) {
                    byte[] theData = Files.readAllBytes(theFile.toPath());
                    if (version.startsWith("HTTP/")) {
                        //发送一个含200状态码的MIME首部
                        sendHeader(out, "HTTP/1.0 200 OK", contentType, theData.length);
                    }
                    /*处理GET方法*/
                    if ("GET".equals(method)) {
                        //发送文件，这可能是一个图像或者其他二进制数据，
                        //所以要用底层输出流，而不是writer
                        raw.write(theData);
                        raw.flush();
                    }
                } else {
                    //客户端请求的文件超出根目录之外或者无读权限，返回404
                    String body = "<HTML>\r\n" +
                            "<HEAD><TITLE>File Not Found</TITLE>\r\n" +
                            "</HEAD>\r\n" +
                            "<BODY>" +
                            "<H1>HTTP Error 404:File Not Found</H1>\r\n" +
                            "</BODY></HTML>\r\n";
                    //发送一个含404状态码的MIME首部
                    if (version.startsWith("HTTP/")) {
                        sendHeader(out, "HTTP/1.0 404 Not Found", "text/html; charset=utf-8", body.length());
                    }
                    /*处理GET方法*/
                    if ("GET".equals(method)) {
                        out.write(body);
                        out.flush();
                    }
                }
            } else {
                //除了GET和HEAD的其他方法
                if (header.getRequestVersion() != null) {
                    version = header.getRequestVersion();
                }
                String body = "<HTML>\r\n" +
                        "<HEAD><TITLE>Not Implemented</TITLE>\r\n" +
                        "</HEAD>\r\n" +
                        "<BODY>" +
                        "<H1>HTTP Error 501:Not Implemented</H1>\r\n" +
                        "</BODY></HTML>\r\n";
                if (version.startsWith("HTTP/")) {
                    //发送一个MIMIE首部
                    sendHeader(out, "HTTP/1.0 501 Not Implemented", "text/html; charset=utf-8", body.length());
                }
                out.write(body);
                out.flush();
            }
        } catch (IOException e) {
            logger.error("Error talking to " + connection.getRemoteSocketAddress(), e);
        } finally {
            try {
                connection.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void sendHeader(Writer out, String responseCode, String contentType, int length) throws IOException {
        //写HTTP响应行
        out.write(responseCode + "\r\n");
        //写HTTP头的其他字段
        out.write("Date: " + new Date() + "\r\n");
        out.write("Server: littleTom 0.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");
        out.flush();
    }
}
