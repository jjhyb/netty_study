package com.yibo.netty2.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 0:36
 * @Description:
 */
public class TcpClient {

    private String host;

    private int port;

    public TcpClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run(){
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TcpClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TcpClient("127.0.0.1",7000).run();
    }
}
