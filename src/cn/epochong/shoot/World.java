package cn.epochong.shoot;
//相框
import cn.music.www.AwardMusic;
import cn.music.www.ExplodeMusic;
import cn.music.www.LightMusic;
import cn.music.www.Shootmusic;

import javax.swing.*;
//相板,面板

import java.awt.*;
import java.awt.event.*;
import java.util.*;
//监听器
//监听事件

/**
 * @author wangchong
 * @date 2019/6/25 10:06
 * @email 876459397@qq.com
 * @blog epochong.github.io
 * @describe 整个窗口,继承相板
 */


public class World extends JPanel{

    /**
     * 窗口宽度
     */
    static final int WIDTH = 400;
    /**
     * 窗口高度
     */
    static final int HEIGHT = 700;
    /**
     * 启动状态
     */
    public static final int START = 0;
    /**
     * 运行状态
     */
    public static final int RUNNING = 1;
    /**
     * 暂停状态
     */
    public static final int PAUSE = 2;
    /**
     * 游戏结束状态
     */
    public static final int GAME_OVER = 3;
    /**
     * 状态:默认为启动状态
     */
    public int state = START;
    private Sky sky = new Sky();
    private Hero hero = new Hero();
    private FlyingObject[] enemies = {

    };
    private Bullet[] bullets = {
            //new Bullet(180,300)
    };

    private BigShell[] bigShells = {};

    private Light[] lights = {};

    /**
     * 生成敌人
     * 控制小蜜蜂的数量比其他少，不然英雄机不那么容易死
     * @return 小敌机、大敌机、蜜蜂对象
     */
    public FlyingObject nextOne() {
        Random random = new Random();
        //用于判断产生什么飞行物产生随机数
        int type = random.nextInt(25);
        /*
         * 0 - 4成成小蜜蜂对象
         * 5 - 11生成小敌机
         * 12 - 19生成大敌机
         */
        if (type < 5 ) {
            return new AwardLife();
        } else if (type < 12) {
            return new Airplane();
        } else if (type < 20){
            return new BigAirplane();
        } else {
            return new AwardFire();
        }
    }

    /**
     * 敌人入场计数器:敌机个数
     */
    private int enterIndex = 0;
    /**
     * 让敌机每10毫秒走一次
     */
    void enterAction() {
        enterIndex++;
        //每400（40 * 10）毫秒调用下一个敌机
        if (enterIndex % 40 == 0) {
            FlyingObject flyingObject = nextOne();
            //每增加一个对象扩容一个
            enemies = Arrays.copyOf(this.enemies, this.enemies.length + 1);
            enemies[enemies.length - 1] = flyingObject;
        }
    }

    /**
     * 天空动,敌人动,子弹动
     */
    void stepAction() {
        //天空动
        sky.step();
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].step();
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
        for (int i = 0; i < bigShells.length; i++) {
            bigShells[i].step();
        }

    }
    /**
     * 子弹入场计数器
     */
    int shootIndex = 0;

    /**
     * 英雄机发射子弹
     */
    void shootAction() {
        //每10秒增1
        shootIndex++;

        //每300秒走一次
        if(shootIndex % 30 == 0) {
            Thread thread = new Thread(new ShootMusicThread("ishoot"));
            thread.start();
            Shootmusic shootmusic = new Shootmusic();
            shootmusic.start();
            Bullet[] bs = hero.shoot();

            //扩容(shoot有几个及扩容几个)
            bullets = Arrays.copyOf(bullets,bullets.length + bs.length);
            /**
             * bs是原始数组bs
             * 0是从原始数组bs第一个元素开始
             * bullets是目标数组
             * bullets.length - bs.length从下标及开始复制
             * bs.length代表是复制几个元素
             */
            System.arraycopy(bs,0,bullets,bullets.length - bs.length,bs.length);
        }

    }

    int bigShellIndex = 0;
    void shootBigShellAction() {
        bigShellIndex++;
        if (bigShellIndex % 60 == 0) {
            BigShell[] bs = hero.shootBigShell();
            bigShells = Arrays.copyOf(bigShells,bigShells.length + bs.length);
            System.arraycopy(bs,0,bigShells,bigShells.length - bs.length,bs.length);
        }
    }

    void shootLightAction() {
        if (hero.isShootLight) {
            //LightMusic lightMusic = new LightMusic();
            //lightMusic.start();
            Light[] li = hero.shootLight();
            //System.out.println("------------------- + " + li.length);
            lights = Arrays.copyOf(lights,lights.length + li.length);
            System.arraycopy(li,0,lights,lights.length - li.length,li.length);
            //System.out.println("*************"+lights.length);
        }
    }

    /**
     * 画图像
     * @param graphics 画笔
     */
    @Override
    public void paint(Graphics graphics){
        sky.paintObject(graphics);
        hero.paintObject(graphics);
        for(int i = 0;i < enemies.length; i++){
            enemies[i].paintObject(graphics);
        }
        for(int i = 0;i < bullets.length; i++){
            bullets[i].paintObject(graphics);
        }
        for (int i = 0; i < bigShells.length; i++) {
            bigShells[i].paintObject(graphics);
        }

        for (int i = 0; i < lights.length; i++) {
            lights[i].paintObject(graphics);

        }
        lights = new Light[0];
        //System.out.println("light:" + lights.length);
        /**
         * 画的字符的内容
         * 距离左边框的距离
         * 距离上边框的距离
         */
        graphics.setColor(Color.PINK);
        graphics.drawString("SCORE:" + score,10,25);
        //画命
        graphics.drawString("LIFE:" + hero.getLife(),10,45);
        switch(state){
            case START:
                graphics.drawImage(Images.start,0,0,null);
                break;
            case PAUSE:
                graphics.drawImage(Images.pause,0,0,null);
                break;
            case GAME_OVER:
                graphics.drawImage(Images.gameover,0,0,null);
                graphics.setColor(Color.MAGENTA);
                graphics.setFont(new Font("楷体",Font.BOLD,26));
                graphics.drawString("排行榜",150,330);
                String[] result;
                try {
                    result = UserDao.getScoreAndUserName();
                    int y = 360;
                    /**
                     * 只显示排名前十的打印排行榜
                     */
                    for (int i = 0; i < 10; i++) {
                        if (result[i] != null) {
                            graphics.setFont(new Font("Dialog",Font.PLAIN,25));
                            graphics.drawString(result[i],80,y);
                            y += 30;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 删除越界的敌人和子弹
     * 只存不越界的越界的就是被删除了
     */
     void outOfBoundsAction() {
        //不越界敌人的个数
        int index = 0;
        //统计不越界的敌人的数组
        FlyingObject[] enemiesLives = new FlyingObject[enemies.length];
        for (int i = 0; i < enemies.length; i++) {
            FlyingObject f = enemies[i];
            //不越界,并且是未删除的(敌人和蜜蜂被子弹打中会被删除)
            if (!f.outOfBounds() && !f.isRemove()) {
                enemiesLives[index] = f;
                index++;
            }
        }
        //把不越界的敌人放到原来的敌人数组中,长度时不越界的敌人的个数
        enemies = Arrays.copyOf(enemiesLives,index);

        //不越界子弹的个数
        index = 0;
        //统计不越界的子弹的数组
        Bullet[] bulletsLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            //不越界,且未删除的子弹
            if (!b.outOfBounds() && !b.isRemove()) {
                bulletsLives[index] = b;
                index++;
            }
        }
        //把不越界的子弹放到原来的子弹数组中,长度时不越界的子弹的个数
        bullets = Arrays.copyOf(bulletsLives,index);

         //不越界子弹的个数
         index = 0;
         //统计不越界的子弹的数组
         BigShell[] bigShellLives = new BigShell[bigShells.length];
         for (int i = 0; i < bigShells.length; i++) {
             BigShell b = bigShells[i];
             //不越界
             if (!b.outOfBounds() && !b.isRemove()) {
                 bigShellLives[index] = b;
                 index++;
             }
         }
         //把不越界的子弹放到原来的子弹数组中,长度时不越界的子弹的个数
         bigShells = Arrays.copyOf(bigShellLives,index);


         //不越界子弹的个数
         index = 0;
         //统计不越界的子弹的数组
         Light[] lightLives = new Light[lights.length];
         for (int i = 0; i < lights.length; i++) {
             Light b = lights[i];
             //不越界
             if (!b.isRemove()) {
                 lightLives[index] = b;
                 index++;
             }
         }
         //把不越界的子弹放到原来的子弹数组中,长度时不越界的子弹的个数
         lights = Arrays.copyOf(lightLives,index);



     }
    int score = 0;
    void bulletBangAction() {
        //每遍历一个子弹遍历所有敌人，判断是否碰撞
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            for (int j = 0; j < enemies.length; j++) {
                FlyingObject f = enemies[j];
                if (b.isAlive() && f.isAlive() && f.hit(b)) {
                    if (f instanceof BigAirplane) {
                        ((BigAirplane) f).life--;
                        if (((BigAirplane) f).life == 0) {
                            f.goDead();
                        }
                    } else {
                        f.goDead();
                    }
                    b.goDead();
                    //派生类是否实现Enemy接口（得分接口）
                    if (f instanceof Enemy) {
                        ExplodeMusic explodeMusic = new ExplodeMusic();
                        explodeMusic.start();
                        Enemy enemy = (Enemy) f;
                        score += enemy.getScore();
                    }
                    //派生类是否实现Award接口（奖励接口）
                    if (f instanceof Award) {
                        AwardMusic awardMusic = new AwardMusic();
                        awardMusic.start();
                        Award award = (Award) f;
                        int type = award.getAwardType();
                        //根据奖励类型给奖励
                        switch(type){
                            //增加火力
                            case Award.DOUBLE_FIRE:
                                hero.addDoubleFire();
                                break;
                            //增加生命
                            case Award.LIFE:

                                hero.addLife();
                                break;
                            default:
                                break;
                        }

                    }

                    //以下代码复用性差
                   /* //enemies包含小蜜蜂，小敌机，大敌机，分数不一样，要根据类型给分数
                    if (f instanceof Airplane) {
                        score += ((Airplane)f).getScore();
                    }
                    if (f instanceof BigAirplane) {
                        score += ((BigAirplane)f).getScore();
                    }
                    if (f instanceof AwardLife) {
                        AwardLife bee = (AwardLife)f;
                        int type = bee.getAwardType();
                        switch(type){
                            case Award.DOUBLE_FIRE:
                                hero.addDoubleFire();
                                break;
                            case Award.LIFE:
                                hero.addLife();
                                break;
                            default:
                                break;
                        }
                    }*/
                }
            }
        }
    }

    public void bigShellBangAction() {
        //每遍历一个子弹遍历所有敌人，判断是否碰撞
        for (int i = 0; i < bigShells.length; i++) {
            BigShell b = bigShells[i];
            for (int j = 0; j < enemies.length; j++) {
                FlyingObject f = enemies[j];
                if (b.isAlive() && f.isAlive() && f.hit(b)) {
                    b.goDead();
                    f.goDead();
                    //派生类是否实现Enemy接口（得分接口）
                    if (f instanceof Enemy) {
                        ExplodeMusic explodeMusic = new ExplodeMusic();
                        explodeMusic.start();
                        Enemy enemy = (Enemy) f;
                        score += enemy.getScore();
                    }
                    //派生类是否实现Award接口（奖励接口）
                    if (f instanceof Award) {
                        AwardMusic awardMusic = new AwardMusic();
                        awardMusic.start();
                        Award bee = (Award) f;
                        int type = bee.getAwardType();
                        //根据奖励类型给奖励
                        switch (type) {
                            //增加火力
                            case Award.DOUBLE_FIRE:
                                hero.addDoubleFire();
                                break;
                            //增加生命
                            case Award.LIFE:
                                hero.addLife();
                                break;
                            default:
                                break;
                        }

                    }

                }
            }
        }
    }


    public void lightsBangAction() {
        //每遍历一个子弹遍历所有敌人，判断是否碰撞
        for (int i = 0; i < lights.length; i++) {
            Light b = lights[i];
            for (int j = 0; j < enemies.length; j++) {
                FlyingObject f = enemies[j];
                if (b.isAlive() && f.isAlive() && f.hit(b)) {
                    b.goDead();
                    f.goDead();
                    //派生类是否实现Enemy接口（得分接口）
                    if (f instanceof Enemy) {
                        ExplodeMusic explodeMusic = new ExplodeMusic();
                        explodeMusic.start();
                        Enemy enemy = (Enemy) f;
                        score += enemy.getScore();
                    }
                    //派生类是否实现Award接口（奖励接口）
                    if (f instanceof Award) {
                        AwardMusic awardMusic = new AwardMusic();
                        awardMusic.start();
                        Award bee = (Award) f;
                        int type = bee.getAwardType();
                        //根据奖励类型给奖励
                        switch (type) {
                            //增加火力
                            case Award.DOUBLE_FIRE:
                                hero.addDoubleFire();
                                break;
                            //增加生命
                            case Award.LIFE:
                                hero.addLife();
                                break;
                            default:
                                break;
                        }

                    }

                }
            }
        }
    }

    public void heroBangAction() {
        //遍历所有敌人
        for (int i = 0; i < enemies.length; i++) {
            FlyingObject enemy = enemies[i];
            //英雄机活着,敌机活着,并且碰撞,然后改变他们的状态
            if (hero.isAlive() && enemy.isAlive() && enemy.hit(hero)) {
                enemy.goDead();
                //英雄机减命,清空火力值
                hero.subtaractLife();
                hero.clearDoubleFire();
            }
        }
    }

    /**
     * 检测游戏结束
     */
    public void checkGameOverAction() {
        if (hero.getLife() <= 0) {
            try {
                UserDao.save(userName,score);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将当前状态修改为游戏结束状态
            state = GAME_OVER;

        }
    }
    /**
     * 所有动作
     */
    private void action() {
        //匿名内部类:只用一次
        MouseAdapter l = new MouseAdapter() {
            /**
             * 鼠标移动事件
             * @param e 鼠标事件
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {

                //获取鼠标的x坐标
                int x = e.getX();
                //获取鼠标的y坐标
                int y = e.getY();

                hero.moveTo(x,y);
                }

            }

            @Override
            public void mouseClicked(MouseEvent e) {


                //根据当前的状态做不同的处理
                switch(state){
                    //启动状态
                    case START:
                        //修改为运行状态
                        state = RUNNING;
                        break;
                    //清零
                    case GAME_OVER:
                        score = 0;
                        sky = new Sky();
                        hero = new Hero();
                        enemies = new FlyingObject[0];
                        bullets = new Bullet[0];
                        bigShells = new BigShell[0];

                        System.gc();
                        //游戏结束状态后又修改为启动状态
                        state = START;
                        break;
                    default:
                        break;
                }
            }

            /**
             * 鼠标移出事件
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                //运行状态时修改为暂停状态
                if (state == RUNNING) {
                    state = PAUSE;
                }
                //System.out.println(state);
            }

            /**
             * 鼠标移入事件
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                //暂停状态变为运行状态
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }

        };
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                hero.isShoot2(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                hero.notShoot2(e);
            }
        };

        /*
         * 事件的处理
         * this表示当前面板
         * 处理鼠标操作事件
         */
        this.addMouseListener(l);
        //处理鼠标滑动事件
        this.addMouseMotionListener(l);
        this.addKeyListener(keyAdapter);

        Thread actionThread = new Thread(new ActionThread(this));
        Thread heroThread = new Thread(new HeroThread(hero));
        Thread bgmThread = new Thread(new BgmMusicThread());

        actionThread.start();
        heroThread.start();
        bgmThread.start();
    }

    public  void reFoucs() {
        this.requestFocus();
    }

    public static String userName = null;
    public static void main (String[] args){

        System.out.print("输入玩家名:");
        Scanner input = new Scanner(System.in);
        userName = input.next();
        //System.out.println("游戏开始！");
        System.out.println("点击游戏界面开始游戏！");
        JFrame jFrame = new JFrame();
        World world = new World();
        jFrame.add(world);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        jFrame.setSize(WIDTH, HEIGHT);
        //如果组件当前未显示,或者为null,则此窗口将会显示在屏幕的中央
        jFrame.setLocationRelativeTo(null);
        //是否可见、调用paint方法(上面重写的方法)

        jFrame.setSize(400, 700);
        //如果组件当前未显示,或者为null,则此窗口将会显示在屏幕的中央
        jFrame.setLocationRelativeTo(null);
        //是否可见

        jFrame.setVisible(true);
        world.action();
    }




}
