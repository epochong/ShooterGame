package cn.epochong.shoot;


import java.awt.*;
import java.awt.image.BufferedImage;


import java.util.Random;

/**
 * @author wangchong
 * @date 2019/6/26 9:31
 * @email 876459397@qq.com
 * @blog epochong.github.io
 * @describe 飞行抽象类
 */
public abstract class FlyingObject {

    /**
     * 活着的状态
     */
    public static final int ALIVE = 0;
    /**
     * 死亡状态
     */
    public static final int DEAD = 1;
    /**
     * 删除的状态
     */
    public static final int REMOVE = 2;
    /**
     * 当前状态 默认为活着的状态
     */
    protected int state = ALIVE;

    /**
     * 对象图片像素的高
     */
    protected int height;

    /**
     * 对象图片像素的宽
     */
    protected int width;
    /**
     * 对象在窗口的横坐标
     */
    protected int x;
    /**
     * 对象在窗口的纵坐标
     */
    protected int y;
    /**
     * 敌机的构造方法
     * @param width 宽度
     * @param height 高度
     */
    public FlyingObject(int width, int height) {
        this.width = width;
        this.height = height;
        Random random = new Random();
        //x坐标为(窗口宽度 - 敌人的的宽度)之间的随机数
        x = random.nextInt(World.WIDTH - this.width);
        //负的敌人的高
        y = this.height;
        //y = 0;
        x = random.nextInt(World.WIDTH - this.width);
        //负的敌人的高
        y = -this.height;
    }

    /**
     * 英雄机,子弹,天空的构造方法
     * @param height 高度
     * @param width 宽度
     * @param x x坐标
     * @param y y坐标
     */
    public FlyingObject(int width, int height, int x, int y) {

        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;

    }

    /**
     * 移动速度
     * 此处写public访问权限后,子类访问权限必须大于超类的访问权限
     * 默认为default
     */
    public abstract void step();

    /**
     * 获取对象图片
     * @return 对象的图片
     */
    public abstract BufferedImage getImage();
    public boolean isAlive() {
        /**
         * 当前状态为ALIVE表示当前对象是活着的
         */
        return state == ALIVE;
    }
    public boolean isDead() {
        /**
         * 当前状态为DEAD表示当前对象是死亡的
         */
        return state == DEAD;
    }
    public boolean isRemove() {
        /**
         * 当前状态为ALIVE表示当前对象是删除的
         */
        return state == REMOVE;
    }

    /**
     * 画笔、画对象
     * @param graphics 画笔
     */
    public void paintObject(Graphics graphics) {
        graphics.drawImage(this.getImage(),this.x,this.y,null);
    }

    /**
     * 判断敌人是否越界
     * @return
     */
    public boolean outOfBounds() {
        //敌人坐标大于窗口的高,就完全出去了
        return this.y >= World.HEIGHT;
    }

    /**
     * 碰撞检测
     * this是敌人
     * @param other 子弹/英雄机
     * @return
     */
    public boolean hit(FlyingObject other) {
        //x1:敌人的x-子弹英雄机的宽
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;
        //x 在 x1 与 x2 之间， 并且 y 在 y1 与 y2 之间，即为撞上了
        return x >= x1 && x <= x2 && y <= y2 && y >= y1;
    }

    /**
     * 飞行物去死
     */
    public void  goDead() {
        //将当前状态修改为DEAD
        state = DEAD;
    }


    int speed;

}
