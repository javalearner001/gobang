package com.sun.gobang.chess;

import com.sun.gobang.entity.Chess;
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

    public void piece(int x, int y, int color) {
        if (chessList.size() == SIZE * SIZE) {
            System.out.println("棋盘已满，游戏结束");
            // 平局
            // 给对手发送消息，让他更新数据库信息

            return;
        }

        // 判断下子是否重复
        if (arr[x][y]) {
            System.out.println("同一坐标重复落子，无效！");
            return;
        }

        arr[x][y] = true;
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
