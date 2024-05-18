package com.lhedu.tankgame5;

/**
 * @Author LiuH
 * @Date 2024/5/17 20:21
 * @PackageName com.lhedu.tankgame5
 * @ClassName Nodw
 * @Version
 * @Description 表示坦克的信息
 */

@SuppressWarnings("all")

public class Node {
    private int x;
    private int y;
    private int direct;

    public Node(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
