package cn.epochong.shoot;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/**
 * @author wangchong
 * @date 2019/6/25 10:01
 * @email 876459397@qq.com
 * @blog epochong.github.io
 * @describe
 */
public class Hero extends FlyingObject{

    /**
     * 生命
     */
    private int life;
    /**
     * 火力值
     */
    private int doubleFire;


    public Hero() {
        super(97,139,140,400);
        /**
         * 初始值3条命
         */
        life = 3;
        doubleFire = 0;
    }

    /**
     * 英雄机切换图片
     * 英雄机跟着鼠标移动 moveTo()
     * 英雄机已经实现切换图片的方法 getImage()
     *
     * 空方法防止报错,因为这个父类抽象方法
     */
    @Override
    public void step() {

    }

    /**
     * 英雄机随之鼠标移动
     * 所以英雄机的坐标要根据鼠标的坐标改变
     * 要求鼠标的坐标在英雄机的中央
     * @param x 鼠标的x坐标
     * @param y 鼠标的y坐标
     */
    void moveTo(int x, int y) {
        //英雄机的x坐标 = 鼠标的x - (英雄机的宽) / 2
        this.x = x -this.width / 2;
        //英雄机的y坐标 = 鼠标的y - (英雄机的宽) / 2
        this.y = y - this.height / 2;

    }

    /**
     * 英雄机下标从0开始
     */
    private int index = 0;
    @Override
    public BufferedImage getImage() {

        if (isAlive()) {
            //取余划分
            return Images.heros[index++ % Images.heros.length];
        }
        return null;
    }

    public Bullet[] shoot() {
         //英雄机的四分之一作为横方向的一小步
        int xStep = this.width / 3;

        int yStep = 6;
        if (doubleFire >= 0 && doubleFire < 5){
            /**
             * 单倍火力
             */
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(this.x + 2 * xStep - 36,this.y - yStep);
            return bullets;
        } else if (doubleFire < 10 && doubleFire >= 5|| doubleFire < 20 && doubleFire >= 15) {
            //2倍火力
            Bullet[] bullets = new Bullet[2];
            bullets[0] = new Bullet(this.x - 20,this.y - yStep);
            bullets[1] = new Bullet(this.x + 3 *  xStep - 20,this.y - yStep);


           /* for (int i = 0; i < bullets.length; i++) {
                bullets[i] = new Bullet(this.x + (i + 1) * xStep,this.y - yStep);
            }

*/
            /*//无限火力
            Bullet[] bullets = new Bullet[20];
            for (int i = 0; i < bullets.length; i++) {
                bullets[i] = new Bullet(i * xStep,this.y - yStep);
            }*/
            //doubleFire -= 2;
            return bullets;
        } else  if (doubleFire >= 10 && doubleFire < 15){
            Bullet[] bullets = new Bullet[3];
            bullets[0] = new Bullet(this.x -20,this.y - yStep);
            bullets[1] = new Bullet(this.x + 2 *  xStep - 36,this.y - yStep);
            bullets[2] = new Bullet(this.x + 3 *  xStep - 20,this.y - yStep);
            return bullets;
        } else {
            Bullet[] bullets = new Bullet[0];
            return bullets;
        }
    }

    public BigShell[] shootBigShell() {
        //英雄机的四分之一作为横方向的一小步
        int xStep = this.width / 3;

        int yStep = 10;
        if (doubleFire < 15){
            BigShell[] bigShells = new BigShell[0];
            //  bigShells[0] = new BigShell(this.x + 2 * xStep,this.y - 15 * yStep);
            return bigShells;
        }else if (doubleFire < 20) {
            //多倍火力
            BigShell[] bigShells = new BigShell[1];
            bigShells[0] = new BigShell(this.x + 2 * xStep - 33,this.y - 15 * yStep);
            return bigShells;
        } else if (doubleFire < 25){
            BigShell[] shellBigs = new BigShell[2];

            shellBigs[0] = new BigShell(this.x ,this.y - 15 * yStep);
            shellBigs[1] = new BigShell(this.x + 3  * xStep ,this.y - 15 * yStep);

            return shellBigs;
        } else {
            BigShell[] shellBigs = new BigShell[3];
            for (int i = 0; i < shellBigs.length; i++) {
                shellBigs[i] = new BigShell(this.x + i * xStep,this.y - 15 * yStep);
            }
            return shellBigs;
        }
    }

    public Light[] shootLight() {
        int xStep = this.width / 3;
        Light[] lights = new Light[6];
        for (int i = 0; i < lights.length; i++) {
            lights[i] = new Light(this.x - 5,this.y - 960);
        }
        doubleFire = 0;
        return lights;
    }
    /**
     * 减命
     */
    public void subtaractLife() {
        life--;
    }

    /**
     * 英雄机增命
     */
    public void addLife() {
        //命数自增1
        life++;
    }

    /**
     * 获取英雄机命数
     * @return 英雄机命数
     */
    public int getLife() {
        return life;
    }

    /**
     * 英雄机增加火力
     */
    public void addDoubleFire() {
        doubleFire += 5;
        //System.out.println(doubleFire);
    }

    /**
     * 英雄机碰撞的时候
     * 火力清零
     */
    public void clearDoubleFire() {
        doubleFire = 0;
    }



    boolean  shoot, isShootLight;


    public void isShoot2(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S) {
            isShootLight = true;
        }
    }
    public void notShoot2(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S) {
            isShootLight = false;
        }
    }


}
