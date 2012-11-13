package com.audio;

import java.io.*; 

import javax.sound.sampled.*; 

import java.net.*;

/**
 *    sound 类实现声音 录制  播放     
 * 
 *录音 Record();
*播放Play();
*保存Save(string path);
*发送数据流SendStream(socket s);
*接受数据流ReceiveStream(socket s);
*暂停Stop();
 * 
 * 
 */
public class audio {
	//网络套接字 传输网络数据
	ServerSocket serSocket;
    Socket clientSocket;
 
	static boolean stopCapture = false; // 控制录音标志
	public static AudioFormat audioFormat; // 录音格式
	
	// 读取数据：从TargetDataLine写入ByteArrayOutputStream录音
	public static ByteArrayOutputStream byteArrayOutputStream;
	public static int totaldatasize = 0;
	static TargetDataLine targetDataLine;
	
	// 播放数据：从AudioInputStream写入SourceDataLine播放
	static AudioInputStream audioInputStream;
	static SourceDataLine sourceDataLine;
	
	
	// 取得AudioFormat 音频格式
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000.0F;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

/**
 * 发送数据流SendStream(socket s)
 * @param s 目标客户端的套接字
 */
	public void SendStream(Socket s){
		try{
		        // 打开录音
					audioFormat = getAudioFormat();
					DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
					targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
					targetDataLine.open(audioFormat);
					targetDataLine.start();
		}
		catch (Exception e) {
			    e.printStackTrace();
		    	System.exit(0);
		}
		
	}
	
    public void ReceiveStream(){
    	
    }
	
}
