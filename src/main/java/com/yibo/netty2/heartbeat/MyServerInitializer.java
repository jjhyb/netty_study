package com.yibo.netty2.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:40
 * @Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty提供的IdleStateHandler
        /**
         * 说明：
         * IdleStateHandler：是netty提供的处理空闲状态的处理器
         * long readerIdleTime：表示多长时间没有读(server没有读取到客户端数据),就会发送心跳检测包，检测是否还是连接状态
         * long writerIdleTime：表示多长时间没有写(server没有写数据到客户端),就会发送心跳检测包，检测是否还是连接状态
         * long allIdleTime：表示多长时间没有读写,就会发送心跳检测包，检测是否还是连接状态
         * 当IdleStateEvent触发后，就会传递给管道的下一个handler去处理，通过调用下一个handler的userEventTiggered
         * 在userEventTiggered方法中处理IdleStateEvent事件(读空闲，写空闲，读写空闲)
         */
        pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));

        //加入一个对空闲检测进一步处理的handler
        pipeline.addLast(new MyServerHandler());

    }
}
