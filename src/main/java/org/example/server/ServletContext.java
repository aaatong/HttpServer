package org.example.server;

import org.example.servlet.Default404Servlet;
import org.example.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Map;

public class ServletContext {

    private static Map<String, String> urlMap;
    private static Map<String, String> servletMap;

    static  {
        urlMap = new HashMap<>();
        servletMap = new HashMap<>();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            WebHandler handler = new WebHandler();
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB_INFO/web.xml"), handler);
            urlMap = handler.getUrlMap();
            servletMap = handler.getServletMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Servlet getServlet(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (urlMap.containsKey(url)) {
            String servletFullName = servletMap.get(urlMap.get(url));
            return (Servlet)Class.forName(servletFullName).newInstance();
        } else {
            return null;
        }
    }

}
