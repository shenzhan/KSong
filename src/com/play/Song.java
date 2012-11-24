package com.play;

import java.io.*;
public class Song
{
	private String title;
	private String path;
	private String artist;
	private int playCount;
	private double length = 0f;
	public Song(String p, String a, int c){
		path = p;
		artist = a;
		playCount = c;
		try
		{
			File file = new File(path);
			if(!file.exists()) {//文件不存在
				System.out.println("文件不存在");
			} else {
				length = 1.0*file.length()/1024;
				title = file.getName();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public boolean equals(Object aSong){
		Song s = (Song)aSong;
		return path.equals(s.path);
	}
	public int hashCode(){
		return path.hashCode();
	}
	public String getTitle(){
		return title;
	}
	public String getPath(){
		return path;
	}
	public String getArtist(){
		return artist;
	}
	public int getPlayCount(){
		return playCount;
	}
	public String getLength(){
		return String.format("%.2fKB", length);
	}
	public String toString(){
		return title;
	}
	public void addPlayCount(){
		++playCount;
	}
}