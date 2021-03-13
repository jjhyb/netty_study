package com.yibo.netty2.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 18:21
 * @Description:
 * 自定义Handler需要继承Netty规定好的HandlerAdapter
 * 这样自定义的Handler,才能称为Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件（读取客户端发送的消息）
     * @param ctx   上下文对象，可以获取管道pipeline,通道channel,地址
     * @param msg   客户端发送的数据，默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转成ByteBuf
        //ByteBuf是netty提供的，不是Jdk原生的ByteBuffer
        /*ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());*/

        //如果这里有一个非常耗时长的业务 -> 异步执行 -> 提交该channel对应的NioEventLoop的TaskQueue中
        //解决方案1：用户自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~喵2~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //解决方案2：用户自定义的定时任务 -> 该任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~喵3~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },5, TimeUnit.SECONDS);

        System.out.println("......go on ......");
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~喵1~", CharsetUtil.UTF_8));
    }

    /**
     * 发生异常，需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
