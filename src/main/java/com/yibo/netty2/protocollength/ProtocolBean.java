package com.yibo.netty2.protocollength;

/**
 * @Author: huangyibo
 * @Date: 2021/3/13 1:31
 * @Description:
 */

public class ProtocolBean {

    //类型  系统编号 1表示ios端, 2表示安卓端, 3表示PC端
    private byte type = 1;

    //信息标志 1文本消息, 2图片消息, 3语音消息, 4视频消息, 5表示心跳包, 6表示超时包
    private byte flag;

    //内容长度
    private int length;

    //内容
    private byte[] content;

    public ProtocolBean() {
    }

    public ProtocolBean(byte type, byte flag, int length, byte[] content) {
        this.type = type;
        this.flag = flag;
        this.length = length;
        this.content = content;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
