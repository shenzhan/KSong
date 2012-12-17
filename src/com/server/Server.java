package com.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * ���������ṩ������
 */
public class Server extends UnicastRemoteObject implements IServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	// public void conn = null; // ����Connection����
	static Connection conn;

	/**
	 * ���ӵ����ݿ�
	 */
	public static void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // �������ݿ�����
			String url = "jdbc:mysql://localhost:3306/ksong";
			String user = "root";
			String passWord = "root";
			DriverManager.setLoginTimeout(20);
			conn = DriverManager.getConnection(url, user, passWord);
			// getConnection()���������ֱ�ָ���������ݿ��URL���û���������
			if (conn == null) {
				System.out.println("���ݿ�����ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}// �쳣����
			// return conn;// ����Connection����
	}

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
	public boolean Login(String strName, String strPwd, String strIP) {

		if (strName.length() == 0 || strPwd.length() == 0) {
			return false;
		}

		String sql = String.format(
				"select * from userinfo where uName='%s' and uPassword='%s' ",
				strName, strPwd);
		try {
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql);
			while (res.next()) {
				// ��¼�ɹ� �޸�����״̬
				sql = String
						.format("update userinfo set uStatus=1, uIP='%s'   where uName='%s' and uPassword='%s' ",
								strIP, strName, strPwd);
				statement.execute(sql);
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * ��ȡĳ���˵�IP
	 * 
	 * @param nId
	 *            �û���ID
	 * 
	 * @return String
	 */
	public String GetIP(int nId) {
		String sql = String
				.format("select uIP from userinfo where uId=%d", nId);
		try {
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql);
			while (res.next()) {
				return res.getString("uIP");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ظ����б�
	 * 
	 * @return SongStru[] ��������
	 */
	public ArrayList<SongStru> GetSongList() {
		String sql = String.format("select * from song "); // �������еĸ���
		ArrayList<SongStru> SongList = new ArrayList<SongStru>(); // ������������
		try {
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql); // ִ������

			while (res.next()) {
				// ��Ҫ���صļ������������
				SongStru song = new SongStru();
				song.sId = res.getInt("sId");
				song.sName = res.getString("sName");
				song.uId = res.getInt("uId");
				song.sPath = res.getString("sPath");
				song.sCount = res.getInt("sCount");
				song.sTime = res.getDate("sTime");
				SongList.add(song);
			}
			return SongList;
		} catch (Exception e) {
             e.printStackTrace();
             return null;
		}
	}

	@Override
	/**  ���غ����б�
	 * @param nId  �û���ID
	 * @return FriendStru[] ��������
	 */
	public ArrayList<FriendStru> GetFriendList(int nId) {
		try {
			String sql = String.format("select * from friend where uIdMe=%d", nId); // �������еĺ���
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql); // ִ������

			ArrayList<FriendStru> FriendList = new ArrayList<FriendStru>(); // ������������
			while (res.next()) {
				// ��Ҫ���صļ������������
				FriendStru friend = new FriendStru();
				friend.fId = res.getInt("fId");
				friend.uIdMe = res.getInt("uIdMe");
				friend.uIdFriend = res.getInt("uIdFriend");
				FriendList.add(friend);
			}
			return FriendList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//*******Logic ERROR  Need to change********************************************************************************

	@Override
	/**
	 * ����KȺ�б�
	 * @param nId  �û���ID
	 * @return KGroupStru[] KȺ����
	 */
	public ArrayList<KGroupStru> GetKGroupList(int nId) {
		try {
			String sql = String
					.format("select * from kgroup where nId=%d", nId); // �������е�KȺ
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql); // ִ������
			ArrayList<KGroupStru> KGroupList = new ArrayList<KGroupStru>(); // ������������
			while (res.next()) {
				// ��Ҫ���صļ������������
				KGroupStru kgroup = new KGroupStru();
				kgroup.kId = res.getInt("kId");
				kgroup.kName = res.getString("kName");
				kgroup.kTime = res.getDate("kTime");
				KGroupList.add(kgroup);
			}
			return KGroupList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
