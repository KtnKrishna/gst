package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection_reg() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://salary.csuwfpzwgdsj.us-east-1.rds.amazonaws.com:3306/salarylocal","root","Sandeep123");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg?allowMultiQueries=true&useUnicode=yes&characterEncoding=UTF-8","root","root");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("databases issue");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://salary.csuwfpzwgdsj.us-east-1.rds.amazonaws.com:3306/salarylocal","root","Sandeep123");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gstapi?allowMultiQueries=true","root","root");
			System.out.println("Connected");
			return con;
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("databases issue");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static Connection getConnection(String db,String host) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://salary.csuwfpzwgdsj.us-east-1.rds.amazonaws.com:3306/salarylocal","root","Sandeep123");
			Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+db+"?allowMultiQueries=true&useUnicode=yes&characterEncoding=UTF-8","root","root");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("databases issue");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Connection getAccessConnection(String DbPAth) {
		try {
			
			
			 //DbPAth = "c://temp1//aa.mdb";       
			
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://salary.csuwfpzwgdsj.us-east-1.rds.amazonaws.com:3306/salarylocal","root","Sandeep123");
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://"+DbPAth);
			return con;
			
			
		} catch (SQLException e) {
			System.out.println("databases issue");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
