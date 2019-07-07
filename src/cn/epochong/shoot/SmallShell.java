package cn.epochong.shoot;

import java.awt.image.BufferedImage;

/**
 * @author epochong
 * @date 2019/7/4 13:28
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class SmallShell extends FlyingObject {
    /**
     * 移动速度
     */
    private int speed;

    public SmallShell(int x, int y) {
        super(39,41,x,y);
        speed = 3;

    }

    /**
     * 子弹移动
     */
    @Override
    public void step() {
        //子弹向上移动
        y -= speed;
    }

    @Override
    public BufferedImage getImage() {
        if (isAlive()) {
            return Images.smallShell;
        } else if (isDead()) {
            state = REMOVE;
        }
        /**
         * 死了和删除都没有图片
         */
        return null;
    }

    /**
     * 判断子弹是否越界
     * @return 越界返回true,未越界返回false
     */
    @Override
    public boolean outOfBounds() {
        //子弹坐标小于的自己负的高,就完全出去了
        return this.y <= -this.height;
    }
}
