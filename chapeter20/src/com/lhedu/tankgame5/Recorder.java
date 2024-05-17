package com.lhedu.tankgame5;

import java.io.*;
import java.util.Vector;

/**
 * @Author LiuH
 * @Date 2024/5/17 19:20
 * @PackageName com.lhedu.tankgame5
 * @ClassName Recorder
 * @Version
 * @Description 用于记录相关的信息，和文件交互
 */

@SuppressWarnings("all")

public class Recorder {

    //定义一个变量，我方击毁坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO 对象
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";
    //用于得到敌人的坦克信息
    private static Vector<EnemyTank> enemyTanks = null;

    //定义一个Node 的Vector 用于保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();

    public static String getRecordFile() {
        return recordFile;
    }

    //增加方法，用于读取文件，恢复相关信息
    //该方法在继续上局游戏的时候调用
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件
            String line = "";
            Node node = null;
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                 node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);//放入node Vector
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //当游戏退出时，我们将 allEnemyTankNum 保存到 recorfFile
    public static void keepRecord() throws IOException {
        bw = new BufferedWriter(new FileWriter(recordFile));
        bw.write(allEnemyTankNum + "\r\n");
        //遍历敌人坦克的Vector,然后保存
        //定义一个属性，然后通过set 得到敌人的Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人的坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
            bw.write(record + "\r\n");
        }

        bw.close();
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁一个敌方坦克。就将 allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
