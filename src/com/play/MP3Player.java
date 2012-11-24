package com.play;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import java.util.*;

public class MP3Player
{
	public static final String SONG_LIST = "SongList.txt";
	public static final String SPLIT_STRING = ";";
	public static final int SORT_BY_TITLE = 0;//按标题排序
	public static final int SORT_BY_ARTIST = 1;//按作家排序
	public static final int SORT_BY_PLAY_COUNT = 2;//按播放次数排序
	static String fileName = "未命名";//文件名
	static AudioInputStream audioInputStream;//mp3数据流
	static AudioFormat audioFormat;//格式
	static SourceDataLine sourceDataLine;//混频器
	static TargetDataLine targetDataLine;//目标数据行
	static boolean isStopPlay = true;//是否停止播放
	static boolean isStopCapture = false;//是否停止录音
	static String filePath = "";
	public static ArrayList<Song> songList = new ArrayList<Song>();
	//从文件中得到歌曲列表
	public void getSongs(){
		try
		{
			File file = new File(SONG_LIST);
			if(file.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null)
				{
					String[] tokens = line.split(SPLIT_STRING);
					int count = Integer.parseInt(tokens[2]);
					Song nextSong = new Song(tokens[0], tokens[1], count);
					songList.add(nextSong);
				}
			} else {
				System.out.println("文件列表不存在！");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public MP3Player(){
		new HandlerShutDownEvent();
		getSongs();
	}
	public MP3Player(String path){
		this();
		try
		{
			File file = new File(path);
			Song song = new Song(path, " ", 0);
			int index = songList.indexOf(song);
			if (index < 0)//使歌曲不重复
			{
				songList.add(song);
			}
			select(song);
			play();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//选择歌曲
	public void select(Song song){
		try
		{
			fileName = song.getTitle();
			filePath = song.getPath();
			File file = new File(filePath);
			//取得文件输入流
			audioInputStream = AudioSystem.getAudioInputStream(file);
			System.out.println("取得文件输入流");
			System.out.println("文件大小：" + song.getLength());
			song.addPlayCount();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("找不到指定文件：" + filePath);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	//检查音频文件格式
	private void checkAudioFormat(){
		//编码不符合默认要求
		if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED){
			//定义新的文件格式
			audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				audioFormat.getSampleRate(), 16,
				audioFormat.getChannels(),
				audioFormat.getChannels() * 2,
				audioFormat.getSampleRate(), false);
			//利用新格式和音频输入流构造新的音频输入流
			audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
		}
	}
	//取得AudioFormat
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000.0F;
		//8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		//8,16
		int channels = 1;
		//1,2
		boolean signed = true;
		//true,false
		boolean bigEndian = false;
		//true,false
		return new AudioFormat(sampleRate, 
			sampleSizeInBits, channels, 
			signed,
			bigEndian);
	}
	//播放
	public void play(){
		try 
		{
			System.out.println("开始播放");
			audioFormat = audioInputStream.getFormat();
			checkAudioFormat();
			//构造数据行
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat);
			//将数据行读入原数据行
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			isStopPlay = false;
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	//录音
	public void capture(){
		try
		{
			System.out.println("开始录音");
			audioFormat = getAudioFormat();
			//构造数据行
			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);
			//将数据行读入目标数据行
			targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
			isStopCapture = false;
			Thread captureThread = new Thread(new CaptureThread());
			captureThread.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//保存
	public void saveCaptureFile(String path){
		try
		{
			filePath = path;
			File file = new File(filePath + ".wav");		
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
			FileWriter writer = new FileWriter(SONG_LIST, true);//true表示写到文件末尾
			writer.write(filePath + SPLIT_STRING + " " + SPLIT_STRING +"0\n");
			writer.close();
			System.out.println(fileName + "已保存！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//停止播放
	public void stopPlay(){
		isStopPlay = true;
	}
	//停止录音
	public void stopCapture(){
		isStopCapture = true;
	}
	//删除歌曲
	public void delete(Song song){
		try
		{
			File file = new File(song.getPath());
			file.delete();
			System.out.println("文件已删除！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	public void sort(int orderBy){
		Comparator<Song> compare = null;
		switch (orderBy)
		{
		case SORT_BY_TITLE:
			compare = new TitleCompare();
			break;
		case SORT_BY_ARTIST:
			compare = new ArtistCompare();
			break;
		case SORT_BY_PLAY_COUNT:
			compare = new PlayCountCompare();
			break;
		default:
			break;
		}
		Collections.sort(songList, compare);
	}
}