package com.server;

import java.util.ArrayList;

import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

/**
 * 服务器主函数 主要是绑定对象
 * @author shenzhan
 *
 */
public class ServerMain {

	/**服务器主函数
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		
		try{
			IServer server=new Server();
			Server.getConnection();  //连接数据库
			
			//本地主机上的远程对象注册表Registry的实例，并指定端口为8888，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上 
            LocateRegistry.createRegistry(8888); 

            //把远程对象注册到RMI注册服务器上，并命名为RHello 
            //绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的） 
            Naming.bind("rmi://localhost:8888/RServer",server); 
//            Naming.bind("//localhost:8888/RHello",rhello); 

            System.out.println(">>>>>INFO:远程IServer对象绑定成功！"); 
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	  /*
		  Server ser=new Server();
		
		//System.out.println("ok");

			 if(ser.Login("zhan", "zhan", "10.22.28.157")){
				 System.out.println("ok");
			}
			 else{
				 System.out.println("fail");
			 }
		
		
			System.out.println(ser.GetIP(2));
			
			ArrayList<SongStru> SongList;
			SongList=ser.GetSongList();
			for(int i=0;i<SongList.size();++i){
				System.out.println(SongList.get(i).sName);
			}
         ArrayList<FriendStru> FriendList;
         FriendList=ser.GetFriendList(1);
         for(int i=0;i<FriendList.size();++i){
				System.out.println(FriendList.get(i).uIdFriend);
			}
      */   
		
	}

}
