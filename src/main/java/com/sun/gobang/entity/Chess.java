package com.sun.gobang.entity;

import java.awt.*;

/**
 * 棋子类
 * @author sunkai
 * @since 2021/11/5 2:41 下午
 */
public class Chess {
    // 棋子在棋盘上的x轴坐标
    private int x;
    // 棋子在棋盘上的y轴坐标
    private int y;
    // 棋子颜色  1：黑   2：白
    private int color;

    public Chess(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
