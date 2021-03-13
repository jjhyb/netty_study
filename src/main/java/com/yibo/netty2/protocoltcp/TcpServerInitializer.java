package com.yibo.netty2.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:40
 * @Description:
 */
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();

        //解码器
        pipeline.addLast("MessageProtocolDecoder",new MessageProtocolDecoder());

        //编码器
        pipeline.addLast("MessageProtocolEncoder",new MessageProtocolEncoder());

        //加入业务处理的handler
        pipeline.addLast("handler",new TcpServerHandler());
    }
}
