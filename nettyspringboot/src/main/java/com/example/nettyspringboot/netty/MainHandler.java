package com.example.nettyspringboot.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        try {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String request = new String(bytes,"utf-8");

            System.out.println("server get:"+request);

            String response = "进行返回给客户端的响应：" + request ;
            String response1 = "........：" + request ;
            ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
            ctx.writeAndFlush(Unpooled.copiedBuffer(response1.getBytes()));

        }catch (Exception e){

        }finally {
            //必须要释放,有write的时候就不用释放了
//            buf.release();
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("报错了...");
        cause.printStackTrace();
        //释放
        ctx.close();
    }
}
