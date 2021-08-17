package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.ConnectionUtil;

public class Mdb {

	
	public static void main(String[] args) {
		try {
			
			Connection con = ConnectionUtil.getAccessConnection("c://temp1//aa.mdb");
                   
         String    sql = "SELECT * FROM tmledgr";
             
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            while (result.next()) {
                int id = result.getInt("ledcd");
                String fullname = result.getString("lednm");
                String email = result.getString("a1");
                String phone = result.getString("a2");
                 
                System.out.println(id + ", " + fullname + ", " + email + ", " + phone);
            }
             
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	
}
