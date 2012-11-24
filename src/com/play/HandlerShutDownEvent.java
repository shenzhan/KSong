package com.play;

import java.io.*;
import java.util.*;
class HandlerShutDownEvent
{
	public HandlerShutDownEvent(){
		doShutDownWork();
	}
	private void doShutDownWork(){
		Runtime.getRuntime().addShutdownHook(
			new Thread(){
				public void run(){
					try
					{
						FileWriter writer = new FileWriter(MP3Player.SONG_LIST);
						System.out.println("正在保存文件");
						Iterator it = MP3Player.songList.iterator();
						while (it.hasNext())
						{
							Song s = (Song)it.next();
							writer.write(s.getPath()
								+ MP3Player.SPLIT_STRING + s.getArtist()
								+ MP3Player.SPLIT_STRING + s.getPlayCount() + "\n");
						}
						writer.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
		});
	}
}