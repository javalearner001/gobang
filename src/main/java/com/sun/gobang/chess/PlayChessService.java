package com.sun.gobang.chess;

import com.alibaba.fastjson.JSON;
import com.sun.gobang.entity.Chess;
import com.sun.gobang.entity.ChessRoom;
import com.sun.gobang.entity.SocketMessage;
import com.sun.gobang.entity.enumc.MessageTypeEnum;
import com.sun.gobang.entity.response.MessageResponse;
import com.sun.gobang.singlesource.ChessRoomMapFactory;
import com.sun.gobang.singlesource.UserRoomMapFactory;
import com.sun.gobang.socket.WebSocket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunkai
 * @since 2021/11/5 4:49 下午
 */
@Service
public class PlayChessService {

    private static final int SIZE = 15;

    private static final List<Chess> chessList = new ArrayList<>();

    private static final boolean[][] arr = new boolean[SIZE][SIZE];
    private static final int[][] colors = new int[SIZE][SIZE];


    public void lowChess(SocketMessage socketMessage) {
        Chess chess = socketMessage.getChess();

        String roomId = UserRoomMapFactory.getUserRoomMap().get(socketMessage.getUserId());
        ChessRoom chessRoom = ChessRoomMapFactory.getChessRoomMap().get(roomId);
        int[][] checkBoard = chessRoom.getCheckBoard();
        //1.判断平局
        //2.落子
        if (checkBoard[chess.getX()][chess.getY()] != 0) {
            System.out.println("同一坐标重复落子，无效！");
            return;
        }
        checkBoard[chess.getX()][chess.getY()] = chess.getColor();

        //3.将全部数据返回对方用户
        String anotherUserId = chessRoom.getAnotherUserId(socketMessage.getUserId());

        MessageResponse messageResponse = new MessageResponse(JSON.toJSONString(checkBoard), MessageTypeEnum.CHECK_BOARD_DATA.getCode());
        WebSocket.webSocketMap.get(anotherUserId).sendObjMessage(messageResponse);


        /*// 出现五连子，结束游戏
        if (LogicService.isWin(chessList,chess,arr,colors)) {
            // 出现五连，发送结果消息

        }else {
            //通知对手下棋

        }*/

        /*for (int x = 0 ; x < checkBoard.length ; x++){
            int[] yArr = checkBoard[x];
            for ()
        }*/
    }


    public void piece(int x, int y, int color) {
        if (chessList.size() == SIZE * SIZE) {
            System.out.println("棋盘已满，游戏结束");
            // 平局
            // 给对手发送消息，让他更新数据库信息

            return;
        }

        // 绘制棋子
        Chess chess = new Chess(x,y,color);
        // 将棋子的颜色记录到数组colors 中
        colors[x][y] = color;

        // 把棋子加入到棋盘中
        //pane.getChildren().add(circle);
        // 将棋子的信息保存到数组中
        chessList.add(chess);


        // 出现五连子，结束游戏
        if (LogicService.isWin(chessList,chess,arr,colors)) {
            // 出现五连，发送结果消息

        }else {
            //通知对手下棋

        }
    }

}
