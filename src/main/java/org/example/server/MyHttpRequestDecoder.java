package org.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpRequestDecoder;
import org.example.util.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class MyHttpRequestDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Request request = new Request();
        boolean validRequest = parseRequest(in, request);
        if (validRequest) {
            out.add(request);
        }
    }

    private boolean parseRequest(ByteBuf in, Request request) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteBufInputStream(in)));
        // StringBuilder wholeRequestBuilder = new StringBuilder();
        String requestLine = null;
        try {
            requestLine = reader.readLine();
            if (requestLine == null) {
                return false;
            }
            // System.out.println(requestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Request method: GET, POST, etc.
        String requestMethod = requestLine.split(" ")[0];
        if (requestMethod.equalsIgnoreCase("get")) {
            request.setHttpMethod(Constant.HTTP_GET);
        } else if (requestMethod.equalsIgnoreCase("post")) {
            request.setHttpMethod(Constant.HTTP_POST);
        }

        // requested resource and parameters
        String url = requestLine.split(" ")[1];
        request.setUrl(url);

        // // parameter string
        // String parameterStr = null;
        //
        // if (requestMethod.equalsIgnoreCase("get") && url.contains("?")) {
        //     String[] urlArr = url.split("\\?");
        //     url = urlArr[0];
        //     parameterStr = urlArr[1];
        // }

        // read the rest of requests
        try {
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                if (nextLine.equals("")) {
                    break;
                }
            }
            // TODO: read variable-length HTTP request
            char[] parameterChars = new char[1500];
            int bodyLength = reader.read(parameterChars);
            if (bodyLength != -1) {
                ByteBuf messageBody = Unpooled.copiedBuffer(parameterChars, 0, bodyLength, Charset.defaultCharset());
                request.setMessageBody(messageBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if(url.equals("/")){
        //     url += "index.html";
        // }

        return true;
    }
}
