package com.yibo.netty2.protocoltcp;

/**
 * @Author: huangyibo
 * @Date: 2021/3/13 1:31
 * @Description:
 */

/**
 * 自定义协议
 * 数据包格式
 * +--------+--------+----------+
 * |  Type  | Length |  Content |
 * +--------+--------+----------+
 * 1.Type：消息类型，为int类型的数据，1、表示文本消息，2表示图片消息，3表示视频消息等
 * 2.Length：传输数据的长度，int类型
 * 3.content：要传输的数据
 */
public class MessageProtocol {

    /**
     * 消息类型type，默认为文本消息
     */
    private int type = 1;

    /**
     * 消息长度length
     */
    private int length;

    /**
     * 消息的内容
     */
    private byte[] content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
