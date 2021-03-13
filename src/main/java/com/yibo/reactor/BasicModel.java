package com.yibo.reactor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 0:32
 * @Description:
 */
class BasicModel implements Runnable {
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (!Thread.interrupted()) {
                //创建新线程来handle
                // or, single-threaded, or a thread pool
                new Thread(new Handler(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Handler implements Runnable {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                byte[] input = new byte[1024];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] process(byte[] input) {
            byte[] output=null;

            //业务逻辑处理

            return output;
        }
    }
}
