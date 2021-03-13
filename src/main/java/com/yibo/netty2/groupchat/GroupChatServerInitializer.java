package com.yibo.netty2.groupchat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:40
 * @Description:
 */
public class GroupChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入解码器
        pipeline.addLast("decoder",new StringDecoder());
        //加入编码器
        pipeline.addLast("encoder",new StringEncoder());
        //加入业务处理的handler
        pipeline.addLast("handler",new GroupChatServerHandler());
    }
}
