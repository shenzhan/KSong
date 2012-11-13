package com.audio;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;


  class RecordThread extends Thread  {
	
	// 临时数组
		byte tempBuffer[] = new byte[10000];
		Socket client;
		BufferedOutputStream captrueOutputStream;
		
	public 	RecordThread(Socket s){
			this.client=s;
		}
		
		public void run() {
			audio.byteArrayOutputStream = new ByteArrayOutputStream();
			audio.totaldatasize = 0;
			audio.stopCapture = false;
			
			 try { 
		           captrueOutputStream=new BufferedOutputStream(client.getOutputStream());//建立输出流 此处可以加套压缩流用来压缩数据 
		         } 
		         catch (IOException ex) { 
		             return; 
		         }
			
			try {// 循环执行，直到按下停止录音按钮
				while (!audio.stopCapture) {
					// 读取10000个数据
					int cnt = audio.targetDataLine.read(tempBuffer, 0,
							tempBuffer.length);
					//把数据写入网络流中
					try { 
			               captrueOutputStream.write(tempBuffer, 0, cnt);//写入网络流 
			             } 
			             catch (Exception ex) { 
			                 break; 
			             } 
					
					//把数据写入缓冲区中
					if (cnt > 0) {
						// 保存该数据
						audio.byteArrayOutputStream.write(tempBuffer, 0, cnt);
						audio.totaldatasize += cnt;
					}
				}
				audio.byteArrayOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

}
