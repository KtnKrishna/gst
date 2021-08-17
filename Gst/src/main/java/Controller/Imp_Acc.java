package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;
import Util.ConnectionUtil;
public class Imp_Acc {
	
	public static JSONObject  DataImport(String api,String db,String tbl,String fl,String fv,String Qry,String Qtype,String flist,String vlist) 
			throws JSONException{
		JSONObject obj=new JSONObject();
		
		int ExcRecord = 0 ;
	
		Statement st = null;
		String Err01 = "";
		String ErrMsg="";
		String CrcString = "";
		String found_cd ="";
		String NotFind_cd = "";
		String found_cecksum = "";
		
		int k =0;
		
		
		try {//try001
			Connection con=ConnectionUtil.getConnection("test","localhost"); 
		
			if(con!=null) {	Err01 = "ok"; }
			else {	Err01="MysqlError"; }
			con.close();
			
			 con=ConnectionUtil.getConnection(db,"localhost");
			      			
				 if(con!=null) 
				   {
					if (Qtype.equals("SQLExcute")) 
					  {
						
						System.out.println(Qry);
					  st = con.createStatement();
			          ExcRecord = st.executeUpdate(Qry);
				      Err01="Excute";
				      
				      
				      
				      if  (!tbl.equals(""))  {
				    	  
				    	  
				    	  String StrSql = "select group_concat("+fl+"),group_concat(chksum), '"+tbl+"' from " +tbl + " where " +fl + " in (" + fv +")";
				    	  PreparedStatement	ps=con.prepareStatement(StrSql);
				         	System.out.println(StrSql);
				         	ResultSet	rs=ps.executeQuery();
				         	if (!rs.isBeforeFirst()) {
				         		
				         		obj.put("Err01","Get Data Error");
				    		    obj.put("status", "error");
				    			obj.put("code", 200);
				    			obj.put("message", "Record Not Found");
				    			obj.put("Data", "Reocrd Not Found");  	         		
				         	}
				         	else {
				         		if (rs.next()) {
				         			do {
				         				obj.put("Err01",Err01);
				         				obj.put("cd-"+k , rs.getString(1));
				         				obj.put("csum-"+k, rs.getString(2));
				         				obj.put("tbl-"+k, rs.getString(3)); 
				         				
				         				
				         			} while(rs.next());
				         			
				         		}
				         	}
				    	  
				      }
				      
				      }//SQLExcute
					if (Qtype.equals("Get_Cd_Status")) //////////////////////////////////////////////////////////////////////////////
					
					{  System.out.println("Get_Cd_Status"+" Qry= "+Qry);
						k=0;
				    	 
				    	  PreparedStatement	ps=con.prepareStatement(Qry);
				         	
				         	ResultSet	rs=ps.executeQuery();
				         	
				         	System.out.println("xxxx");
				         	
				         	if (!rs.isBeforeFirst()) {
				         		
				         		obj.put("Err01",Qtype);
				    		    obj.put("status", "Record Not Found");
				    			obj.put("code", 200);
				    			obj.put("message", "Record Not Found");
				    			obj.put("Data", Qry);  	         		
				         	}
				         	else {
				         		if (rs.next()) {
				         			do {
				         				//System.out.println(rs.getString(1));
				         				//found_cd=found_cd +rs.getString(1)+"," ;
				         				//found_cecksum=found_cecksum +rs.getString(2)+"," ;
				         				
				         				obj.put("cd-"+k , rs.getString(1));
				         				obj.put("csum-"+k, rs.getString(2));
				         				obj.put("tbl-"+k, rs.getString(3));
				         				
				         				k=k+1;
				         			   } while(rs.next());
				         			
				         		}
				         		
				         		System.out.println(found_cd);
				         		Err01 = "Get_Cd_Status";
				         	}
						
						
					}//Get_Cd_Status //////////////////////////////////////////////////////////////////////////////
					
				
					if (Qtype.equals("InsUpd")) 
					  {
						System.out.println("InsUpd");
						
						
						String[] a_flist = flist.split(",");
						
						String[] a_vlist = vlist.split(String.valueOf((char)2));
						
						
						
						for (int i = 0;i<=a_vlist.length-1;i++)
						{// For1
							
								String[] aaa_vlist = a_vlist[i].split("`");
							
							String Sql = " select "+ fl+"  from "+tbl+ " Where " + fl +" = "+aaa_vlist[0].toString()  ;
								
							System.out.println("qry :"+Sql);
								String Result= Get_Data(con, Sql);
								
								System.out.println("Result :"+Result);
									if  (Result.equals("error")) 
									{// insert Record 
										System.out.println("Insert Start");		
										String sql = " insert into " +tbl + " (" + flist+ ") Values (";
												sql=sql + "'"+ a_vlist[i].replace("`", "','")+"')";
												
												System.out.println(sql);		
											int rst=	SqlExc01(con,sql);	
											
									}
									else // Update Record 
									{		System.out.println("update Start");		
									
										String sql_ = " Update " +tbl + "  set " ;
									
										for (int q=1;q<=a_flist.length-1;q++ ) 
										{
										
											sql_=sql_+ a_flist[q]+ "='" +aaa_vlist[q] + "',";
     									}
										sql_ = sql_.substring(0,sql_.length()-1);
										sql_ =sql_ + " Where " + a_flist[0]+ "=" +aaa_vlist[0];
										
										System.out.println(sql_);
								        int rst=SqlExc01(con,sql_);	
										
									}	// Update Record 

						} // for 1
						

			    	  String StrSql = "select " + fl+", hex(crc32(concat("+flist+"))) as crc,"+flist+" From "+tbl + " Where " +fl + " in " + fv ;
			    	  PreparedStatement	ps=con.prepareStatement(StrSql);
			         	System.out.println(StrSql);
			         	ResultSet	rs=ps.executeQuery();
			         	if (!rs.isBeforeFirst()) {
			         		
			         		obj.put("Err01","Get Data Error");
			    		    obj.put("status", "error");
			    			obj.put("code", 200);
			    			obj.put("message", "Record Not Found");
			    			obj.put("Data", "Reocrd Not Found");  	         		
			         	}
			         	else {
			         		if (rs.next()) {
			         			do {
			         				CrcString=CrcString + rs.getString(fl)+"!"+rs.getString("crc")+"|";
			         			         				
			         			} while(rs.next());
			         			
			         		}
			         		
			         		System.out.println(CrcString);
			         		Err01 = "InsUpd";
			         	}
					
					  }  //InsUpd
					
				   } 	else {	Err01="DatabaseError"; 	}
			 
					
		} //try001
		catch (Exception e) {
			e.printStackTrace();
			Err01="Error";
			ErrMsg =(e.getLocalizedMessage());
		} //try001
		finally {
			
		System.out.println("finaly ...."+Err01);
		//System.out.println(Qry);
		
		if (Err01.equals("Get_Cd_Status")) {
			obj.put("Err01",Err01);
		    obj.put("status", "ok");
			obj.put("code", 200);
			obj.put("message", "UPDATE");
			//obj.put("Data", found_cd);
			//obj.put("found_cecksum", found_cecksum);
			}
		

		if (Err01.equals("MysqlError")) {
			obj.put("Err01",Err01);
		    obj.put("status", "error");
			obj.put("code", 200);
			obj.put("message", "Mysql Not Running");
			obj.put("Data", "Mysql Not Running");  }
		if (Err01.equals("DatabaseError")) 
		{	obj.put("Err01",Err01);
			obj.put("status", "error");
			obj.put("code", 200);
			obj.put("message", "Database Not Found");
			obj.put("Data", db); 		 }
		
		 if (Err01.equals("Excute")) 
		 {
			obj.put("status", "ok");
			obj.put("code", 200);
			obj.put("message", "ExcuteQry");
			obj.put("Data", ExcRecord);
			obj.put("crc", CrcString);
			
		 }
		 if (Err01.equals("InsUpd")) 
		 {	obj.put("Err01",Err01);
			obj.put("status", "ok");
			obj.put("code", 200);
			obj.put("message", "InsUpd");
			obj.put("Data", ExcRecord);
			obj.put("crc", CrcString);
		 }
		 
		 
		 if (Err01.equals("Error")) 
		 {	obj.put("Err01",Err01);
			obj.put("status1", "error");
			obj.put("code1", 200);
			obj.put("message2", ErrMsg);
			obj.put("Data1", Qry);   	 }
	    	}
		return obj;
	}
	
	
	/*
	 Group Table 
	  
	  TMGrpup
	  	Group, gcode, Gtype
	  
	  Mst_group 
	    Grp_nm,grp_cd,grp_typ,grp_order,u_Cd,c_cd
	  
	  
	  
	  
	  */
	

	 private static int SqlExc01(Connection con1, String StrSql)  {
			Statement st = null;
			
			try {
				st = con1.createStatement();
			int a = st.executeUpdate(StrSql);
			return a;
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
	    
	 @SuppressWarnings("finally")
	private  static String Get_Data(Connection con, String StrSql) throws SQLException  {
			
		    String result="";
			PreparedStatement ps=null;
			ResultSet rs=null;
		    	try {
		    
		    	ps=con.prepareStatement(StrSql);
		    	System.out.println(StrSql);
		    	rs=ps.executeQuery();
		    	
		    	if (rs.next()) 
					{
						 result = rs.getString(1);

					}
					else
					{
						result = "error";
					}	
		    	}
				catch (SQLException e) {
					System.out.println(e.getMessage());
					result = "error";
				}
		    	finally {
		    		
		    	
					//if(con!=null) { con.close(); }
					
					//if(rs!=null)  { rs.close();	}
					
					//if(ps!=null)  { ps.close(); }
					
					return result;
				}
	
	
	
	 }	

}
