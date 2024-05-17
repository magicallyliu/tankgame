package com.lhedu.tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大战的绘图区域
 */
@SuppressWarnings("all")
//为了让Panel 不停的重绘子弹，需要将 MyPanel 类实现 Runnable，当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人的坦克
    Vector<EnemyTank> enemiesTanks = new Vector();
    //定义一个存放Node 对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector ，用于存放炸弹
    //当子弹击中坦克时，就加入一个 bomb对象
    Vector<Bomb> bombs = new Vector<>();
    //设置敌方坦克数量
    int enemyTankSize = 5;

    //定义三种炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //初始化坦克
    public MyPanel(String key) {

        //判断记录的文件是否存在
        //如果存在，就正常执行，如果不存在， 提示，只能开启新游戏 key = 1
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("文件不存在，只能开启新游戏");
            key = "1";
        }
        //将myPanel 对象 enemyTanks 设置给 pecorder 对象
        Recorder.setEnemyTanks(enemiesTanks);
        hero = new Hero(700, 100);//初始化我的坦克位置

        switch (key) {
            case "1":
                //初始化敌人的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //初始化摧毁数
                    Recorder.setAllEnemyTankNum(0);
                    //创建敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将 enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemiesTanks);
                    enemyTank.setDirect((int) (Math.random() * 4));
                    //启动线程，让坦克动起来
                    new Thread(enemyTank).start();

                    //同时创建子弹
                    Shot shot = new Shot(enemyTank.getX() + 18, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    //加入
                    enemiesTanks.add(enemyTank);
                }
                break;
            case "2"://继续上局游戏
                //初始化敌人的坦克

                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将 enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemiesTanks);
                    enemyTank.setDirect(node.getDirect());
                    //启动线程，让坦克动起来
                    new Thread(enemyTank).start();

                    //同时创建子弹
                    Shot shot = new Shot(node.getX() + 18, node.getY() + 60, node.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    //加入
                    enemiesTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
                break;
        }


        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb3.gif"));

    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {

        //画出玩家总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("摧毁敌方坦克数量", 1020, 30);
        drawTank(1020, 60, g, 0, 1);//画出敌方坦克
        g.setColor(Color.black);
        //g.drawString("0",1080,100);
        g.drawString(String.valueOf(Recorder.getAllEnemyTankNum()), 1080, 100);
    }

    //设计坦克，画出子弹等
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认是黑色
        showInfo(g);

        //画出hero射击的子弹
//        if (hero.shot != null && hero.shot.getIsLive() == true) {
//            g.setColor(Color.cyan);
//            g.draw3DRect(hero.shot.getX(), hero.shot.getY(), 3, 3, false);
//        }
        //将hero 的子弹集合遍历取出
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.getIsLive() == true) {
                g.setColor(Color.cyan);//改变颜色
                g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
            } else {//如果该hero对象的子弹以及销毁，就将其从集合移除
                hero.shots.remove(shot);
            }
        }

        //如果bombs 集合有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前对象的 life 值判断对应的图片
            if (bomb.life > 12) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 6) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //休眠10 毫秒，让爆炸效果展示
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bomb.lifeDown();
            //如果bome.life 为0 就退出循环
            if (bomb.life == 0) {
                bombs.remove(bomb);
                break;
            }
        }
        //画出自己的坦克-封装方法,遍历Vector 方法
        //如果 有坦克，并且没有被敌方坦克击中
        if (hero != null && hero.getIsLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        //画出敌人的坦克
        for (int i = 0; i < enemiesTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemiesTanks.get(i);
            //判断当前坦克是否还存活
            if (enemyTank.getIsLive() == true) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                //enemyTankMove();
                //画出 enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.getIsLive() == true) {
                        g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
                    } else {
                        //从Vector 移除
                        enemyTank.shots.remove(shot);
                    }
                }
            } else {//当坦克不存活时，把坦克从集合中删去
                enemiesTanks.remove(enemyTank);
            }
        }
    }

    /**
     * @param x      坦克左上角横坐标
     * @param y      坦克左上角纵坐标
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克的类型
     */
    //编写方法，画出坦克
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
//        this.x = x;
//        this.y = y;
        //判断类型,给不同坦克设置颜色
        switch (type) {
            case 0://自己的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，来绘制坦克
        //direct 表示方向(0:向上 1:向右 2:向下 3:向左)
        //
        switch (direct) {
            case 0://向上的方向
                Fill3DRect0(x, y, g, 10, 60, false);
                break;
            case 1://向右
                Fill3DRect1(x, y, g, 60, 10, false);
                break;
            case 2://向下
                Fill3DRect2(x, y, g, 10, 60, false);
                break;
            case 3://向左
                Fill3DRect3(x, y, g, 60, 10, false);
                break;


        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
//
//    //编写方法，让敌人的坦克开始移动
//    public void enemyTankMove() {
//        int direct = (int) (Math.random() * 4);
//
//        for (int i = 0; i < enemiesTanks.size(); i++) {
//
//            switch (direct) {
//                case 0:
//
//                    enemiesTanks.get(i).moveUp();
//                    enemiesTanks.get(i).setDirect(0);
//                    break;
//                case 1:
//                    enemiesTanks.get(i).moveRight();
//                    enemiesTanks.get(i).setDirect(1);
//                    break;
//                case 2:
//                    enemiesTanks.get(i).moveDown();
//                    enemiesTanks.get(i).setDirect(2);
//                    break;
//                case 3:
//                    enemiesTanks.get(i).moveLeft();
//                    enemiesTanks.get(i).setDirect(3);
//                    break;
//            }
//        }
//    }

    @Override
    public void keyPressed(KeyEvent e) {//监听键盘，从而使坦克移动（wasd)和改变方向
        //如果 被敌方坦克击中
        if ((hero.getIsLive())) {
            if (e.getKeyCode() == KeyEvent.VK_S) {//KeyEvent.VK_DOWN 向下的箭头对应的code
                hero.setDirect(2);
                if (hero.getY() + 100 < 750) {
                    hero.moveDown();
                }

            } else if (e.getKeyCode() == KeyEvent.VK_W) {//向上的箭头
                hero.setDirect(0);
                if (hero.getY() > 0) {
                    hero.moveUp();

                }

            } else if (e.getKeyCode() == KeyEvent.VK_A) {//向左
                hero.setDirect(3);
                if (hero.getX() - 10 > 0) {
                    hero.moveLeft();
                }

            } else if (e.getKeyCode() == KeyEvent.VK_D) {//向右边
                hero.setDirect(1);
                if (hero.getX() + 60 < 1000) {
                    hero.moveRight();
                }

            }

            //如果用户按下 J，就发射子弹
            if (e.getKeyCode() == KeyEvent.VK_J) {

                // 判断hero 的子弹是否被销毁,发射一颗子弹
//            if (hero.shot == null || !hero.shot.getIsLive()) {
//                hero.shotEnemyTank();
//            }

                // 判断hero 的子弹是否被销毁,发射多颗子弹
                //如果 有坦克，并且没有被敌方坦克击中

                hero.shotEnemyTank();


            }
        }
        //重绘面板
        this.repaint();
    }

    //判断敌人坦克是否击中我的坦克
    public void hiHero() {
        //遍历所有的坦克
        for (int i = 0; i < enemiesTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemiesTanks.get(i);
            //遍历enemyTank 对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断是否击中我的坦克
                if (hero.getIsLive() && shot.getIsLive()) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //如果坦克可以发射多个子弹
    //就应该把集合中的子弹都取出来
    //和敌人的所有坦克进行判断
    public void hitEnemyTank() {

        //遍历所有的子弹
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断子弹是否击中敌人的坦克
            if (hero.shot != null && hero.shot.getIsLive()) {//判断子弹是否存活
                for (int i = 0; i < enemiesTanks.size(); i++) {
                    hitTank(hero.shot, enemiesTanks.get(i));
                }
            }
        }

    }

    //编写方法，判断我方子弹是否击中敌方坦克
    //在run  方法判断子弹是否击中坦克
    public void hitTank(Shot s, Tank tank) {


        //判断 s 击中坦克 (0:向上 1:向右 2:向下 3:向左)
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.getX() > tank.getX() - 3 && s.getX() < tank.getX() + 37
                        && s.getY() > tank.getY() - 3 && s.getY() < tank.getY() + 57) {
                    s.setIsLive(false);
                    tank.setIsLive(false);
                    //当子弹命中敌方坦克后，将其从 Vector拿掉
                    enemiesTanks.remove(tank);
                    //命中后给自己的击毁敌方坦克记录加一
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建 Bomb 对象，加入到 bombs 集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (s.getX() > tank.getX() - 3 && s.getX() < tank.getX() + 57
                        && s.getY() > tank.getY() - 3 && s.getY() < tank.getY() + 37) {
                    s.setIsLive(false);
                    tank.setIsLive(false);
                    //当子弹命中敌方坦克后，将其从 Vector拿掉
                    enemiesTanks.remove(tank);
                    if (tank instanceof EnemyTank) {
                        //命中后给自己的击毁敌方坦克记录加一
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //绘制坦克的方法
    private void Fill3DRect0(int x, int y, Graphics g, int width, int height, boolean raised) {
        g.fill3DRect(x, y, width, height, false);//左边轮子
        g.fill3DRect(x + 30, y, width, height, false);//右边轮子
        g.fill3DRect(x + 10, y + 10, 20, 40, false);//盖子
        g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
        g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
    }

    private void Fill3DRect1(int x, int y, Graphics g, int width, int height, boolean raised) {
        g.fill3DRect(x - 10, y + 10, width, height, false);//左边轮子
        g.fill3DRect(x - 10, y + 40, width, height, false);//右边轮子
        g.fill3DRect(x, y + 20, 40, 20, false);//盖子
        g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
        g.drawLine(x + 20, y + 30, x + 50, y + 30);//画出炮筒
    }

    private void Fill3DRect2(int x, int y, Graphics g, int width, int height, boolean raised) {
        g.fill3DRect(x, y, width, height, false);//左边轮子
        g.fill3DRect(x + 30, y, width, height, false);//右边轮子
        g.fill3DRect(x + 10, y + 10, 20, 40, false);//盖子
        g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
        g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
    }

    private void Fill3DRect3(int x, int y, Graphics g, int width, int height, boolean raised) {
        g.fill3DRect(x - 10, y + 10, width, height, false);//左边轮子
        g.fill3DRect(x - 10, y + 40, width, height, false);//右边轮子
        g.fill3DRect(x, y + 20, 40, 20, false);//盖子
        g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
        g.drawLine(x + 20, y + 30, x - 10, y + 30);//画出炮筒
    }

    //使用线程让Panel 不停的重绘子弹
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //判断我的多个子弹是否命中敌人的坦克
            hitEnemyTank();
            //判断敌人是否击中我
            hiHero();
            //重新绘制
            this.repaint();
//            //如果 被敌方坦克击中
//            if (!(hero.getIsLive())) {
//
//                break;
//            }
        }
    }
}
