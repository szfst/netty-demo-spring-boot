package com.example.nettyspringboot;

import com.example.nettyspringboot.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettyspringbootApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(NettyspringbootApplication.class, args);
		new Thread(()->{
			NettyServer nettyServer = (NettyServer)ctx.getBean("nettyServer");
			try {
				nettyServer.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
