package gamePlay;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BetterSound extends Thread {
	Clip c;
	boolean loop;

	public BetterSound(File f, boolean runWhenCreated, boolean loop) {
		this.loop = loop;
		try {
			c = AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(f));
		} catch (LineUnavailableException e) {
			System.out.println("ERROR, line unavalible");
		} catch (IOException e) {
			System.out.println("ERROR, IO");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("ERROR, unspported");
		}
		if (runWhenCreated)
			start();
	}

	/**
	 * WHATEVER YOU DO DON'T CALL THIS!!!!!!!!!!!!!!!!! just call something called
	 * start() in the thread class DON'T BE DUMB, DON'T CALL THIS please, I really
	 * mean it!
	 */
	public void run() {
		if(loop)
			c.loop(c.LOOP_CONTINUOUSLY);
		c.start();
	}
}