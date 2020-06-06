package org.example.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.Servlet.Servlet;
import org.example.Servlet.StaticContentServlet;
import sun.net.dns.ResolverConfiguration;

import java.net.Socket;

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
        System.out.println(request.getUrl());
        Response response = new Response(ctx.channel());

        Servlet servlet = new StaticContentServlet();
        servlet.service(request, response);
        System.out.println("Bingo!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}