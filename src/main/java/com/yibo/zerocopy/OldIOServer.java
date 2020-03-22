package com.yibo.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: huangyibo
 * @Date: 2019/4/16 22:41
 * @Description:
 */
public class OldIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] byteArray = new byte[4096];

            while(true){
                int readCount = dataInputStream.read(byteArray, 0, byteArray.length);

                //这里仅仅是为了测试传统拷贝和零拷贝的速度，故没有将数据写入磁盘
                if(-1 == readCount){
                    break;
                }
            }
        }
    }
}
