package org.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.example.util.IOClose;
import static org.example.util.Constant.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;


public class Response {

    private final Channel client;
    private BufferedWriter bw;
    private BufferedOutputStream bos;
    private final StringBuilder responseHeader;
    private final StringBuilder responseContent;
    // private int contentLength;

    public Response(Channel client) {
        this.client = client;
        // try {
        //     bos = new BufferedOutputStream(new ByteBufOutputStream(client));
        //
        //     // bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        responseHeader = new StringBuilder();
        responseContent = new StringBuilder();
        // contentLength = 0;
    }

    // public void print(String data) {
    //     responseContent.append(data);
    //     contentLength += data.length();
    // }
    //
    // public void println(String data) {
    //     responseContent.append(data).append(CRLF);
    //     contentLength += data.length() + CRLF.length();
    // }

    private void buildResponseHeader(int statusCode, String mimeType, int contentLength) {
        // status line
        responseHeader.append(HTTP_VERSION).append(BLANK_SPACE)
                .append(statusCode).append(BLANK_SPACE)
                .append(statusCodeMap.get(statusCode)).append(CRLF);
        // response header
        responseHeader.append("Server: QServer/1.0").append(CRLF);
        responseHeader.append("Data: ").append(new Date().toString()).append(CRLF);
        responseHeader.append("Content-type: ").append(mimeType).append(CRLF);
        responseHeader.append("Content-Length: ").append(contentLength).append(CRLF);
        responseHeader.append(CRLF);
    }

    public void pushHeader (int statusCode, String mimetype, int contentLength) throws IOException {
        buildResponseHeader(statusCode, mimetype, contentLength);
        ByteBuf byteBuf = client.alloc().buffer();
        byteBuf.writeCharSequence(responseHeader, Charset.defaultCharset());
        client.write(byteBuf);
        client.flush();
    }

    public void pushContent(byte[] data, int off, int len) throws IOException {
        ByteBuf byteBuf = client.alloc().buffer();
        byteBuf.writeBytes(data, off, len);
        client.write(byteBuf);
        client.flush();
    }

    // public void pushToClient(int statusCode, String mimeType) {
    //     buildResponseHeader(statusCode, mimeType);
    //     try {
    //         bos.write(responseHeader.toString().getBytes());
    //         bos.write(responseContent.toString().getBytes());
    //         bos.flush();
    //         // bw.write(responseHeader.toString());
    //         // bw.write(responseContent.toString());
    //         // bw.flush();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void close() {
        IOClose.close(bw);
        IOClose.close(bos);
    }
}
