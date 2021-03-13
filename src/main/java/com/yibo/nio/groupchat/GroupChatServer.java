package com.yibo.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: huangyibo
 * @Date: 2021/2/25 0:28
 * @Description: 群聊系统服务端
 */
public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    //初始化
    public GroupChatServer(){
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该listenChannel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try {
            //循环处理
            while(true){
                int count = selector.select();
                if(count == 0){
                    continue;
                }
                //遍历得到的selectKey集合,selector.selectedKeys() 返回关注事件的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    //获取到SelectionKey
                    SelectionKey selectionKey = iterator.next();
                    //根据selectionKey 对应的通道发生的事件做相应处理
                    if(selectionKey.isAcceptable()){
                        //监听到Accept
                        SocketChannel socketChannel = listenChannel.accept();
                        socketChannel.configureBlocking(false);
                        //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                        socketChannel.register(selector,SelectionKey.OP_READ);
                        //提示
                        System.out.println(socketChannel.getRemoteAddress()+": 上线");
                    }else if(selectionKey.isReadable()){
                        //通道发生read事件，即通道是可读状态
                        readData(selectionKey);
                    }

                    //当前的selectionKey删除，防止重复处理
                    iterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    //读取客户端消息
    private void readData(SelectionKey selectionKey){
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的Channel
            channel = (SocketChannel) selectionKey.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if(count > 0){
                //把缓冲区的数据转成字符串输出
                String message = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端: " + message);
                //向其他客户端转发消息
                sendInfoToOtherClients(message,channel);
            }
        }catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + " 离线");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //转发消息给其他客户端
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");
        //遍历所有注册到selector 上的socketChannel 并排除self
        for (SelectionKey key : selector.keys()) {
            //通过key取出对应的通道
            SocketChannel targetChannel = (SocketChannel) key.channel();
            //排除自己
            if(targetChannel != self){
                //将msg存储到buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                //将byteBuffer 数据写入到通道中
                targetChannel.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
