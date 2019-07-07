package cn.epochong.shoot;


import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * @author wangchong
 * @date 2019/6/25 10:02
 * @email 876459397@qq.com
 * @blog epochong.github.io
 * @describe 天空
 */
public class Sky extends FlyingObject{


    private int speed;
    /**
     * 第二章张图片的y坐标
     */
    private int y1;
    public Sky() {
        super(World.WIDTH, World.HEIGHT,0,0);
        speed = 1;
        y1 = -World.HEIGHT;
    }


    /**
     * 天空移动
     */
    @Override
    public void step() {
        y += speed;
        y1 += speed;
        //y大于窗口高
        if (y >= World.HEIGHT) {
            //将y移动到最上面
            y = -World.HEIGHT;
        }
        //y1大于窗口高
        if (y1 >= World.HEIGHT) {
            //将y1移动到最上面
            y1 = -World.HEIGHT;
        }
    }

    @Override
    public BufferedImage getImage() {
        return Images.background;
    }

    @Override
    public void paintObject(Graphics graphics) {
        graphics.drawImage(this.getImage(),this.x,this.y,null);
        graphics.drawImage(this.getImage(),this.x,this.y1,null);
    }


}
