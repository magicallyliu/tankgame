package com.lhedu.tankgame5;

import java.util.Vector;

/**
 * 自己的坦克
 */
@SuppressWarnings("all")
public class Hero extends Tank {

    //定义一个Shot对象，表示一个射击（线程）
    Shot shot = null;
    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {
        //每次最多发射7个
        if (shots.size() == 7){
            return;
        }
        //创建一个Shot 对象，根据当前hero 对象的位置来创建
        //(0:向上 1:向右 2:向下 3:向左)
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 18, getY() - 3, getDirect());
                break;
            case 1:
                shot = new Shot(getX() + 50, getY() + 28, getDirect());
                break;
            case 2:
                shot = new Shot(getX() + 18, getY() + 60, getDirect());
                break;
            case 3:
                shot = new Shot(getX() - 13, getY()  + 28, getDirect());
                break;
        }
        //将新创建的子弹放入 Vector.Shot集合 中
        shots.add(shot);

        //启动 shot 线程
        new Thread(shot).start();
    }



    //移动属性

}
