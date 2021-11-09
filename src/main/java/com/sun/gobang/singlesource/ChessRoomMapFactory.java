package com.sun.gobang.singlesource;

import com.sun.gobang.entity.ChessRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai
 * @since 2021/11/9 3:42 下午
 */
public class ChessRoomMapFactory {

    private static Map<String, ChessRoom> chessRoomMap = new ConcurrentHashMap<>();

    private ChessRoomMapFactory(){

    }

    public static Map<String, ChessRoom> getChessRoomMap() {
        if (chessRoomMap == null) {
            chessRoomMap = new ConcurrentHashMap<>();
        }
        return chessRoomMap;
    }
}
