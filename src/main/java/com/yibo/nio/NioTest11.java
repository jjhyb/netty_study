package com.yibo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 22:13
 * @Description:
 *
 * 关于Buffer的Scattering和Gathering
 */
public class NioTest11 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2+3+4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while(true){
            int bytesRead = 0;

            while(bytesRead < messageLength){
                long read = socketChannel.read(buffers);
                bytesRead += read;

                System.out.println("bytesRead：" + bytesRead);

                Arrays.asList(buffers).stream().map(buffer ->
                    "position：" + buffer.position() + "limit：" + buffer.limit()
                ).forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(buffer -> {
                buffer.flip();
            });

            long bytesWritten = 0;
            while(bytesWritten < messageLength){
                long write = socketChannel.write(buffers);
                bytesWritten += write;
            }
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("bytesRead：" + bytesRead + "，bytesWritten：" + "messageLength：" + messageLength);
        }
    }
}
