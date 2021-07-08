import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigMapper;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * littleTom Web服务器.
 * <p>
 * 创建时间: 2021/5/11 18:09
 *
 * @author KevinHwang
 */
public class HTTPServer {
    /**
     * 日志工具
     */
    private final static Logger logger = LogManager.getLogger("HTTPServerLog");
    /**
     * 最大线程数
     */
    private final int NUM_THREADS;
    /**
     * 主页文件
     */
    private final String INDEX_FILE;
    /**
     * 网站根目录
     */
    private final File rootDirectory;
    /**
     * 监听端口
     */
    private final int port;

    /**
     * 指定网站根目录和监听端口的构造方法.
     *
     * @param num_threads
     * @param index_file
     * @param rootDirectory 网站根目录
     * @param port          监听端口
     * @throws IOException 如果网站根目录参数不正确，抛出异常提示不存在该文件夹。
     */
    public HTTPServer(int num_threads, String index_file, File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exists as a directory.");
        }
        NUM_THREADS = num_threads;
        INDEX_FILE = index_file;
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    /**
     * 打开服务器的方法.
     *
     * @throws IOException 如果实例化ServerSocket失败，抛出该异常。
     */
    public void start() throws IOException {
        logger.info("littleTom has ran on http://localhost:" + port);
        //自定义线程池，避免 FixedThreadPool 的队列无限扩张导致内存耗尽的问题
        final AtomicInteger threadIndex = new AtomicInteger(1);
        ThreadFactory tf = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "request-pool-" + threadIndex.getAndIncrement());
            }
        };
        RejectedExecutionHandler reh = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.error("线程池已满，有连接被丢弃，请处理！");
            }
        };
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, NUM_THREADS, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), tf, reh);
        try (
                ServerSocket server = new ServerSocket(port)) {
            logger.info("Document Root: " + rootDirectory);
            logger.info("Index file is " + INDEX_FILE);

            while (true) {
                try {
                    Socket request = server.accept();
                    // 每接收到一个连接请求，就向线程池里添加一个线程处理
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
        ConfigMapper mapper = ConfigMapper.getMapper();

        int numThreads = Integer.parseInt(mapper.get("num_threads"));
        File docRoot = new File(mapper.get("rootDirectory"));
        int port = Integer.parseInt(mapper.get("port"));
        String indexFile = mapper.get("index_file");
        try {
            HTTPServer webserver = new HTTPServer(numThreads, indexFile, docRoot, port);
            webserver.start();
        } catch (IOException e) {
            logger.error("Server couldn't start.", e);
        }
    }
}
