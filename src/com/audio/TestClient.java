package com.audio;

import java.net.ServerSocket;
import java.net.Socket;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


       audio a=new audio();
		
		try{
			          
	          Socket c=new Socket("127.0.0.1",3000);
	          a.SendStream(c);
	          System.out.println("connect success");
	          
			
		}catch(Exception e){
			System.out.println("client error");
		}

	}

}
