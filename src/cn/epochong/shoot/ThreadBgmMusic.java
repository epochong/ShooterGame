package cn.epochong.shoot;

import cn.music.www.Music;

/**
 * @author epochong
 * @date 2019/7/6 0:49
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class ThreadBgmMusic implements Runnable {
    int index = 0;
    int start = 0;

    @Override
    public void run() {

        while (true) {
            Music music = new Music(index);
            music.start();
            index++;
            if (index == 7) {
                try {
                    Thread.sleep(244000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index = 0;
                start = 0;
                continue;
            }

            try {
                if (start == 0) {
                    Thread.sleep(28000);
                } else if (index < 6){
                    Thread.sleep(14000);
                }
                start = 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}