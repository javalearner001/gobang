package com.sun.gobang.chess;

import com.sun.gobang.entity.Chess;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunkai
 * @since 2021/11/5 4:30 下午
 */
@Service
public class LogicService {

    /**
     * 判断是否获胜
     * @param chessList
     * @param chess  当前落子
     * @param arr
     * @param colors
     * @return
     */
    public static boolean isWin(List<Chess> chessList, Chess chess, boolean[][] arr, int[][] colors) {
        //棋子少于9个，不可能出现五子
        if (chessList.size() < 9) {
            return false;
        }
        // 当前连子个数
        int count = 1;
        // 横左有几个连子
        for (int i = chess.getX() - 1; i >= 0 && i >= chess.getX() - 4; i--) {
            if (arr[i][chess.getY()] && chess.getColor() == (colors[i][chess.getY()])) {
                count++;
            } else {
                break;
            }
        }
        // 横右有几个连子
        for (int i = chess.getX() + 1; i <= 14  && i <= chess.getX() + 4; i++) {
            if (arr[i][chess.getY()] && chess.getColor() == (colors[i][chess.getY()])) {
                count++;
            } else {
                break;
            }
        }
        // 横线是否五连
        if (count >= 5) {
            return true;
        }
        count = 1;

        // 竖上有几个连子
        for (int i = chess.getY() - 1; i >= 0 && i >= chess.getY() - 4; i--) {
            if (arr[chess.getX()][i] && chess.getColor() == (colors[chess.getX()][i])) {
                count++;
            } else {
                break;
            }
        }
        // 竖下有几个连子
        for (int i = chess.getY() + 1; i <= 14 && i <= chess.getY() + 4; i++) {
            if (arr[chess.getX()][i] && chess.getColor() == (colors[chess.getX()][i])) {
                count++;
            } else {
                break;
            }
        }
        // 竖线是否五连
        if (count >= 5) {
            return true;
        }
        count = 1;

        // 右斜上有几个连子
        for (int i = chess.getX() - 1,  j = chess.getY() - 1;
             i >= 0 &&  i >= chess.getX() - 4 && j >= 0 &&
                     j >= chess.getY() - 4; i--, j--) {
            if (arr[i][j] && chess.getColor() == (colors[i][j])) {
                count++;
            } else {
                break;
            }

        }

        // 右斜下有几个连子
        for (int i = chess.getX() + 1,  j = chess.getY() + 1;
             i <= 14 &&  i <= chess.getX() + 4 && j <= 14 &&
                     j <= chess.getY() + 4; i++, j++) {
            if (arr[i][j] && chess.getColor() == (colors[i][j])) {
                count++;
            } else {
                break;
            }

        }
        // 右斜线是否五连
        if (count >= 5) {
            return true;
        }
        count = 1;
        // 左斜上有几个连子
        for (int i = chess.getX() + 1,  j = chess.getY() - 1;
             i <= 14 &&  i <= chess.getX() + 4 && j >= 0 &&
                     j >= chess.getY() - 4; i++, j--) {
            if (arr[i][j] && chess.getColor() == (colors[i][j])) {
                count++;
            } else {
                break;
            }

        }

        // 左斜下有几个连子
        for (int i = chess.getX() - 1,  j = chess.getY() + 1;
             i >= 0 &&  i >= chess.getX() - 4 && j <= 14 &&
                     j <= chess.getY() + 4; i--, j++) {
            if (arr[i][j] && chess.getColor() == (colors[i][j])) {
                count++;
            } else {
                break;
            }

        }
        // 左斜线是否五连
        if (count >= 5) {
            return true;
        }
        return false;
    }
}
