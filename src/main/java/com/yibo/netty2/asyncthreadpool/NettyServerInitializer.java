package com.yibo.netty2.asyncthreadpool;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/3/14 17:35
 * @Description:
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    //private static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("handler",new NettyServerHandler());

        //在addLast添加handler,参数指定了EventExecutorGroup
        //那么该handler会优先加入到线程池中执行
        //pipeline.addLast(group,"handler",new NettyServerHandler());
    }
}
