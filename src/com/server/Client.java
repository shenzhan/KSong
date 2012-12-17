package com.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.util.ArrayList;

public class Client {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args)   {
		 try{
			 IServer server=(IServer)Naming.lookup("rmi://localhost:8888/RServer");
			 
			 if(server.Login("zhan", "zhan", "10.22.28.157")){
				 System.out.println("ok");
			}
			 else{
				 System.out.println("fail");
			 }
			 
			 System.out.println(server.GetIP(2));
			 ArrayList<FriendStru> FriendList;
			 FriendList=server.GetFriendList(1);
			 for(int i=0;i<FriendList.size();++i)
				 System.out.println(FriendList.get(i).uIdFriend);
			 
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		
		

	}

}
