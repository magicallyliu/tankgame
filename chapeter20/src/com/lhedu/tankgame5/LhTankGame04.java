package com.lhedu.tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class LhTankGame04 extends JFrame {

    //定义一个MyPanel面板
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    //用于判断新游戏还是继续上局游戏
    public static void main(String[] args) {


        LhTankGame04 lhTankGame01 = new LhTankGame04();


    }

    public LhTankGame04() {//在构造器中完成面板的初始化
        System.out.println("请输入选择 1: 新游戏 2: 继续上局游戏");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp 放入Thread ,并启用
        new Thread(mp).start();
        this.add(mp);//把面板（加载游戏的绘图区域）
        this.setSize(1250,750);//定义面板的大小
        this.addKeyListener(mp);//获取监听
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//能够同时关闭方法
        this.setVisible(true);//显示出画面

        //在JFrame 增加相关关闭的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);//退出程序
            }
        });
    }

}
