package com.yibo.netty2.asyncthreadpool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 18:21
 * @Description:
 * 自定义Handler需要继承Netty规定好的HandlerAdapter
 * 这样自定义的Handler,才能称为Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //group充当业务线程池，可以将任务提交到该线程池
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    /**
     * 读取数据事件（读取客户端发送的消息）
     * @param ctx   上下文对象，可以获取管道pipeline,通道channel,地址
     * @param msg   客户端发送的数据，默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("NettyServerHandler.channelRead 执行的线程："+Thread.currentThread().getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());
        //如果这里有一个非常耗时长的业务 -> 提交到线程池异步执行
        group.submit(() -> {
            try {
                //模拟耗时长的业务
                Thread.sleep(10 * 1000);
                System.out.println("group.submit 异步执行的线程："+Thread.currentThread().getName());
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端~喵2~", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

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
