package utils;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;

/**
 * 一个采用单例模式的工具类，从web.xml中获得 文件后缀名 与 MIME type 对应关系.
 * <p>
 * 创建时间: 2021/7/2 14:29
 *
 * @author KevinHwang
 */
public class MimeMapper extends HashMap<String, String> {
    /**
     * 存放 MIME type 信息的 xml文件
     */
    private final String XML_PATH = "src/main/webapp/WEB-INF/web.xml";

    private MimeMapper() {
        super();
        Document doc = XMLReader.getXMLDOM(XML_PATH);
        // 获取包含 mime-mapping 的文本结点
        assert doc != null;
        NodeList nl = doc.getElementsByTagName("mime-mapping");
        int len = nl.getLength();
        for (int i = 0; i < len; i++) {
            Element mimeNode = (Element) nl.item(i);
            String extension = mimeNode.getElementsByTagName("extension").item(0).getTextContent();
            String mimeType = mimeNode.getElementsByTagName("mime-type").item(0).getTextContent();
            put(extension, mimeType);
        }
    }

    private static class HolderClass {
        private final static MimeMapper INSTANCE = new MimeMapper();
    }

    public static MimeMapper getMapper() {
        return HolderClass.INSTANCE;
    }
}
