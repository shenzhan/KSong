package com.server;

import java.io.Serializable;
import java.sql.Date;

/**
 * 歌曲的结构体
 * @author shenzhan
 *
 */
public class SongStru  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int sId;
	String sName;
	int uId;
	String sPath;
	int sCount;
	Date sTime;

}
