package com.example.nettyspringboot.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();

        b.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture cf = b.connect("127.0.0.1", 8087).sync();
        cf.channel().write(Unpooled.copiedBuffer("777".getBytes()));
        cf.channel().write(Unpooled.copiedBuffer("777".getBytes()));
        cf.channel().write(Unpooled.copiedBuffer("666".getBytes()));
        cf.channel().flush();
        cf.channel().closeFuture().sync();
        worker.shutdownGracefully();
    }
}
