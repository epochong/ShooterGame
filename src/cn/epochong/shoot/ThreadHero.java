package cn.epochong.shoot;

/**
 * @author epochong
 * @date 2019/7/3 17:33
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class ThreadHero implements Runnable {
    Hero hero;

    public ThreadHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //this.hero.moveTo((int) (Math.random() * World.WIDTH),World.HEIGHT - 100);
        }
    }
}
