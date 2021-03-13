package com.yibo.netty2.protocollength;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 0:41
 * @Description:
 */
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;  //最大长度
    private static final int LENGTH_FIELD_LENGTH = 4;  //长度字段所占的字节数
    private static final int LENGTH_FIELD_OFFSET = 2;  //长度偏移
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取到pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();

        //解码器
        pipeline.addLast("ProtocolDecoder",new ProtocolDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));

        //编码器
        pipeline.addLast("ProtocolEncoder",new ProtocolEncoder());

        //加入业务处理的handler
        pipeline.addLast("handler",new TcpClientHandler());
    }
}
