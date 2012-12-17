package com.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author shenzhan
 * ����Զ�̷���ӿ� ��Ҫ�ڵ��ö��׳�RemoteException
 */
public interface IServer  extends Remote {
	
	/**
	 * �û���¼ �޸�����״̬ �Ǽǵ�ǰIP
	 * 
	 * @param strName
	 *            �û���
	 * 
	 * @param strPwd
	 *            ����
	 * 
	 * @param strIP
	 *            �û���ǰIP
	 * 
	 * @return boolean
	 */
	public boolean Login(String strName, String strPwd, String strIP)  throws RemoteException;
	
	/**
	 * ��ȡĳ���˵�IP
	 * 
	 * @param nId
	 *            �û���ID
	 * 
	 * @return String
	 */
	public String GetIP(int nId) throws RemoteException;
	
	/**
	 * ���ظ����б�
	 * 
	 * @return SongStru[] ��������
	 */
	public ArrayList<SongStru> GetSongList() throws RemoteException;
	
	
	 /**  ���غ����б�
		 * @param nId  �û���ID
		 * @return FriendStru[] ��������
		 */
	   public ArrayList<FriendStru> 	GetFriendList(int nId) throws RemoteException;
	   
	   
	   /**
		 * ����KȺ�б�
		 * @param nId  �û���ID
		 * @return KGroupStru[] KȺ����
		 */
		public ArrayList<KGroupStru> 	GetKGroupList(int nId) throws RemoteException;

}
