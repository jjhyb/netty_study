package com.yibo.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author: huangyibo
 * @Date: 2021/2/25 23:08
 * @Description:
 */
public class GroupChatClient {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6666;

    private Selector selector;

    private SocketChannel socketChannel;

    private String username;

    //完成初始化工作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + " is ok");
    }

    //向服务器发送消息
    public void sendInfo(String msg){
        msg = username + " 说："+msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取服务器端回复的消息
    public void readInfo(){
        try {
            if(selector.select() == 0){
                return;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isReadable()){
                    //得到相关的通道
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //得到一个Buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //读取
                    int read = channel.read(byteBuffer);
                    if(read > 0){
                        String message = new String(byteBuffer.array());
                        System.out.println(message.trim());
                    }
                }
                iterator.remove();//防止重复操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程，每隔3秒从服务器端读取数据
        new Thread() {
            @Override
            public void run() {
                while(true){
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //监听键盘，发送数据
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            chatClient.sendInfo(line);
        }
    }
}
