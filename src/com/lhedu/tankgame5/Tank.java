package com.lhedu.tankgame5;

//坦克的属性
public class Tank {

    private int x;//坦克横坐标
    private int y;//坦克纵坐标
     private int speed = 2 ;//移动属性
    //坦克的朝向 (0:向上 1:向右 2:向下 3:向左)
    private int direct;
    private boolean isLive = true;//判断坦克是否存活

    //上下左右的移动方法a
    public void moveUp() {
        y -=speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public Tank(int x, int y, int speed, int direct) {
        this.x = x;
        this.y = y;
        this.speed = speed;
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
}
