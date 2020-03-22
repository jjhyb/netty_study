package com.yibo.netty.example6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author: huangyibo
 * @Date: 2019/3/12 1:26
 * @Description:
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if(0 == randomInt){
            MyDataInfo.Person person = MyDataInfo.Person.newBuilder()
                    .setName("张三").setAge(20).setAddress("深圳").build();

            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(person).build();
        }else if (1 == randomInt){
            MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("二哈").setColor("灰白黑").build();

            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(dog).build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder().setName("黑猫").setCity("重庆").build()).build();
        }

        ctx.channel().writeAndFlush(myMessage);

    }
}
