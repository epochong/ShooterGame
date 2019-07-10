package cn.music.www;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 背景音乐
 */
public class Music extends Thread {
	int index;
	public Music(int index) {
		this.index = index;
	}

	@Override
	public void run() {
		try {
			//将文件从硬盘加载到内存
			FileInputStream fil1 = 
					new FileInputStream("./src/Music/bgm" + index +".mp3");
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