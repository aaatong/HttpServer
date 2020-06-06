package org.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Constant {

    public static final String CRLF = "\r\n";

    public static final String BLANK_SPACE = "\r\n";

    public static final String HTTP_VERSION = "HTTP/1.1";

    // map status code to reason phrase
    public static final HashMap<Integer, String> statusCodeMap = new HashMap<>();
    static {
        statusCodeMap.put(200, "OK");
        statusCodeMap.put(404, "Not Found");
        statusCodeMap.put(500, "Internal Server Error");
    }

    // map suffix to mime type
    public static final HashMap<String, String> suffixMap = new HashMap<>();
    static {
        Properties properties = new Properties();
        try {
            // InputStream ins = Constant.class.getResourceAsStream("/MimeTypeMappings.properties");
            // System.out.println(ins);
            properties.load(Constant.class.getResourceAsStream("/MimeTypeMappings.properties"));
            for (Map.Entry<Object, Object> e : properties.entrySet()) {
                suffixMap.put((String)e.getKey(), (String)e.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String DEFAULT_MIME_TYPE = "text/html";

    public static final String webRoot = "/home/luke/Documents/webroot";
}
