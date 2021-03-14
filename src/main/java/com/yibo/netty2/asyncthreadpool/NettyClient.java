package com.yibo.netty2.asyncthreadpool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 18:43
 * @Description:
 */
public class NettyClient {

    public static void main(String[] args) {
        //客户端只需要一个事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(eventLoopGroup)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            //客户端连接服务器端 channelFuture涉及到Netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
