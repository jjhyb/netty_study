package com.yibo.netty2.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 0:36
 * @Description:
 */
public class GroupChatClient {

    private String host;

    private int port;

    public GroupChatClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run(){
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new GroupCharClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("--------"+channel.remoteAddress()+"--------");

            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush(line+"\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1",7000).run();
    }
}
