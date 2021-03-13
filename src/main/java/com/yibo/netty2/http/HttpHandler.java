package com.yibo.netty2.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Author: huangyibo
 * @Date: 2021/3/1 22:19
 * @Description:
 * SimpleChannelInboundHandler  ChannelInboundHandlerAdapter的子类
 * HttpObject表示客户端和服务器端相互通讯的数据被封装成HttpObject
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            System.out.println("msg 类型："+msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名：" + httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return ;
            }

            //回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);
            //构造Http响应
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
