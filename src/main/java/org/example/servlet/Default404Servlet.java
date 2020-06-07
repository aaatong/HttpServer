package org.example.servlet;



import org.example.server.Request;
import org.example.server.Response;
import org.example.util.Constant;
import org.example.util.IOClose;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Default404Servlet implements Servlet{

    @Override
    public void service(Request request, Response response) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Constant.webRoot + "/404.html");
            response.pushHeader(404, "text/html", fis.available());
            byte[] data = new byte[4096];
            int len = 0;
            while ((len = fis.read(data)) != -1) {
                response.pushContent(data, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOClose.close(fis);
        }
    }
}
