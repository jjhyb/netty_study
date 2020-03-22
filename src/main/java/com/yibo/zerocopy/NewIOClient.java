package com.yibo.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: huangyibo
 * @Date: 2019/4/16 23:28
 * @Description:
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);
        String fileName = "E:\\高并发基础\\01.工程化专题\\04_工程化专题回顾-.mp4";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //复制文件，零拷贝
        long transferCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);

        System.out.println("发送的总字节数：" + transferCount + "，耗时：" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
