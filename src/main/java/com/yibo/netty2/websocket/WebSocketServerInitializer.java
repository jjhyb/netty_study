package com.yibo.netty2.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:40
 * @Description:
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();

        //因为基于http协议,使用Http的编码解码器
        pipeline.addLast("http-decoder",new HttpServerCodec());// 请求消息解码器

        /*
         * ChunkedWriteHandler：chunked,其实看名字就能够看出来,数据是一块一块的发送的,其中每一块分为两个部分：
         *（1）chunked头部 （2）chunked的body
         * 每一部分之后都需要一个回车换行来隔开 其中头部其实就是一个整数，用于表示body的长度
         * 在进行大文件传输的时候，一次将文件的全部内容映射到内存中，很有可能导致内存溢出。
         * 为了解决大文件传输过程中的内存溢出，Netty提供了ChunkedWriteHandler
         * 来解决大文件或者码流传输过程中可能发生的内存溢出问题
         *
         * http server接收数据时，发现header中有Transfer-Encoding: chunked，则会按照truncked协议分批读取数据。
         * http server发送数据时，如果需要分批发送到客户端，则需要在header中加上 Transfer-Encoding: chunked，然后按照truncked协议分批发送数据
         * chunk主要包含大小和数据，大小表示这个这个trunck包的大小，使用16进制标示。其中trunk之间的分隔符为CRLF。
         * 通过last-chunk来标识chunk发送完成。 一般读取到last-chunk(内容为0)的时候，代表chunk发送完成。
         * trailer 表示增加header等额外信息，一般情况下header是空。通过CRLF来标识整个chunked数据发送完成。
         */
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());//目的是支持异步大文件传输（）

        /*
         * http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合起来
         * 这就是浏览器发送大量数据时，就会发出多次http请求
         */
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(8192));// 目的是将多个消息转换为单一的request或者response对象

        /*
         * 对于webSocket,它的数据是以帧(frame)的形式传递的
         * 可以看到WebSocketFrame类下有6个子类
         * 浏览器请求 ws://localhost:7000/hello 表示请求的uri
         * WebSocketServerProtocolHandler 核心功能是将http协议升级为WebSocket协议，保持长连接
         * 是通过一个状态码101来切换的
         */
        pipeline.addLast("http-WebSocket",new WebSocketServerProtocolHandler("/hello"));

        //自定义的Handler,处理业务逻辑
        pipeline.addLast("TextWebSocketServerHandler",new TextWebSocketServerHandler());
    }
}
