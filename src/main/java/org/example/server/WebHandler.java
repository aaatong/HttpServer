package org.example.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebHandler extends DefaultHandler {

    private Map<String, String> urlMap;
    private Map<String, String> servletMap;

    private String beginTag;
    private boolean isMapping;
    private UrlMapping urlMapping;
    private ServletMapping servletMapping;

    public WebHandler() {
        urlMap = new HashMap<>();
        servletMap = new HashMap<>();
    }

    private class UrlMapping {
        private String ServletName;
        private List<String> urlPatterns;

        public UrlMapping() {
            urlPatterns = new ArrayList<>();
        }

        public String getServletName() {
            return ServletName;
        }

        public List<String> getUrlPatterns() {
            return urlPatterns;
        }

        public void setServletName(String servletName) {
            ServletName = servletName;
        }
    }

    private class ServletMapping {
        private String ServletName;
        private String ServletClass;

        public void setServletName(String servletName) {
            ServletName = servletName;
        }

        public void setServletClass(String servletClass) {
            ServletClass = servletClass;
        }

        public String getServletName() {
            return ServletName;
        }

        public String getServletClass() {
            return ServletClass;
        }
    }

    @Override
    public void startDocument() throws SAXException {
        // System.out.println("Start parsing XML!");
    }

    @Override
    public void endDocument() throws SAXException {
        // System.out.println("XML parsing ends!");
    }

    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        if (null != qName) {
            beginTag = qName;
            if (qName.equals("servlet")) {
                isMapping = false;
                servletMapping = new ServletMapping();
            } else if (qName.equals("url-mapping")) {
                isMapping = true;
                urlMapping = new UrlMapping();
            }
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (null != beginTag) {
            String content = new String(ch,start,length);
            if (isMapping) {
                if (beginTag.equals("servlet-name")) {
                    urlMapping.setServletName(content);
                } else if (beginTag.equals("url-pattern")) {
                    urlMapping.getUrlPatterns().add(content);
                }
            } else {
                if (beginTag.equals("servlet-name")) {
                    servletMapping.setServletName(content);
                } else if (beginTag.equals("servlet-class")) {
                    servletMapping.setServletClass(content);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (null != qName) {
            if (qName.equals("servlet")) {
                servletMap.put(servletMapping.getServletName(), servletMapping.getServletClass());
            } else if (qName.equals("url-mapping")) {
                for (String url : urlMapping.getUrlPatterns()) {
                    urlMap.put(url, urlMapping.getServletName());
                }
            }
        }
        beginTag=null;
    }

    public Map<String, String> getUrlMap() {
        return urlMap;
    }

    public Map<String, String> getServletMap() {
        return servletMap;
    }
}
