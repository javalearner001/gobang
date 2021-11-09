package com.sun.gobang.chess;

import com.sun.gobang.entity.ChessRoom;
import com.sun.gobang.entity.enumc.MessageTypeEnum;
import com.sun.gobang.entity.response.MessageResponse;
import com.sun.gobang.singlesource.ChessRoomMapFactory;
import com.sun.gobang.singlesource.UserRoomMapFactory;
import com.sun.gobang.socket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author sunkai
 * @since 2021/11/5 10:20 上午
 */
@Slf4j
@Service
public class MatchOpponentService {



    public void createChessRoom(String userIdOne,String userIdTwo){
        //1.创建房间，存放房间
        ChessRoom chessRoom = new ChessRoom(userIdOne,userIdTwo);
        Map<String, ChessRoom> chessRoomMap = ChessRoomMapFactory.getChessRoomMap();
        chessRoomMap.put(chessRoom.getRoomId(),chessRoom);

        //2.保存用户和房间号
        Map<String, String> userRoomMap = UserRoomMapFactory.getUserRoomMap();
        userRoomMap.put(userIdOne,chessRoom.getRoomId());
        userRoomMap.put(userIdTwo,chessRoom.getRoomId());

        //3.给用户发送消息
        log.info("当前存在房间数:{}", chessRoomMap.size());
        MessageResponse response = new MessageResponse("对战开始", MessageTypeEnum.BROAD_CAST.getCode());
        WebSocket.webSocketMap.get(userIdOne).sendObjMessage(response);
        WebSocket.webSocketMap.get(userIdTwo).sendObjMessage(response);
    }
}
