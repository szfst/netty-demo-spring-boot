package com.example.nettyspringboot.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MainHandler mainHandler;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("接收到客户端的连接..."+socketChannel.remoteAddress());
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(mainHandler);
    }
}
