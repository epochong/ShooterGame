package cn.epochong.shoot;

import java.awt.image.BufferedImage;

/**
 * @author epochong
 * @date 2019/7/6 20:11
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class AwardFire extends FlyingObject implements Award {

    /**
     * x坐标移动速度
     */
    private int xSpeed;
    /**
     * y坐标移动速度
     */
    private int ySpeed;



    public AwardFire() {
        super(46,30);
        xSpeed = 1;
        ySpeed = 2;
    }

    /**
     * 小蜜蜂移动
     */
    @Override
    public void step() {
        x += xSpeed;
        y += ySpeed;
        //碰到左右两边墙的时候切换方向
        if (x <= 0 || x >= World.WIDTH - this.width) {
            xSpeed *= -1;
        }
    }
    /**
     * 爆照图片的下标从1开始
     */
    private int index = 1;
    @Override
    public BufferedImage getImage() {
        if (isAlive()) {
            return Images.fires[0];
        } else if (isDead()) {
            /**
             * 死亡状态
             */
            BufferedImage image = Images.fires[index++];
            /**
             * index达到最大值要删除对象
             */
            if (index == Images.fires.length) {
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
    public int getAwardType() {
        return 0;
    }
}
