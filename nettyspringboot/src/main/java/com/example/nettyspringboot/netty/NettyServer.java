package com.example.nettyspringboot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component("nettyServer")
public class NettyServer {
    private int port = 8087;
    @Autowired
    private MyChannelInitializer initializer;
    public void start() throws InterruptedException {
        //一个是用于处理服务器端接收客户端连接的
        //一个是进行网络通信的（网络读写的）
        NioEventLoopGroup mainLoop = new NioEventLoopGroup();
        NioEventLoopGroup workLoop = new NioEventLoopGroup();

        try {
            //2 创建辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainLoop,workLoop)
                    //指定NIO的通道模式
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer)

            .option(ChannelOption.SO_BACKLOG, 128)         //设置tcp缓冲区,默认128,不用配置也可以
            .childOption(ChannelOption.SO_KEEPALIVE, true); //保持连接


            ChannelFuture future = serverBootstrap.bind(port).sync();
            //在关闭之前进行阻塞，相当于Thread.sleep(1000);这样的语句
            future.channel().closeFuture().sync().channel();
        }catch (Exception e){
            System.out.println("netty 报错"+e);
        }finally {

            workLoop.shutdownGracefully();
            mainLoop.shutdownGracefully();
        }


    }
}
