package com.yibo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author: huangyibo
 * @Date: 2019/3/16 19:35
 * @Description:
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();

        System.out.println("server started!");

        //这是在服务端jvm关闭之前主动退出grpc服务，且关闭其相应的资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("关闭jvm");
            GrpcServer.this.stop();
        }));
    }

    private void stop(){
        if(null != this.server){
            this.server.shutdown();
        }
    }

    //让服务启动后处于等待状态，不然服务已启动马上就停止了
    private void awaitTermination() throws InterruptedException {
        if(null != this.server){
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        GrpcServer grpcServer = new GrpcServer();

        grpcServer.start();
        grpcServer.awaitTermination();
    }
}
