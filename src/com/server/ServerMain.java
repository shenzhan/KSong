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
 * ������������ ��Ҫ�ǰ󶨶���
 * @author shenzhan
 *
 */
public class ServerMain {

	/**������������
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		
		try{
			IServer server=new Server();
			Server.getConnection();  //�������ݿ�
			
			//���������ϵ�Զ�̶���ע���Registry��ʵ������ָ���˿�Ϊ8888����һ���ز����٣�JavaĬ�϶˿���1099�����ز���ȱ��һ����ȱ��ע����������޷��󶨶���Զ��ע����� 
            LocateRegistry.createRegistry(8888); 

            //��Զ�̶���ע�ᵽRMIע��������ϣ�������ΪRHello 
            //�󶨵�URL��׼��ʽΪ��rmi://host:port/name(����Э��������ʡ�ԣ���������д��������ȷ�ģ� 
            Naming.bind("rmi://localhost:8888/RServer",server); 
//            Naming.bind("//localhost:8888/RHello",rhello); 

            System.out.println(">>>>>INFO:Զ��IServer����󶨳ɹ���"); 
			
			
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
