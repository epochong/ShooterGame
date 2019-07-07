package cn.epochong.shoot;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author epochong
 * @date 2019/7/4 14:03
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class Light extends FlyingObject implements Shell{

    /**
     * 移动速度
     */
    public Light(int x, int y) {
        super(101,1000,x,y);
        speed = 3;
    }

    /**
     * 子弹移动
     */
    @Override
    public void step() {
        //子弹向上移动
        //y -= speed;
    }
    private int index = 0;
    @Override
    public BufferedImage getImage() {
        if (isAlive()) {
            return Images.lights[index++ % Images.lights.length];
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
        return true;
    }
}
