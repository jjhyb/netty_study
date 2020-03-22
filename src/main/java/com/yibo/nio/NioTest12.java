package com.yibo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: huangyibo
 * @Date: 2019/3/31 2:00
 * @Description:
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        int[] ports = {5001,5002,5003,5004,5005};

        //1、创建Selector实例
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            //2、创建ServerSocketChannel实例，并绑定指定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            //3、将serverSocketChannel注册到selector，并指定事件OP_ACCEPT，最底层的socket通过channel和selector建立关联
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口：" + ports[i]);
        }

        while(true){
            //4、如果没有准备好的socket，select方法会被阻塞一段时间并返回0；
            //5、如果底层有socket已经准备好，selector的select方法会返回socket的个数
            //而且selectedKeys方法会返回socket对应的事件（connect、accept、read or write）
            int numbers = selector.select();
            System.out.println("numbers：" + numbers);
            if(numbers == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys：" + selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //6、根据事件类型，进行不同的处理逻辑
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //在步骤3中elector只注册了serverSocketChannel的OP_ACCEPT事件
                if(selectionKey.isAcceptable()){
                    //如果有客户端A连接服务，执行select方法时，可以通过serverSocketChannel获取客户端A的socketChannel，
                    // 并在selector上注册socketChannel的OP_READ事件
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);


                    //将选择器注册到连接到的客户端信道，
                    //并指定该信道key值的属性为OP_READ，
                    //同时为该信道指定关联的附件
                    socketChannel.register(selector,SelectionKey.OP_READ);


                    System.out.println("获得客户端连接：" + socketChannel);
                }else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while(true){
                        ByteBuffer buffer = ByteBuffer.allocateDirect(512);
                        buffer.clear();
                        int read = socketChannel.read(buffer);
                        if(read <= 0){
                            break;
                        }
                        buffer.flip();
                        socketChannel.write(buffer);
                        bytesRead += read;
                    }
                    System.out.println("读取：" + bytesRead + "，来自于：" + socketChannel);
                }else if(selectionKey.isWritable() && selectionKey.isValid()){
                    //针对性的处理
                    //handleWrite(selectionKey);
                }else if(selectionKey.isConnectable()){
                    //针对性的处理
                    System.out.println("isConnectable = true");
                }

                //在处理完之后，一定要将此事件删除掉
                iterator.remove();
            }
        }
    }
}
