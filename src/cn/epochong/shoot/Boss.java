package cn.epochong.shoot;

import java.awt.image.BufferedImage;

/**
 * @author epochong
 * @date 2019/7/7 18:58
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class Boss extends FlyingObject implements Enemy{

    /**
     * 移动速度
     */

    private int speed;

    public Boss() {
        super(48,50);
        speed = 2;
    }

    /**
     * 小敌机移动
     */
    @Override
    public void step() {
        //向下移动speed距离
        y += speed;
    }

    /**
     * 爆照图片的下标从1开始
     */
    int index = 1;
    @Override
    public BufferedImage getImage() {
        if (isAlive()) {
            return Images.airplanes[0];
        } else if (isDead()) {
            /**
             * 死亡状态
             */
            BufferedImage image = Images.airplanes[index++];
            /**
             * index达到最大值要删除对象
             */
            if (index == Images.airplanes.length) {
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
    public int getScore() {
        //打掉小敌机玩家得一分
        return 1;
    }
}
