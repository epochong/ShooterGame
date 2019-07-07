package cn.music.www;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author epochong
 * @date 2019/7/6 18:16
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class LightMusic extends Thread {
    @Override
    public void run() {
        try {
            FileInputStream fil1 =
                    new FileInputStream("./src/Music/light.mp3");
            System.out.println("----------");
            BufferedInputStream bis1 =
                    new BufferedInputStream(fil1);
            Player p = new Player(bis1);
            p.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
