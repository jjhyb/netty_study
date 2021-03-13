package com.yibo.netty2.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Author: huangyibo
 * @Date: 2021/3/8 23:20
 * @Description:
 * TextWebSocketFrame类型，表示一个文本帧(frame)
 */
public class TextWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息："+msg.text());

        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+ LocalDateTime.now() + msg.text()));
    }

    /**
     * 客户端连接后，触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一的值,LongText是唯一的,ShortText不是唯一的
        System.out.println("handlerAdded方法被调用 "+ctx.channel().id().asLongText());
        System.out.println("handlerAdded方法被调用 "+ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved方法被调用 "+ctx.channel().id().asLongText());
        System.out.println("handlerRemoved方法被调用 "+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生："+cause.getMessage());
        ctx.channel().close();
    }
}
