package com.sun.gobang.entity;

/**
 * @author sunkai
 * @since 2021/11/5 10:01 上午
 */
public class ChessRoom {

    private String userIdOne;

    private String userIdTwo;

    private String roomName = "一对一房间";

    private String roomId;

    private int[][] checkBoard = new int[15][15];

    public ChessRoom(String userIdOne, String userIdTwo) {
        this.userIdOne = userIdOne;
        this.userIdTwo = userIdTwo;
        this.roomId = userIdOne + "-" + userIdTwo;
    }

    public String getAnotherUserId(String userId){
        if (userId.equals(this.userIdOne)){
            return this.userIdTwo;
        }
        return this.userIdOne;
    }

    public String getUserIdOne() {
        return userIdOne;
    }

    public void setUserIdOne(String userIdOne) {
        this.userIdOne = userIdOne;
    }

    public String getUserIdTwo() {
        return userIdTwo;
    }

    public void setUserIdTwo(String userIdTwo) {
        this.userIdTwo = userIdTwo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int[][] getCheckBoard() {
        return checkBoard;
    }

    public void setCheckBoard(int[][] checkBoard) {
        this.checkBoard = checkBoard;
    }
}
