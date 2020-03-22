package com.yibo.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: huangyibo
 * @Date: 2019/4/16 23:27
 * @Description:
 */
public class NewIOServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);//此方法为，如果一个连接断掉但处于超时状态，该端口是否立即让新连接使用
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //这里为了演示拷贝文件效率，没有用到Selector，故设置为阻塞
            socketChannel.configureBlocking(true);

            int readCount = 0;
            while(-1 != readCount){
                readCount = socketChannel.read(byteBuffer);

                //将byteBuffer数组的值从新归位，为下一次读做准备。
                byteBuffer.rewind();
                //如果要写数据的话，必须用到byteBuffer.flip();
            }
        }
    }
}
