package com.yibo.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: huangyibo
 * @Date: 2019/3/31 23:41
 * @Description:
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));

        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                if(selectionKey.isConnectable()){
                    SocketChannel client = (SocketChannel)selectionKey.channel();
                    if(client.isConnectionPending()){//是否处于连接过程中
                        //是的话，完成这个连接
                        client.finishConnect();

                        //第一次建立连接，通过buffer将数据写出
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((LocalDateTime.now() + "：连接成功").getBytes());
                        writeBuffer.flip();
                        client.write(writeBuffer);

                        //通过一个线程的线程池取监听键盘输入
                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        executorService.submit(() -> {
                            while(true){
                                //首先初始化writeBuffer
                                writeBuffer.clear();
                                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                BufferedReader br = new BufferedReader(inputStreamReader);
                                String sendMessage = br.readLine();

                                writeBuffer.put(sendMessage.getBytes());
                                writeBuffer.flip();
                                client.write(writeBuffer);
                            }
                        });
                    }
                    //注册客户端read事件
                    client.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel)selectionKey.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int read = client.read(readBuffer);
                    if(read > 0){
                        String receivedMessage = new String(readBuffer.array(),0,read);
                        System.out.println(receivedMessage);
                    }
                }
            }
            //其实在清除这里，最好是用iterator进行遍历，那样做那个可以直接在iterator里面进行删除
            selectionKeys.clear();
        }
    }
}
