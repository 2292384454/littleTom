package utils;

import org.w3c.dom.Document;

import java.util.HashMap;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/7/2 19:53
 *
 * @author KevinHwang
 */
public class ConfigMapper extends HashMap<String, String> {
    /**
     * 存放 MIME type 信息的 xml文件
     */
    private final String XML_PATH = "src/main/config/config.xml";

    private ConfigMapper() {
        super();
        Document doc = XMLReader.getXMLDOM(XML_PATH);
        // 获取包含 mime-mapping 的文本结点
        assert doc != null;
        put("num_threads", doc.getElementsByTagName("num_threads").item(0).getFirstChild().getNodeValue().trim());
        put("index_file", doc.getElementsByTagName("index_file").item(0).getFirstChild().getNodeValue().trim());
        put("rootDirectory", doc.getElementsByTagName("rootDirectory").item(0).getFirstChild().getNodeValue().trim());
        put("port", doc.getElementsByTagName("port").item(0).getFirstChild().getNodeValue().trim());
    }

    private static class HolderClass {
        private final static ConfigMapper INSTANCE = new ConfigMapper();
    }

    public static ConfigMapper getMapper() {
        return ConfigMapper.HolderClass.INSTANCE;
    }
}
