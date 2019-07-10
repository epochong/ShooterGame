package cn.epochong.shoot;

/**
 * @author epochong
 * @date 2019/7/3 23:00
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class ThreadAction implements Runnable{
    World world;

    public ThreadAction(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (world.state == World.RUNNING) {
                world.reFoucs();
                world.enterAction();
                world.shootAction();
                world.shootBigShellAction();
                world.shootLightAction();
                world.stepAction();
                world.outOfBoundsAction();
                world.bulletBangAction();
                world.bigShellBangAction();
                world.lightsBangAction();
                world.heroBangAction();
                world.checkGameOverAction();
            }
            world.repaint();
        }
    }
}
