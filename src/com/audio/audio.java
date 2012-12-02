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
	static boolean stopPlay=false;         //播放控制标志
	
	public static AudioFormat audioFormat; // 录音格式
	
	// 读取数据：从TargetDataLine写入ByteArrayOutputStream录音
	public static ByteArrayOutputStream byteArrayOutputStream;
	public static int totaldatasize = 0;
	static TargetDataLine targetDataLine;
	
	// 播放数据：从AudioInputStream写入SourceDataLine播放
	static AudioInputStream audioInputStream;
	static SourceDataLine sourceDataLine;
	
	Thread Record;
	PlayThread Play;
	
	
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
			
					
					 Record=new Thread(new RecordThread(s));
					//RecordThread Record=new RecordThread(s;)
					Record.start();
		}
		catch (Exception e) {
			    e.printStackTrace();
		    	System.exit(0);
		}
		
	}
	
/**
 * 从网络流中获取数据并播放
 * @param s 与对方机器的套接字
 */
    public void ReceiveStream(Socket s){
    	
    	try{
    		audioFormat = getAudioFormat();
    		
    		//获得播放器资源
    		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			
		   Play=new PlayThread(s);
		   Play.start();
			
			
    		
    	}catch(Exception e)
    	{
    		  e.printStackTrace();
		    	System.exit(0);
    	}
    	
    }

   public void StopRecord()
   {
	   stopCapture=true;
   }
   public void StopPlay(){
	   stopPlay=true;
   }
    
/**
 * 暂停录音
 */
   public void PauseRecord(){
	   
	   try{
		     if(Record.isAlive()){
		    	 Record.wait();
		     }
		    	 
	   }catch(Exception e){
		   System.out.println("error in PauseRecord"); 
	   }
   }
    
    
 /**
  * 暂停播放  
  */
   public void PausePlay(){
	   try{
		     if(Play.isAlive()){
		    	 Play.wait();
		     }
		    	 
	   }catch(Exception e){
		   System.out.println("error in PausePlay"); 
	   }  
   }
   
   /**
    * 再次播放
    */
   public void AgainPlay(){
	   try{
		    
		    Play.notify();	 
	   }catch(Exception e){
		   System.out.println("error in AgainPlay"); 
	   }  
   }
   
   /**
    * 再次录音
    */
   public void AgainRecord()
   {
	   try{
		    
		    Record.notify();	 
	   }catch(Exception e){
		   System.out.println("error in AgainRecord"); 
	   }  
	   
   }
   
  /**
   *  保存文件
   * @param path 文件路径
   */
   public void Save(String path){
	// 取得录音输入流
			AudioFormat audioFormat = getAudioFormat();
			byte audioData[] = byteArrayOutputStream.toByteArray();
			InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
			audioInputStream = new AudioInputStream(byteArrayInputStream,audioFormat, audioData.length / audioFormat.getFrameSize());
	   
			try {
				File file = new File(path);
				AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
   }
   
 /**
  *   
  */
   public void PlayBuff(){
	 
   }
   
}
