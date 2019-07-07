package cn.epochong.shoot;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author epochong
 * @date 2019/7/4 15:39
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */


public class GameUtil {
    // 工具类最好将构造器私有化。
    private GameUtil() {

    }

    public static Image getImage(String path) {
        BufferedImage bi = null;
        try {
            URL u = GameUtil.class.getClassLoader().getResource(path);
            bi = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }
}