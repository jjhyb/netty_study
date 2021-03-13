package com.yibo.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/2/26 22:36
 * @Description:
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename = "text.zip";
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        //在linux下一个transferTo 方法就可以完成传输
        //在windows下 一次调用 transferTo 只能发送8M, 大文件就需要分段传输文件
        //transferTo 底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        //关闭
        fileChannel.close();
    }
}
