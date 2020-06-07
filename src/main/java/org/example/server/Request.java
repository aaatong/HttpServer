package org.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequestDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private String url;

    private String httpMethod = null;

    private ByteBuf messageBody = null;

    // public Request(Socket client) {
    //     try {
    //         is = client.getInputStream();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     valid = true;
    //     parseRequest();
    // }
    //
    // private void parseRequest() {
    //     BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    //     // StringBuilder wholeRequestBuilder = new StringBuilder();
    //     String requestLine = null;
    //     try {
    //         requestLine = reader.readLine();
    //         if (requestLine == null) {
    //             valid = false;
    //             return;
    //         }
    //         System.out.println(requestLine);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //
    //     // Request method: GET, POST, etc.
    //     String requestMethod = requestLine.split(" ")[0];
    //     // requested resource
    //     url = requestLine.split(" ")[1];
    //     // the whole request string
    //     // wholeRequestBuilder.append(requestLine).append(Constant.CRLF);
    //     try {
    //         String nextLine = null;
    //         while ((nextLine = reader.readLine()) != null) {
    //             // wholeRequestBuilder.append(nextLine).append(Constant.CRLF);
    //             // System.out.println(nextLine);
    //             if (nextLine.equals("")) {
    //                 break;
    //             }
    //         }
    //         // char[] data = new char[3000];
    //         // int len = reader.read(data);
    //         // System.out.println(String.valueOf(data, 0, len));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //
    //     if(url.equals("/")){
    //         url += "index.html";
    //     }
    //
    // }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ByteBuf getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(ByteBuf messageBody) {
        this.messageBody = messageBody;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    // public boolean isValid() {
    //     return valid;
    // }
}
