package com.server;

import java.io.Serializable;
import java.sql.Date;

/**
 * �����Ľṹ��
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
