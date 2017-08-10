package Util;

import java.sql.*; 

public class data {
	private static String driver = "com.mysql.jdbc.Driver";
	// URLָ��Ҫ���ʵ����ݿ���scutcs
	private static String url = "jdbc:mysql://192.168.3.61/person?useUnicode=true&characterEncoding=utf-8";
	// MySQL����ʱ���û���
	private static String user = "root";
	// Java����MySQL����ʱ������
	private static String password = "root";
	private static Connection conn;
	static{
		try {
		// ������������
		Class.forName(driver);
		// �������ݿ�
		conn = DriverManager.getConnection(url, user, password);
		if(!conn.isClosed())
		System.out.println("Succeeded connecting to the Database!");
		// statement����ִ��SQL���
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return conn;
	}
	
//	public static void main(String[] args) {
//		
//		Connection conn = data.getConnection();
//
//	}
}
