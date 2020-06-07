package org.example.servlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.example.server.ParameterParser;
import org.example.server.Request;
import org.example.server.Response;
import org.example.util.Constant;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class LoginServlet implements Servlet{
    @Override
    public void service(Request request, Response response) {
        if (request.getHttpMethod().equals(Constant.HTTP_GET)) {
            doGet(request, response);
        } else if (request.getHttpMethod().equals(Constant.HTTP_POST)) {
            doPost(request, response);
        }
    }

    private void doGet(Request req, Response rep){
        Map<String, List<String>> parameterMap = ParameterParser.parse(req.getUrl());
        String name = parameterMap.get("uname").get(0);
        String pwd = parameterMap.get("pwd").get(0);
        StringBuilder messageBody = new StringBuilder();
        if(login(name,pwd)) {
            messageBody.append("<html><head><title>Welcome!</title>").append(Constant.CRLF);
            messageBody.append("</head><body>").append(Constant.CRLF);
            messageBody.append("Hello, ").append(name).append("!").append(Constant.CRLF);
            messageBody.append("</body></html>");
        }else {
            messageBody.append("Login Failed!");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(messageBody, Charset.defaultCharset());
        rep.pushHeader(200, Constant.DEFAULT_MIME_TYPE, byteBuf.readableBytes());
        rep.pushContent(byteBuf);
    }

    private void doPost(Request request, Response response) {

    }

    public boolean login(String name,String pwd) {
        return name.equals("luke") && pwd.equals("123456");
    }

}
