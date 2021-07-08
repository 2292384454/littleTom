package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/7/2 14:31
 *
 * @author KevinHwang
 */
public class XMLReader {
    /**
     * 日志工具
     */
    private final static Logger logger = LogManager.getLogger("RequestProcessorLog");

    public static Document getXMLDOM(String path) {
        try {
            // 创建 DOM 文档对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            File f=new File(path);
            return builder.parse(f);
        } catch (Exception e) {
            logger.error("创建 DOM 文档对象失败，请检查 " + path + " 文件！");
            return null;
        }
    }
}
