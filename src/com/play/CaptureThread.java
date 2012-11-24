package com.play;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
class CaptureThread extends Thread
{
	byte[] tempBuffer = new byte[320];
	public void run(){
		try
		{
			System.out.println("正在录音...");
			int cnt;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			while(!MP3Player.isStopCapture){
				cnt = MP3Player.targetDataLine.read(tempBuffer, 0, tempBuffer.length);
				if(cnt > 0){
					//保存数据
					byteArrayOutputStream.write(tempBuffer, 0, cnt);
				}
			}
			byteArrayOutputStream.close();
			//保存为audioInputStream
			byte audioData[] = byteArrayOutputStream.toByteArray();
			//转为输入流
			InputStream inputStream = new ByteArrayInputStream(audioData);
			MP3Player.audioInputStream = new AudioInputStream(inputStream,
					MP3Player.audioFormat, audioData.length / MP3Player.audioFormat.getFrameSize());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}