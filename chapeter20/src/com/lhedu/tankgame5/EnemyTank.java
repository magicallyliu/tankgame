package com.lhedu.tankgame5;

import java.util.Vector;

/**
 * @Author LiuH
 * @Date 2024/5/12 16:56
 * @PackageName:com.lhedu.tankgame2
 * @ClassName:Enemy
 * @Description:敌人的坦克属性（制式）
 */

@SuppressWarnings("all")


public class EnemyTank extends Tank implements Runnable {
    //在敌人坦克类使用 Vector 保存多个 Shot子弹
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank 可以得到敌人坦克的Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    private boolean isLive = true;//判断坦克是否存活
    int count = 30;//坦克可以同时发射的子弹数量

    //提供一个方法，将 MyPanal 的成员 Vector<EnemyTank> enemyTAnke = new Vector<>()
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，哦按段当前敌人坦克，是否和 enemyTank 中的其他坦克重叠或者碰撞
    public boolean isTouchEnemyTank() {

        //判断当前敌人坦克的方向
        switch (this.getDirect()) {
            case 0://上
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上 下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //判断是否会碰撞 左上角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //判断是否会碰撞 右上角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人坦克是 左 右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //判断是否碰撞 左上角
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //判断是否会碰撞 右上角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上 下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //判断是否会碰撞 右上角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //判断是否会碰撞 右下角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人坦克是 左 右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //判断是否会碰撞 右上角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //判断是否会碰撞 右下角坐标
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上 下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //判断是否会碰撞 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //判断是否会碰撞 右下角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人坦克是 左 右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //判断是否会碰撞 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //判断是否会碰撞 右下角坐标
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上 下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //判断是否会碰撞 左上角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //判断是否会碰撞 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人坦克是 左 右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //判断是否会碰撞 左下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //判断是否会碰撞 右下角坐标
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }


    @Override
    public void run() {
        while (true) {

            //判断如果shot szie() = 0;创建一颗子弹，放入到
            //shots集合，并启动
            if (isLive && shots.size() <= count) {

                Shot s = null;

                //判断坦克方向， 创建对应子弹 (0:向上 1:向右 2:向下 3:向左)
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20 - 2, getY() - 3, 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 50, getY() + 30 - 2, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20 - 2, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX() - 10 - 3, getY() + 30 - 2, 3);
                }
                shots.add(s);
                new Thread(s).start();
            }
            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0:
                    //让坦克保持一个方向走一会
                    for (int i = 0; i < 50; i++) {
                        if (!(getY() > 0) || isTouchEnemyTank()) {
                            break;
                        }
                        moveUp();
                        //休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:

                    for (int i = 0; i < 50; i++) {
                        if (!(getX() + 60 < 1000) || isTouchEnemyTank()) {
                            break;
                        }
                        moveRight();
                        //休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:

                    for (int i = 0; i < 50; i++) {
                        if (!(getY() + 100 < 750) || isTouchEnemyTank()) {
                            break;
                        }
                        moveDown();
                        //休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:

                    for (int i = 0; i < 50; i++) {
                        if (!(getX() - 10 > 0) || isTouchEnemyTank()) {
                            break;
                        }
                        moveLeft();
                        //休眠50毫秒
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            //随机改变方向

            setDirect((int) (Math.random() * 4));

            //写多线程 ，要考虑什么时候结束
            if (!isLive) {
                break;
            }
        }
    }
}
