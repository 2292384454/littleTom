package utils;

import java.util.HashMap;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/7/2 15:40
 *
 * @author KevinHwang
 */
public class HTTPHeader {
    /**
     * 请求头
     */
    private final String header;
    /**
     * 请求行
     */
    private String requestLine;
    /**
     * 请求头的其他部分
     */
    private HashMap<String, String> headerMap;

    public HTTPHeader(String header) {
        this.header = header.trim();
        headerMap = new HashMap<>();
        String[] lines = header.split("\r\n");
        this.requestLine = lines[0].trim();
        for (int i = 1; i < lines.length; i++) {
            String[] s = lines[i].split(":");
            String name = s[0].trim();
            String value = s[1].trim();
            headerMap.put(name, value);
        }
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getRequestMethod() {
        return requestLine.split(" ")[0].trim();
    }

    public String getRequestURI() {
        return requestLine.split(" ")[1].trim();
    }

    public String getRequestVersion() {
        if (requestLine.split(" ").length > 2) {
            return requestLine.split(" ")[2].trim();
        } else {
            return null;
        }
    }

    public String getHeaderField(String fieldName) {
        return headerMap.get(fieldName);
    }
}
