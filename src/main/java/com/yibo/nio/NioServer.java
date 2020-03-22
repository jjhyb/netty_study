package com.yibo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author: huangyibo
 * @Date: 2019/3/31 20:52
 * @Description:
 */
public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                SocketChannel client;
                try {
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                        client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ);

                        String key = UUID.randomUUID().toString();
                        clientMap.put(key,client);
                    }else if(selectionKey.isReadable()){
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = client.read(buffer);
                        if(read > 0){
                            buffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String receivedMessage = String.valueOf(charset.decode(buffer).array());
                            System.out.println(client + ":" + receivedMessage);

                            String senderKey = null;
                            for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                if(client == entry.getValue()){
                                    senderKey = entry.getKey();
                                    break;
                                }
                            }

                            for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                if(senderKey == entry.getKey()){
                                    //如果senderKey和是自己客户端就直接跳过
                                    continue;
                                }

                                SocketChannel socketChannel = entry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((senderKey + ":" + receivedMessage).getBytes());
                                writeBuffer.flip();
                                socketChannel.write(writeBuffer);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            selectionKeys.clear();
        }
    }
}
