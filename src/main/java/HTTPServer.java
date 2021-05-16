import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * littleTom Web服务器.
 * <p>
 * 创建时间: 2021/5/11 18:09
 *
 * @author KevinHwang
 */
public class HTTPServer {
    private final static Logger logger = LogManager.getLogger("HTTPServerLog");//日志工具
    private static final int NUM_THREADS = 50;//最大线程数
    private static final String INDEX_FILE = "index.html";//主页文件
    private final File rootDirectory;//网站根目录
    private final int port;//监听端口

    /**
     * 指定网站根目录和监听端口的构造方法.
     *
     * @param rootDirectory 网站根目录
     * @param port          监听端口
     * @throws IOException 如果网站根目录参数不正确，抛出异常提示不存在该文件夹。
     */
    public HTTPServer(File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exists as a directory.");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    /**
     * 打开服务器的方法.
     *
     * @throws IOException 如果实例化ServerSocket失败，抛出该异常。
     */
    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);

            while (true) {
                try {
                    Socket request = server.accept();
                    pool.submit(new RequestProcessor(rootDirectory, INDEX_FILE, request));
                } catch (IOException e) {
                    logger.error("Error accepting connection", e);
                }
            }
        }
    }

    /**
     * 测试方法
     *
     * @param args 输入参数
     */
    public static void main(String[] args) {
//        //得到文档根
//        File docroot;
//        try {
//            docroot = new File(args[0]);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Usage: java littleTom docroot port.");
//            return;
//        }
//        //设置要监听的端口
//        int port;
//        try {
//            port = Integer.parseInt(args[1]);
//            if (port < 0 || port > 65565) port = 80;
//        } catch (RuntimeException e) {
//            port = 80;
//        }
        File docroot = new File("src/main/webapp/");
        int port = 80;
        try {
            HTTPServer webserver = new HTTPServer(docroot, port);
            webserver.start();
        } catch (IOException e) {
            logger.error("Server couldn't start.", e);
        }
    }
}
