package com.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author shenzhan
 * 定义远程服务接口 需要在调用端抛出RemoteException
 */
public interface IServer  extends Remote {
	
	/**
	 * 用户登录 修改在线状态 登记当前IP
	 * 
	 * @param strName
	 *            用户名
	 * 
	 * @param strPwd
	 *            密码
	 * 
	 * @param strIP
	 *            用户当前IP
	 * 
	 * @return boolean
	 */
	public boolean Login(String strName, String strPwd, String strIP)  throws RemoteException;
	
	/**
	 * 获取某个人的IP
	 * 
	 * @param nId
	 *            用户的ID
	 * 
	 * @return String
	 */
	public String GetIP(int nId) throws RemoteException;
	
	/**
	 * 返回歌曲列表
	 * 
	 * @return SongStru[] 歌曲数据
	 */
	public ArrayList<SongStru> GetSongList() throws RemoteException;
	
	
	 /**  返回好友列表
		 * @param nId  用户的ID
		 * @return FriendStru[] 好友数据
		 */
	   public ArrayList<FriendStru> 	GetFriendList(int nId) throws RemoteException;
	   
	   
	   /**
		 * 返回K群列表
		 * @param nId  用户的ID
		 * @return KGroupStru[] K群数据
		 */
		public ArrayList<KGroupStru> 	GetKGroupList(int nId) throws RemoteException;

}
