package org.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            System.out.println(requestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Request method: GET, POST, etc.
        String requestMethod = requestLine.split(" ")[0];
        // requested resource
        String url = requestLine.split(" ")[1];
        // the whole request string
        try {
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                if (nextLine.equals("")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(url.equals("/")){
            url += "index.html";
        }
        request.setUrl(url);
        return true;
    }
}
