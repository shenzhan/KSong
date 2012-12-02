package com.audio;

import java.net.*;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		audio a=new audio();
		
		try{
			
		     ServerSocket serSock=new ServerSocket(3000);
	          Socket cli=serSock.accept();
	          System.out.println("server accept");
	          
	          a.ReceiveStream(cli);
	          
			
		}catch(Exception e){
			System.out.println("server error");
		}
		  

	}

}
