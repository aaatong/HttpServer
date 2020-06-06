package org.example.server;

import org.example.util.IOClose;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {

    private ExecutorService pool;

    private ServerSocket server;

    private int port = 8081;

    public void setPort(int port) {
        this.port = port;
    }

    private void run() {
        init();
        start();
    }

    private void init() {
        System.out.println("Initializing...");

        pool = Executors.newCachedThreadPool();
    }

    private void start() {
        // try {
        //     server = new ServerSocket(9005);
        //     System.out.println("Server started，listening on port 9005...");
        //     while (true){
        //         Socket client = server.accept();
        //         pool.execute(new Dispatcher(client));
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } finally {
        //     IOClose.close(server);
        //     pool.shutdown();
        // }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("httpDecoder", new MyHttpRequestDecoder())
                                    .addLast(new Dispatcher());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpServer().run();
    }
}
