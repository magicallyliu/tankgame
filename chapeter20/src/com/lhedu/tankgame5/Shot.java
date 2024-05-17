package com.lhedu.tankgame5;

/**
 * @Author LiuH
 * @Date 2024/5/13 23:23
 * @PackageName:com.lhedu.tankgame3
 * @ClassName:Shot
 * @Version:3.0
 * @Description:设置坦克发射的子弹
 */

@SuppressWarnings("all")

public class Shot implements Runnable {
    //子弹的坐标
    private int x;
    private int y;
    private int direct;//子弹的方向,默认向上 (0:向上 1:向右 2:向下 3:向左)
    private int speed = 3;//子弹的速度
    private boolean isLive = true;//子弹是否还存活

    //构造器
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override//射击行为
    public void run() {
        while (true) {

            //线程休眠 50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向，改变坐标  (0:向上 1:向右 2:向下 3:向左)

            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }



            //当子弹碰到面板边界时，就销毁子弹
            //当子弹碰到敌人坦克后，也结束线程
            if (isLive == false){
                isLive = false;
                break;
            }
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
                isLive = false;
                break;
            }

        }

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }
}
