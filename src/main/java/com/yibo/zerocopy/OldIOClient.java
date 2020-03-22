package com.yibo.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author: huangyibo
 * @Date: 2019/4/16 23:03
 * @Description:
 */
public class OldIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8899);
        String fileName = "E:\\高并发基础\\01.工程化专题\\04_工程化专题回顾-.mp4";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        int readCount;
        int total = 0;

        long startTime = System.currentTimeMillis();
        while((readCount = inputStream.read(buffer)) != -1){
            total += readCount;
            dataOutputStream.write(buffer,0,readCount);
        }

        System.out.println("发送的总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();;
        inputStream.close();
        socket.close();
    }
}
