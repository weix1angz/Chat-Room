package client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MusicThread implements Runnable {

	private AdvancedPlayer player;

	private int pausedOnFrame;
	
	private String path;
	
	private boolean isPlaying;
	
	public boolean isPlaying() {
		return isPlaying;
	}


	public String getPath() {
		return path;
	}
	

	public MusicThread(String name) {
		try {
			path = name;
			pausedOnFrame = 0;
			isPlaying = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			newTrack();
			System.out.println("Playing " + path + " from frame: " + pausedOnFrame);
			isPlaying = true;
			player.play(pausedOnFrame, Integer.MAX_VALUE);
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause() {
		if (player != null)
			player.stop();
	}
	
	private void newTrack() {
		try {
			FileInputStream fis = new FileInputStream(path);
			this.player = new AdvancedPlayer(new BufferedInputStream(fis));
			player.setPlayBackListener(new PlaybackListener() {
				@Override
				public void playbackFinished(PlaybackEvent event) {
					pausedOnFrame = event.getFrame();
					System.out.println("Track: " + path + " paused on frame: " + pausedOnFrame);
					isPlaying = false;
				}
			});
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
