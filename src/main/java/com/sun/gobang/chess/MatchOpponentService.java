package com.sun.gobang.chess;

import com.sun.gobang.entity.ChessRoom;
import com.sun.gobang.entity.response.MessageResponse;
import com.sun.gobang.socket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai
 * @since 2021/11/5 10:20 上午
 */
@Slf4j
@Service
public class MatchOpponentService {

    static final Map<String, ChessRoom> chessRoomMap = new ConcurrentHashMap<>();


    public void createChessRoom(String userIdOne,String userIdTwo){
        ChessRoom chessRoom = new ChessRoom(userIdOne,userIdTwo);
        chessRoomMap.put(chessRoom.getRoomId(),chessRoom);
        log.info("当前存在房间数:{}",chessRoomMap.size());
        MessageResponse response = new MessageResponse("对战开始");
        WebSocket.webSocketMap.get(userIdOne).sendObjMessage(response);
        WebSocket.webSocketMap.get(userIdTwo).sendObjMessage(response);
    }
}
