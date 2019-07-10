package cn.epochong.shoot;

import java.awt.image.BufferedImage;

/**
 * @author epochong
 * @date 2019/7/8 21:33
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class BulletEnemy extends FlyingObject implements Enemy {
    /**
     * 移动速度
     */
    private int speed;

    int life = 3;
    public BulletEnemy(int x, int y) {
        super(8,20,x,y);
        speed = 4;
    }

    /**
     * 爆照图片的下标从1开始
     */
    int index = 1;
    @Override
    public BufferedImage getImage() {
        if (isAlive()) {
            return Images.bulletEnemy[0];
        } else if (isDead()) {
            /**
             * 死亡状态
             */
            BufferedImage image = Images.bulletEnemy[index++];
            /**
             * index达到最大值要删除对象
             */
            if (index == Images.bulletEnemy.length) {
                state = REMOVE;
            }
            return image;
        }
        /**
         * 删除状态,直接返回null
         */
        return null;
    }

    @Override
    public BulletEnemy[] shoot() {
        return new BulletEnemy[0];
    }

    @Override
    public void step() {
        //向下移动
        y += speed;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
