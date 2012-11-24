package com.play;

import javax.swing.plaf.SliderUI;

public class MiniMusicPlayer
{
	public static void main(String[] args){
		   
		 new MiniMusicPlayer().go();
			//mp3.play();
	}
	public void go(){
		MP3Player mp3;
		
			mp3 = new MP3Player("F:\\KwDownloadx\\D1.MID");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			mp3.stopPlay();
	}
}