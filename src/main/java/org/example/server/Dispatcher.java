package org.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.servlet.Servlet;
import org.example.servlet.StaticContentServlet;

// public class Dispatcher implements Runnable{
//
//     private Response response;
//     private Request request;
//     private Socket client;
//
//     public Dispatcher(Socket client) {
//         System.out.println("New Socket!");
//         this.client = client;
//         this.request = new Request(client);
//         this.response = new Response(client);
//     }
//
//     @Override
//     public void run() {
//         new StaticContentServlet().service(request, response);
//         IOClose.close(client);
//     }
// }

public class Dispatcher extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request)msg;
        Response response = new Response(ctx.channel());

        Servlet servlet = ServletContext.getServlet(request.getUrl().split("\\?")[0]);
        if (servlet == null) {
            servlet = new StaticContentServlet();
        }
        servlet.service(request, response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}