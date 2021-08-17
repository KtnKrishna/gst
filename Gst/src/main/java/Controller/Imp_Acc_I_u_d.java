package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.PreparedStatement;

import Util.ConnectionUtil;

public class Imp_Acc_I_u_d {

	
	public static JSONObject  Import_Group(String api,String db,String Qtype,String flist,String vlist,String Qry,String cdlist) 
			throws JSONException, SQLException
	{  JSONObject obj=new JSONObject();
	
	    Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    String Err01="";
		String ErrMsg = "";
		int ExcRecord = 0;
		
		String Qry_ ="";
		
				try {

		 con=ConnectionUtil.getConnection("test","localhost"); 
		if(con!=null) {	Err01 = "ok"; }
		else {	Err01="MysqlError"; }
		con.close();
    	 con=ConnectionUtil.getConnection(db,"localhost");

			 if(con==null) 
			   { 	obj.put("Err01",Err01);
					obj.put("status", "error");
					obj.put("code", 200);
					obj.put("message", "MysqlError");
					obj.put("Data", "");   
					Qtype = "Exit";
			   }
			
		if (Qtype.equals("d")) {
		     st = con.createStatement();
			 Qry_ = " Delete from Mst_group where Grp_cd int ("+vlist+")";
	         ExcRecord = st.executeUpdate(Qry_);
	         obj.put("message", "Record Deleted");
	    	} 
		if (Qtype.equals("i")) {
	    		 st = con.createStatement();
				 Qry_ = " insert into Mst_group ("+ flist +") values "+vlist;
		         ExcRecord = st.executeUpdate(Qry_);
		         obj.put("message", "Record Inserted");
	    	}  
	    if (Qtype.equals("u")) { 
	    		 st = con.createStatement();
				 ExcRecord = st.executeUpdate(Qry);
		         obj.put("message", "Record Updated");
	    	}  
	    if (Qtype.equals("iu")) { 
	    	 System.out.println("start 01");
	    	    ExcRecord = 0;
	    		String[] ar_flist = flist.split(",");
	    		String[] ar_vlist = vlist.split("`");
	    		 String[] a_vlist = {"1","2","3"};
	    		for (int q=0;q<=ar_vlist.length-1;q++ ) 
	    		{  a_vlist = ar_vlist[q].split(",");
	    			
	    		 System.out.println("="+a_vlist[0]);
	    		
	    		
	    		
	    		st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    		 rs=st.executeQuery("SELECT * FROM Mst_group where grp_cd ="+a_vlist[0] );
	    		
	    		if (!rs.isBeforeFirst()) {
	    			//System.out.println("eof");
	    		    rs.moveToInsertRow();
	    			for (int jj=0;jj<=ar_flist.length-1;jj++ ) {
	    				//System.out.println(ar_flist[j]+" -a- "+ a_vlist[j]);
	    				rs.updateString(ar_flist[jj], a_vlist[jj]);
	    			}
	    			rs.updateString("U_Cd", "0");
	    		
	    			ExcRecord=ExcRecord+1;
	    			rs.insertRow();
	    		} else {

	    		//update with where
	    			rs.next();
	    			System.out.println(rs.getString(1));
		    		System.out.println(rs.getString(2));
	    			
	    		rs.moveToCurrentRow();
	    		   
	    		System.out.println(ar_flist[1]+" -a- "+ a_vlist[1]);
	    		System.out.println(ar_flist[2]+" -a- "+ a_vlist[2]);
	    		System.out.println(ar_flist[3]+" -a- "+ a_vlist[3]);
	    		System.out.println(ar_flist[4]+" -a- "+ a_vlist[4]);
	    		
	    		
	    		
   			  
    				  rs.updateString("grp_nm", a_vlist[1]);
	    			  rs.updateString("grp_typ", a_vlist[2]);
	    			  rs.updateString("Grp_order", a_vlist[3]);
	    			  rs.updateString("c_cd", a_vlist[4]);
	    		   ExcRecord=ExcRecord+1;
	    		   rs.updateRow();
	    		} 
	    		
	    		}//for
	    		rs.close();
	       	}//"iu"
	    	
	     
	    
		
		obj.put("Err01",Err01);
		obj.put("status", "ok");
		obj.put("code", 200);
		obj.put("Data",ExcRecord); 
	
		st = con.createStatement();
		
		 Qry_ = " CALL  ktn_chksum('"+db+"','mst_Group','"+cdlist+"') ";
		 System.out.println("sp strat " +Qry_);
		 rs=st.executeQuery( Qry_);
		 rs.next();
		 System.out.println("sp end");
		 obj.put("cd-0" , rs.getString(1));
		 obj.put("csum-0", rs.getString(2));
		 obj.put("module", "group");
		 
		st.close();
		rs.close(); 
		}// try
	catch (Exception e) {
		e.printStackTrace();
		Err01="Error";
		ErrMsg =(e.getLocalizedMessage());
		
	}
		con.close();
		return obj;
}
	
	public static JSONObject  Import_Leger(String api,String db,String Qtype,String flist,String vlist,String Qry,String cdlist,String yid,String cid ) 
			throws JSONException, SQLException
	{  JSONObject obj=new JSONObject();
	
	    Connection con = null;
	    Statement st = null;Statement st1 = null;
	    ResultSet rs = null;
	    String Sql="";
	    String Err01="";
		String ErrMsg = "";
		String errData= "";
		int ExcRecord = 0;
		
		String Qry_ ="";
		
		try {

		 con=ConnectionUtil.getConnection("test","localhost"); 
		if(con!=null) {	Err01 = "ok"; }
		else {	Err01="MysqlError"; }
		con.close();
    	 con=ConnectionUtil.getConnection(db,"localhost");

			 if(con==null) 
			   { 	obj.put("Err01",Err01);
					obj.put("status", "error");
					obj.put("code", 200);
					obj.put("message", "MysqlError");
					obj.put("Data", "");   
					Qtype = "Exit";
			   }
			
		if (Qtype.equals("d")) {
		     st = con.createStatement();
			 Qry_ = " Delete from Mst_Leger where led_cd int ("+vlist+")";
	         ExcRecord = st.executeUpdate(Qry_);
	         obj.put("message", "Record Deleted");
	    	} 
		if (Qtype.equals("i")) {
	    		 st = con.createStatement();
				 Qry_ = " insert into Mst_Leger ("+ flist +") values "+vlist;
		         ExcRecord = st.executeUpdate(Qry_);
		         obj.put("message", "Record Inserted");
	    	}  
	    if (Qtype.equals("u")) { 
	    		 st = con.createStatement();
				 ExcRecord = st.executeUpdate(Qry);
		         obj.put("message", "Record Updated");
	    	}  
	    if (Qtype.equals("iu")) { 
	    	 System.out.println("start 01");
	    	    ExcRecord = 0;
	    		String[] ar_flist = flist.split(",");
	    		String[] ar_vlist = vlist.split("`");
	    		 String[] a_vlist = {"1","2","3"};
	    		 
	    		for (int q=0;q<=ar_vlist.length-1;q++ ) 
	    		{  st1 = con.createStatement();
	    			
	    			a_vlist = ar_vlist[q].split("~");
	    			
	    	    st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    		 rs=st.executeQuery("SELECT * FROM Mst_Ledger where led_cd ="+a_vlist[0] );
	    		
	    		 
	    		    System.out.println(ar_flist[1]+" -a- "+ a_vlist[1]);
		    		System.out.println(ar_flist[2]+" -a- "+ a_vlist[2]);
		    		System.out.println(ar_flist[3]+" -a- "+ a_vlist[3]);
		    		System.out.println(ar_flist[4]+" -a- "+ a_vlist[4]);
	    		 
	    		 
	    		if (!rs.isBeforeFirst()) {
	    			
	    		    rs.moveToInsertRow();
	    			for (int jj=0;jj<=ar_flist.length-1;jj++ ) {
	    				
	    				System.out.println(ar_flist[jj]+" -- "+ a_vlist[jj]);
	    				
	    				rs.updateString(ar_flist[jj], a_vlist[jj]);
	    			}
	    			errData = a_vlist[2]+"-"+a_vlist[3]+a_vlist[4]+"-"+a_vlist[5];
	    			
	    			rs.updateString("U_Cd", "0");
	    		
	    			ExcRecord=ExcRecord+1;
	    			rs.insertRow();
	    			
	    			
					Sql = " insert into Mst_Ledger_l (year_cd,led_Cd,led_opbal,c_cd) values ("+yid+","+a_vlist[0]+","+a_vlist[1]+","+cid+")";
					
					int ExcRecord1 = st.executeUpdate(Sql);
							System.out.println("end "  );
							
	    		} else {

	    		//update with where
	    			rs.next();	    				    			
	    		   rs.moveToCurrentRow();
	    		   
	    		   for (int jj=1;jj<=ar_flist.length-1;jj++ ) {
	    		    	rs.updateString(ar_flist[jj], a_vlist[jj]);
	    			}
	    		   errData = ar_flist[0]+"-"+a_vlist[2];
	    		   ExcRecord=ExcRecord+1;
	    		   rs.updateRow();
	    		   
	    		    Sql = " update Mst_Ledger_l set c_Cd ="+cid+", year_cd ="+yid+",led_opbal="+a_vlist[1] + " Where led_Cd = " +a_vlist[0];
	    		    int ExcRecord2 = st.executeUpdate(Sql);
				   
	    		} 
	    		
	    		}//for
	    		rs.close();
	    		st1.close();
	       	}//"iu"
			
		obj.put("Err01",Err01);
		obj.put("status", "ok");
		obj.put("code", 200);
		obj.put("Data",ExcRecord); 
	
		st = con.createStatement();
		
		 Qry_ = " CALL  ktn_chksum('"+db+"','mst_Ledger','"+cdlist+"') ";
		 System.out.println("sp strat " +Qry_);
		 rs=st.executeQuery( Qry_);
		 rs.next();
		 System.out.println("sp end");
		 obj.put("cd-0" , rs.getString(1));
		 obj.put("csum-0", rs.getString(2));
		 obj.put("module", "ledger");
		 
		st.close();
		rs.close(); 
		}// try
	catch (Exception e) {
		e.printStackTrace();
		Err01="Error";
		ErrMsg =(e.getLocalizedMessage());
		System.out.println(ErrMsg);
		obj.put("Err01",Err01);
		obj.put("status", "error");
		obj.put("code", 200);
		obj.put("Data",errData); 
		obj.put("Massge",ErrMsg); 
			
		return obj;
	}
		con.close();
		
		
		
		
		return obj;
}

	public static JSONObject  Import_item(String api,String db,String Qtype,String flist,String vlist,String Qry,String cdlist,String yid,String cid ) 
			throws JSONException, SQLException
	{  JSONObject obj=new JSONObject();
	
	    Connection con = null;
	    Statement st = null;Statement st1 = null;
	    ResultSet rs = null;
	    String Sql="";
	    String Err01="";
		String ErrMsg = "";
		String errData= "";
		int ExcRecord = 0;
		
		String Qry_ ="";
		
		try {

		 con=ConnectionUtil.getConnection("test","localhost"); 
		if(con!=null) {	Err01 = "ok"; }
		else {	Err01="MysqlError"; }
		con.close();
    	 con=ConnectionUtil.getConnection(db,"localhost");

			 if(con==null) 
			   { 	obj.put("Err01",Err01);
					obj.put("status", "error");
					obj.put("code", 200);
					obj.put("message", "MysqlError");
					obj.put("Data", "");   
					Qtype = "Exit";
			   }
			
		if (Qtype.equals("d")) {
		     st = con.createStatement();
			 Qry_ = " Delete from Mst_Leger where led_cd int ("+vlist+")";
	         ExcRecord = st.executeUpdate(Qry_);
	         obj.put("message", "Record Deleted");
	    	} 
		if (Qtype.equals("i")) {
	    		 st = con.createStatement();
				 Qry_ = " insert into Mst_Leger ("+ flist +") values "+vlist;
		         ExcRecord = st.executeUpdate(Qry_);
		         obj.put("message", "Record Inserted");
	    	}  
		if (Qtype.equals("ins-grp")) {
   		     
			System.out.println("start 01");
			
			st = con.createStatement();
			 ExcRecord = st.executeUpdate(Qry);
			 System.out.println("start 02");
	         obj.put("message", "Record Inserted");
	         
	         st = con.createStatement();
	 		
			 Qry_ = " SELECT GROUP_CONCAT(grp_Cd SEPARATOR'^'),GROUP_CONCAT(Grp_nm SEPARATOR'^') FROM m_itm_grp WHERE c_Cd = "+cid+";";
			 System.out.println("11 " +Qry_);
			 rs=st.executeQuery( Qry_);
			 rs.next();
			 System.out.println("22");
			 obj.put("cd" , rs.getString(1));
			 obj.put("grp", rs.getString(2));
			 obj.put("module", "item-grp");
	         
	         
	         
     	}  
	    if (Qtype.equals("u")) { 
	    		 st = con.createStatement();
				 ExcRecord = st.executeUpdate(Qry);
		         obj.put("message", "Record Updated");
	    	}  
	    if (Qtype.equals("iu")) { 
	    	 
	    	    ExcRecord = 0;
	    		String[] ar_flist = flist.split(",");
	    		String[] ar_vlist = vlist.split("`");
	    		 String[] a_vlist = {"1","2","3"};
	    		 
	    		for (int q=0;q<=ar_vlist.length-1;q++ ) 
	    		{  st1 = con.createStatement();
	    			
	    			a_vlist = ar_vlist[q].split("~");
	    			
	    	    st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    		 rs=st.executeQuery("SELECT * FROM Mst_Ledger where led_cd ="+a_vlist[0] );
	    		
	    		 
	    		    System.out.println(ar_flist[1]+" -a- "+ a_vlist[1]);
		    		System.out.println(ar_flist[2]+" -a- "+ a_vlist[2]);
		    		System.out.println(ar_flist[3]+" -a- "+ a_vlist[3]);
		    		System.out.println(ar_flist[4]+" -a- "+ a_vlist[4]);
	    		 
	    		 
	    		if (!rs.isBeforeFirst()) {
	    			
	    		    rs.moveToInsertRow();
	    			for (int jj=0;jj<=ar_flist.length-1;jj++ ) {
	    				
	    				System.out.println(ar_flist[jj]+" -- "+ a_vlist[jj]);
	    				
	    				rs.updateString(ar_flist[jj], a_vlist[jj]);
	    			}
	    			errData = a_vlist[2]+"-"+a_vlist[3]+a_vlist[4]+"-"+a_vlist[5];
	    			
	    			rs.updateString("U_Cd", "0");
	    		
	    			ExcRecord=ExcRecord+1;
	    			rs.insertRow();
	    			
	    			
					Sql = " insert into Mst_Ledger_l (year_cd,led_Cd,led_opbal,c_cd) values ("+yid+","+a_vlist[0]+","+a_vlist[1]+","+cid+")";
					
					int ExcRecord1 = st.executeUpdate(Sql);
							System.out.println("end "  );
							
	    		} else {

	    		//update with where
	    			rs.next();	    				    			
	    		   rs.moveToCurrentRow();
	    		   
	    		   for (int jj=1;jj<=ar_flist.length-1;jj++ ) {
	    		    	rs.updateString(ar_flist[jj], a_vlist[jj]);
	    			}
	    		   errData = ar_flist[0]+"-"+a_vlist[2];
	    		   ExcRecord=ExcRecord+1;
	    		   rs.updateRow();
	    		   
	    		    Sql = " update Mst_Ledger_l set c_Cd ="+cid+", year_cd ="+yid+",led_opbal="+a_vlist[1] + " Where led_Cd = " +a_vlist[0];
	    		    int ExcRecord2 = st.executeUpdate(Sql);
				   
	    		} 
	    		
	    		}//for
	    		rs.close();
	    		st1.close();
	       	}//"iu"
			
		obj.put("Err01",Err01);
		obj.put("status", "ok");
		obj.put("code", 200);
		obj.put("Data",ExcRecord); 
	
	/*	st = con.createStatement();
		
		 Qry_ = " CALL  ktn_chksum('"+db+"','mst_Ledger','"+cdlist+"') ";
		 System.out.println("sp strat " +Qry_);
		 rs=st.executeQuery( Qry_);
		 rs.next();
		 System.out.println("sp end");
		 obj.put("cd-0" , rs.getString(1));
		 obj.put("csum-0", rs.getString(2));
		 obj.put("module", "ledger");*/
		 
		st.close();
		rs.close(); 
		}// try
	catch (Exception e) {
		e.printStackTrace();
		Err01="Error";
		ErrMsg =(e.getLocalizedMessage());
		System.out.println(ErrMsg);
		obj.put("Err01",Err01);
		obj.put("status", "error");
		obj.put("code", 200);
		obj.put("Data",errData); 
		obj.put("Massge",ErrMsg); 
			
		return obj;
	}
		con.close();
		
		
		
		
		return obj;
}
	
	@SuppressWarnings("finally")
	private  static String Get_Data(Connection con, String StrSql) throws SQLException  {
		
	    String result="";
		PreparedStatement ps=null;
		ResultSet rs=null;
	    	try {
	    
	    	ps=(PreparedStatement) con.prepareStatement(StrSql);
	    	System.out.println(StrSql);
	    	rs=ps.executeQuery();
	    	
	    	if (rs.next()) 
				{	 result = rs.getString(1);	}
				else
				{	result = "error";		}	
	    	}//try
			catch (SQLException e) {
				System.out.println(e.getMessage());
				result = "error";
			}
	    	finally {
				return result;
			}
 }	//Get_Data



}


