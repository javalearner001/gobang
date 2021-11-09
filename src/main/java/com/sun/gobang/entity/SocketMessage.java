package com.sun.gobang.entity;

/**
 * @author sunkai
 * @since 2021/11/4 4:22 下午
 */
public class SocketMessage {

    private String userId;

    private String text;
    /**
     * 下棋的棋子数据
     */
    private Chess chess;
    /**
     * 消息类型  100：广播消息  1001：匹配开始
     */
    private int messageType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public Chess getChess() {
        return chess;
    }

    public void setChess(Chess chess) {
        this.chess = chess;
    }
}
