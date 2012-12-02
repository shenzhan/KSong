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
 * 服务器端提供服务类
 */
public class Server  extends UnicastRemoteObject implements IServer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	// public void conn = null; // 创建Connection对象
	static Connection conn;

	/**
	 * 连接到数据库
	 */
	public static void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动
			String url = "jdbc:mysql://localhost:3306/ksong";
			String user = "root";
			String passWord = "root";
			DriverManager.setLoginTimeout(20);
			conn = DriverManager.getConnection(url, user, passWord);
			// getConnection()方法参数分别指定连接数据库的URL、用户名和密码
			if (conn == null) {
				System.out.println("数据库连接失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}// 异常处理
			// return conn;// 返回Connection对象
	}

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
				// 登录成功 修改在线状态
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
	 * 获取某个人的IP
	 * 
	 * @param nId
	 *            用户的ID
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
	 * 返回歌曲列表
	 * 
	 * @return SongStru[] 歌曲数据
	 */
	public ArrayList<SongStru> GetSongList() {
		String sql = String.format("select * from song "); // 查找所有的歌曲
		ArrayList<SongStru> SongList = new ArrayList<SongStru>(); // 创建返回数组
		try {
			Statement statement = (Statement) conn.createStatement();
			ResultSet res = (ResultSet) statement.executeQuery(sql); // 执行数据

			while (res.next()) {
				// 在要返回的集合中填充数据
				SongStru song = new SongStru();
				song.sId = res.getInt("sId");
				song.sName = res.getString("sName");
				song.uId = res.getInt("uId");
				song.sPath = res.getString("sPath");
				song.sCount = res.getInt("sCount");
				song.sTime = res.getDate("sTime");
				SongList.add(song);
			}
		} catch (Exception e) {

		}
		return SongList;

	}

}
