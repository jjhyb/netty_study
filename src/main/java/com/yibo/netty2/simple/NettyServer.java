package com.yibo.netty2.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 17:56
 * @Description:
 */
public class NettyServer {

    public static void main(String[] args) {
        //创建bossGroup 和 workerGroup
        //创建两个线程组，bossGroup和workerGroup
        //bossGroup只是处理连接请求，真正的和客户端业务处理会交给workerGroup处理
        //两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器端的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    //给workerGroup的EventLoop对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("......服务器 id ready...");
            //绑定一个端口并同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
