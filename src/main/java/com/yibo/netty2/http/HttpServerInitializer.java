package com.yibo.netty2.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: huangyibo
 * @Date: 2021/3/1 22:19
 * @Description:
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        ChannelPipeline pipeline = ch.pipeline();
        //加入Netty提供的httpServerCodec
        //HttpServerCodec Netty提供的http的编解码器
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        //添加自定义的Handler
        pipeline.addLast("HttpHandler",new HttpHandler());
    }
}
