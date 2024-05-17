package com.lhedu.tankgame5;

/**
 * @Author LiuH
 * @Date 2024/5/14 11:20
 * @PackageName:com.lhedu.tankgame4
 * @ClassName:Bomb
 * @Version:
 * @Description:实现坦克爆炸效果
 */

@SuppressWarnings("all")

public class Bomb {
    int x, y;//炸弹的坐标
    int life = 18;//炸弹的生命周期
    boolean isLive = true;//是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //配合实现图片的爆炸效果
    public void lifeDown(){
        if (life > 0){
            life--;
        }else {
            isLive = false;
        }
    }
}
