package com.yibo.netty2.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 0:41
 * @Description:
 */
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入业务处理的handler
        pipeline.addLast("handler",new TcpClientHandler());
    }
}
