package cn.music.www;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class AwardMusic extends Thread{
	@Override
	public void run() {
		try {
			FileInputStream fil1 = 
					new FileInputStream("./src/Music/award.mp3"); 
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