package com.yibo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 21:20
 * @Description:
 *
 * 文件锁
 */
public class NioTest10 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.tryLock(0, 4, true);
        System.out.println("valid：" + fileLock.isValid());
        System.out.println("lock type：" + fileLock.isShared());

        fileLock.release();
        randomAccessFile.close();
    }
}
