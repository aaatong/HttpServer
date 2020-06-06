package org.example.Servlet;

import org.example.server.Request;
import org.example.server.Response;
import org.example.util.Constant;
import org.example.util.IOClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StaticContentServlet implements Servlet{

    private int statusCode = 200;

    @Override
    public void service(Request request, Response response) {
        // if (!request.isValid()) {
        //     return;
        // }

        String url = request.getUrl();
        String mimeType = Constant.DEFAULT_MIME_TYPE;
        String suffix = url.substring(url.lastIndexOf(".")+1);
        // System.out.println("suffix is " + suffix);
        if (!suffix.equals("")) {
            mimeType = Constant.suffixMap.getOrDefault(suffix, mimeType);
            // System.out.println("mime is " + mimeType);
        }

        File requestedFile = new File(Constant.webRoot + url);

        if (!requestedFile.exists()) {
            new Default404Servlet().service(request, response);
            return;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Constant.webRoot + url);
            response.pushHeader(statusCode, mimeType, fis.available());
            byte[] bytes = new byte[4096];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                response.pushContent(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOClose.close(fis);
        }
    }
}
