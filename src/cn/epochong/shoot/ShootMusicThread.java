package cn.epochong.shoot;

import cn.music.www.Shootmusic;

/**
 * @author epochong
 * @date 2019/7/6 17:56
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class ShootMusicThread implements Runnable {
    String type;

    public ShootMusicThread(String type) {
        this.type = type;
    }

    @Override
    public void run() {
        //Shootmusic shootmusic = new Shootmusic(type);
       // shootmusic.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
