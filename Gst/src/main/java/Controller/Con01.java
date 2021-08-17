package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.ConnectionUtil;

public class Con01 {
	
	
	public  static String Get_Data(Connection con1, String StrSql) throws SQLException  {
		
	Connection	 con = null;
	PreparedStatement ps=null;
	ResultSet rs=null;
    	try {
    	con=ConnectionUtil.getConnection("admin_db","localhost");
    	ps=con.prepareStatement(StrSql);
    	System.out.println(StrSql);
    	rs=ps.executeQuery();
    	
    	if (rs.next()) 
			{
				String result = rs.getString(1);

				
				return result;
			}
			else
			{
				return "error";
			}	
    	}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return "error";
		}
    	finally {
			if(con!=null) {
				con.close();
			}
			
			if(rs!=null) {
				rs.close();
			}
			
			if(ps!=null) {
				ps.close();
			}
			
		}
	

    	   
    	
}
	
	
	
	
	
}