package Controller_acc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.zip.CRC32;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;
import com.sun.tools.javac.util.DefinedBy.Api;

import Controller.Mailer;
import Util.ConnectionUtil;

public class Api_Acc {

		static JSONObject objnull=new JSONObject();
public  static String Account_api_1_userkey(String Api, String mobile,String ip,String tokan,String username,String userkey) throws JSONException, SQLException, IOException {
		
		JSONObject obj=new JSONObject();
		JSONObject obj_1=new JSONObject();
		Connection con=ConnectionUtil.getConnection("acc_main","localhost");
		 
        String uuid="0";
        String EncKey=""; 
		String Otp = "0";
		String Responce1 = ""; 
		int i_Sql = 0;
		
		String DbName = ""; 
			
		if(con==null) {	
			obj.put("status", "error");
			//System.out.println("error06"); 
			obj.put("code", 401);
			obj.put("message", "MySql Database Error");
			Api="99";
		}else {
				
		uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
		i_Sql = SqlExc01(con,"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + ip + "','Acc' )");
	//	System.out.println("Strat----" + Api+" - "+mobile + " uuid" +uuid); 
		 //Session01.setAttribute("ip", ip);
		 
		 String ip0001 =Api_Acc.Get_Data(con, " SELECT Fun_put_Session('"+tokan+"','ip','"+ip+"')");
		}
		
		api01: {
			if(Api.equals("1") || Api.equals("11")) // 1 Only Mobile No =============================
			{  
			
				i_Sql = SqlExc01(con, "update logfile.api_log set Api_String='" + Api + "|" + username	+ "|' where id= " + uuid);

					
					String Sqlll = " SELECT `Nc_database`,userkey  FROM mst_user,`mst_newclint`\r\n"
							+ " WHERE u_LOGIN = '"+username+"' \r\n"
							+ " AND `userkey` = '"+userkey+"' AND mst_newclint.`Cl_cd` =  mst_user.`Cl_Cd`";
							
					
					PreparedStatement ps = con.prepareStatement(Sqlll);
					ResultSet rs = ps.executeQuery();
					
					//System.out.println(Sqlll); 
					
					EncKey = Crc32_(uuid);
					
					
					if (rs.next()) {
						
	 				    DbName=rs.getString("Nc_database");
	 				    
	 				    
	 					//Session01.setAttribute("DbName", DbName);
	 					//Session01.setAttribute("EncKey", EncKey);
	 					//Session01.setAttribute("Strat","Y" );
	 					
	 					String aa124  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','DbName','"+DbName+"')");
	 					String aa125  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','EncKey','"+EncKey+"')");
	 					String aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','Strat','Y')");
	 					
	 					
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','mst_compney','tbl')");
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','Mst_state','tbl')");
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','mst_compney_year','tbl')");
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','mst_taxrate','tbl')");
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','mst_group','tbl')");
	 					 aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','mst_subgroup','tbl')");
	 					aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','item','module')");
	 					aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','ledger','module')");
	 					aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','sale','module')");
	 					aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','pur','module')");
	 					aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','recpayment','module')");
	 					
	 					
						obj.put("status", "success");
						obj.put("code", 200);
						obj.put("message",  "Userkey Authonication Done");
						obj_1.put("module", "item,ledger,sale,pur,recpayment");
						obj_1.put("tokan", tokan);
						obj_1.put("table", "mst_compney,Mst_state,mst_compney_year,mst_taxrate,mst_group,mst_subgroup");
						obj.put("data", obj_1);
						//obj.put("Db", DbName);
									
						
						//obj = null;

					} else {
						//Session01.setAttribute("Strat","N" );
						String aa126  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','Strat','-')");
						obj.put("status", "error");
					//	System.out.println("error01.."); 
						obj.put("code", 401);
						obj.put("message", "Userkey Authonication Failed");
						obj.put("data", objnull);
						
						//obj = null;
					}
				
			} // if api 1
		} // 1 =============================
		
		//System.out.println("LSt:"+obj);
		return obj.toString();
		
	}/// Api 1 end	 Userkey
	
public  static String Account_api_1(String Api, String mobile,String ip,String tokan,String username,String password) throws JSONException, SQLException, IOException {
	
	JSONObject obj=new JSONObject();
	JSONObject obj_01=new JSONObject();
	Connection con=ConnectionUtil.getConnection("acc_main","localhost");
	 
	String userkey =getMd5(new Date().toString()+"ketan");
	
	 String uuid="0";
	String Otp = "0";
	String Responce1 = ""; 
	int i_Sql = 0;
	
	String DbName = ""; 
		
	if(con==null) {	
		obj.put("status", "error");
		obj.put("code", 401);
		obj.put("message", "MySql Database Error");
		//System.out.println("error10"); 
		Api="99";
	}else {
			
	uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
	i_Sql = SqlExc01(con,"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + ip + "','Acc' )");
	//System.out.println("Strat----" + Api+" - "+mobile + " uuid" +uuid); 
	 
	//Session01.setAttribute("ip", ip);
	String aa127  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','ip','"+ip+"')"); 
	
	}
	
	api01: {
		if(Api.equals("1") || Api.equals("11")) // 1 Only Mobile No =============================
		{  
		
			if (!mobile.equals("")) {
				  
				i_Sql = SqlExc01(con, "update logfile.api_log set Api_String='" + Api + "|" + mobile	+ "|' where id= " + uuid);

				
				//"SELECT NC_Mobile,AdroidYN,ifnull(otp,0) as otp,Nc_database  FROM  acc_main.mst_newclint WHERE NC_Mobile = '" + mobile + "'"
				String Sqlll = " SELECT `Nc_database`,otp, CASE WHEN IFNULL(NOW()-otpdt,500)>499 THEN 500 ELSE IFNULL(NOW()-otpdt,500)  END AS otpdt1  FROM mst_user,`mst_newclint`\r\n"
						+ " WHERE u_LOGIN = '"+username+"' \r\n"
						+ " AND `U_password` =  '"+password+"' \r\n"
						+ " AND `U_mobile` = '"+mobile+"' AND mst_newclint.`Cl_cd` =  mst_user.`Cl_Cd`";
						
				
				PreparedStatement ps = con.prepareStatement(Sqlll);
				ResultSet rs = ps.executeQuery();
				
				///System.out.println(userkey); 
				
				if (rs.next()) {
					
 				
					if (rs.getInt("otpdt1")==500)	
				    {Otp = getRandomNumberString() ;
				      Otp = "123456";
				      i_Sql = SqlExc01(con, "update acc_main.mst_user  set userkey = '"+userkey+"', otp =" + Otp + " WHERE `U_mobile` = '" + mobile + "' and u_LOGIN ='"+username+"'");
				    }
					else { Otp=rs.getString("otp");  }
 				    DbName=rs.getString("Nc_database");
 				
					//System.out.println("Otp "+Otp+" Otp");
					
					/*if (!rs.getString("AdroidYN").toUpperCase().equals("Y")) {
						obj.put("status", "error");
						obj.put("code", 401);
						obj.put("message", "inactinve user");
						//obj.put("data", "N");
						obj.put("otp", "");
						Responce1 = obj.toString();
						break api01;
					}*/
					
					
				
				//	System.out.println("Otp is :" + Otp); 
					//String strSMS = "Your OTP is " + Otp + " For E-gam APP, Krishna Software ";
					//strSMS = URLEncoder.encode(strSMS, "UTF-8");
					//String Main1 = "http://mobizz.hginfosys.co.in/sendsms.jsp?user=DZling12&password=e06c72d4a2XX&mobiles=" + mobile + "&sms=" + strSMS + "&senderid=Krisna&unicode=1";
					
					
					String strSMS = "OTP FOR User REG IS "+Otp+" FROM KRISHNA COMPUTER";
					strSMS = URLEncoder.encode(strSMS, "UTF-8");
		
					/*
					String Main1 ="http://sms.krishnasoftware.com/sendsms.jsp?user=Kim001&password=9827177854XX&senderid=KlSHNA&mobiles=+91"+mobile+"&tempid=1207162288031458872&sms="+strSMS;
					//System.out.println(Main1); 
								
					URL url = new URL(Main1);
					HttpURLConnection uc = (HttpURLConnection) url.openConnection();

					BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

					// uc.disconnect();

					String Rslt1 = "";
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						Rslt1 = Rslt1 + inputLine.toString();
					}
					 System.out.println("0001"+Rslt1);
					in.close();
					if (Rslt1.indexOf("<status>success</status>") > 0) {

						// String to="ketan@krishnasoftware.com";
						// String subject="Sms Send";
						// String masg=Rslt1+"\n"+strSMS;
						// Mailer.send(to, subject, masg);
					} else {

						String to = "ketan@krishnasoftware.com";
						String subject = "Api Problem";
						String masg = Rslt1 + "\n" + Main1;
						Mailer.send(to, subject, masg);

						Otp = mobile.substring(0, 2) + mobile.substring(mobile.length() - 2, mobile.length());
						obj.put("status", "error");
						obj.put("code", 401);
						obj.put("message", "SMS Api Problem - "+Rslt1);
						//obj.put("data","N" );
						obj.put("otp", "");
						Responce1 = obj.toString();
						
						break api01;
					}
                        */
					   i_Sql = SqlExc01(con, "update acc_main.mst_user  set   userkey = '"+userkey+"' ,otp =" + Otp + ", otpdt = now() WHERE `U_mobile` = '" + mobile + "' and u_LOGIN ='"+username+"'");
					   
					 //  Session01.setAttribute("DbName", DbName);
					   String aa128  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','DbName','"+DbName+"')");
					   
					
					// obj_01.put("otp", Otp);
					 
					obj.put("status", "success");
					obj.put("code", 200);
					obj.put("message", "Mobile Registered Send otp for Authonication   " ); // 000000000
					obj_01.put("tokan", tokan);
					obj.put("data",obj_01);
					
					
					
					// Session01.setAttribute("Strat","0");
					 String aa129  = Api_Acc.Get_Data(con, " SELECT `Fun_Put_Session`('"+tokan+"','Strat','0')");
				
					
					//obj.put("Db", DbName);
				
					
					//obj.put("data","Y");
					//if (Api.equals("11")) {
						
					//} // remove latter
					
					Responce1 = obj.toString();
					
					//obj = null;

				} else {
					obj.put("status", "error");
					//System.out.println("error11"); 
					obj.put("code", 401);
					obj.put("message", "Mob no Not Register...");
					obj.put("data", objnull);
					obj.put("otp", "");
					Responce1 = obj.toString();
					//obj = null;
				}
			}
		} // if api 1
	} // 1 Only Mobile No =============================
	
//	System.out.println("LSt:"+obj);
	return obj.toString();
	
}/// Api 1 end

public  static String Account_api_2(String Api,String tokan,String otp,String mobile) throws JSONException, SQLException, IOException {
	
	//System.out.println("Strat  001 :" + Session01.getId()); 
	
	JSONObject obj=new JSONObject();
	JSONObject obj_1=new JSONObject();
	JSONArray arr = new JSONArray();
	String uuid="0";
	Connection con01=ConnectionUtil.getConnection("acc_main","localhost");
	
	
	int i_Sql = 0;
	//String dbname = (String) Session01.getAttribute("DbName");
	
	String dbname =Api_Acc.Get_Data(con01, " SELECT `Fun_get_Session`('"+tokan+"','dbname')");
	String aa126  = Api_Acc.Get_Data(con01, " SELECT `Fun_Put_Session`('"+tokan+"','Strat','-')");
	
	//System.out.println("Strat  002:" +Session01.getId()+" " + dbname); 
	
	
	if (dbname == null) {
		obj.put("status", "error");
	//	System.out.println("error12"); 
		obj.put("code", 401);
		obj.put("message", "Session Time Out or Session Not Created ..");
		Api="99";
	} else {
	
	//String ip = (String) Session01.getAttribute("ip");
		String ip =Api_Acc.Get_Data(con01, " SELECT Fun_get_Session('"+tokan+"','ip')");
	
	
	
	Connection con=ConnectionUtil.getConnection(dbname,"localhost");
	
	if(con==null) {	
		obj.put("status", "error");
		//System.out.println("error13"); 
		obj.put("code", 401);
		obj.put("message", "MySql Database Not Found");
		Api="99";
	}else {
		
	
	uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
	i_Sql = SqlExc01(con,"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + ip + "','Acc' )");
	
	}
	
	Api02:{
		try {
			String StrSql= " select * from  acc_main.mst_user  where  otp = '"+otp+"'  and   U_mobile = '" + mobile + "'";
			PreparedStatement ps001 = con.prepareStatement(StrSql);
			ResultSet RecSet = ps001.executeQuery();
				
			if (!RecSet.isBeforeFirst()) {
				
				obj.put("status", "error");
				obj.put("code", 401);
				obj.put("message", "Otp Not Authorised Try Again");
				obj.put("data", objnull);
				//Session01.setAttribute("Strat","N" );
				String ip0001 =Api_Acc.Get_Data(con01, " SELECT Fun_put_Session('"+tokan+"','Strat','N')");
				
			}
			
			
			 if  (RecSet.next()) 
			{ 	
				obj.put("status", "success");
				obj.put("code", 200);
				obj.put("message", "Otp Authorised");
				obj_1.put("UserKey", RecSet.getString("userkey"));
				obj_1.put("module", "item,ledger,sale,pur");
				obj_1.put("table", "mst_compney,Mst_state,mst_compney_year,mst_taxrate,mst_group,mst_subgroup");
				obj.put("data", obj_1);
				//Session01.setAttribute("Strat","Y" );
				
				String ip0001 =Api_Acc.Get_Data(con01, " SELECT Fun_put_Session('"+tokan+"','Strat','Y')");
				

		    }
			 RecSet.close();
		}
		catch (SQLException e) {
			obj.put("status", "error");
			obj.put("code", 401);
			obj.put("message", e.getLocalizedMessage());
			obj.put("data", objnull);
			//Session01.setAttribute("Strat","N" );
			String ip0002 =Api_Acc.Get_Data(con01, " SELECT Fun_put_Session('"+tokan+"','Strat','N')");
			Api="99";
		}
	}
	} //Session01 == null
		
	return obj.toString();
}

public  static String Account_api_3(String Api,String tokan,String table) throws JSONException, SQLException, IOException {
	
		
	System.out.println("api03---01 "+Api);
	
	JSONObject obj=new JSONObject();
	JSONObject obj_01=new JSONObject();
	JSONArray arr = new JSONArray();
	

	
	
    Connection con01=ConnectionUtil.getConnection("acc_main","localhost");

	
	//String ip = (String) Session01.getAttribute("ip");
	//String dbname = (String) Session01.getAttribute("DbName");
	
    String dbname =Api_Acc.Get_Data(con01, " SELECT `Fun_get_Session`('"+tokan+"','dbname')");
    String ip =Api_Acc.Get_Data(con01, " SELECT `Fun_get_Session`('"+tokan+"','ip')");
    String tbl =Api_Acc.Get_Data(con01, " SELECT `Fun_get_Session`('"+tokan+"','"+table+"')");
	
    System.out.println(tbl);
    
    
    if (tbl.equals("NULL")) {
    	obj.put("status", "error");
		obj.put("code", 401);
		obj.put("message", table + " Not Found in Database");
		obj.put("data", objnull);
		Api="99";
    	
    }
    
	
	String uuid="0";
	int i_Sql = 0;
	

	if (Api.equals("3")) {
	Connection con=ConnectionUtil.getConnection(dbname,"localhost");
	if(con==null) {	
		obj.put("status", "error");
		obj.put("code", 401);
		obj.put("message", "MySql Database Not Found");
		obj.put("data", objnull);
		Api="99";
	}else {
			
	uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
	i_Sql = SqlExc01(con,"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + ip + "','Acc' )");
	//System.out.println("Strat--ketan--" + Api+" - "+table + " uuid" +uuid); 
	}
	
	
	Api03:{
		try {
			String StrSql= " select * from "+dbname+"."+table ;
			PreparedStatement ps001 = con.prepareStatement(StrSql);
			ResultSet RecSet = ps001.executeQuery();
		
			java.sql.ResultSetMetaData metaData = RecSet.getMetaData();

			 
			  int numberOfColumns = metaData.getColumnCount();
			//  System.out.println("api02---02 -"+ numberOfColumns ); 
			
			while (RecSet.next()) {
				JSONObject obj1 = new JSONObject();
				for (int i=1;i<=numberOfColumns;i++ ) {
					obj1.put(metaData.getColumnName(i), RecSet.getString(i));
				}
				arr.put(obj1);
			}
			
			obj_01.put(table, arr);
			obj.put("status", "success");
			obj.put("code", 200);
			obj.put("message", "");
			obj.put("data", obj_01);
			
		//	System.out.println("ok 001"); 
		}
		catch (SQLException e) {
			//System.out.println("error-0-01"); 
			obj.put("status", "error");
			obj.put("code", 401);
			obj.put("message", e.getLocalizedMessage());
			obj.put("data", objnull);
			Api="99";
		}
		
		
		
	}}
	
		
	return obj.toString();
}//End Api 3

@SuppressWarnings("null")
public  static String Account_api_module_4(String Api,String tokan,String module,String Ldt ) throws JSONException, SQLException, IOException {
	
	//System.out.println("start 0000" ); 
	
	JSONObject obj=new JSONObject();
	JSONObject obj_01=new JSONObject();
	JSONArray arr = new JSONArray();
	
	Connection con01=ConnectionUtil.getConnection("acc_main","localhost");

	
	//String ip = (String) Session01.getAttribute("ip");
	//String dbname = (String) Session01.getAttribute("DbName");
	
	String SQL001 = " SELECT acc_main.Fun_get_Session('"+tokan+"','DbName') as db";
	//System.out.println(tokan+SQL001 ); 
	
	String dbname =Api_Acc.Get_Data(con01, SQL001);
	String ip =Api_Acc.Get_Data(con01, " SELECT `Fun_get_Session`('"+tokan+"','ip')");
	
	//System.out.println(tokan+"tokan " ); 
	
	
	String uuid="0";
	String Otp = "0";
	String table ="";
			
	
	int i_Sql = 0;
	
	Connection con=ConnectionUtil.getConnection(dbname,"localhost");
	if(con==null) {	
		obj.put("status", "error");
		obj.put("code", 401);
		obj.put("message", "MySql Database Not Found");
		obj.put("data", objnull);
		Api="99";
	}else {
			
	uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
	i_Sql = SqlExc01(con,"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + module + "','Acc' )");
	
	}
	
	if (module.equals("item") || module.equals("ITEM")  ) {
		try {
			String StrSql= " CALL Sp_Data_Exprot_Adroid('ITEM','"+Ldt+"');";
			
			PreparedStatement ps001 = con.prepareStatement(StrSql);
			ResultSet RecSet = ps001.executeQuery();
			java.sql.ResultSetMetaData metaData = RecSet.getMetaData();

			//System.out.println("api02---01 "+StrSql ); 
			
			  int numberOfColumns = metaData.getColumnCount();
			  
			while (RecSet.next()) {
				
				 
				JSONObject obj1 = new JSONObject();
				for (int i=1;i<=numberOfColumns;i++ ) {
					obj1.put(metaData.getColumnName(i), RecSet.getString(i));
				}
				arr.put(obj1);
				
			}
			
			obj_01.put("Mst_item", arr);  ///   1
			arr = new JSONArray();
			RecSet.close();
			
					
			 ps001.getMoreResults();
			 RecSet = ps001.getResultSet();
			// System.out.println("M_hsn" ); 
			 
			
			if (!RecSet.isBeforeFirst()) {
			//	System.out.println("?No Data M_hsn" );
				obj_01.put("M_hsn", objnull);
			}
			else {				
				 metaData = RecSet.getMetaData();
				 numberOfColumns = metaData.getColumnCount();
				 				 
				while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					for (int i=1;i<=numberOfColumns;i++ ) 	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
					arr.put(obj1);
				}
				obj_01.put("M_hsn", arr);  ///   2
			 }
			arr = new JSONArray();
			
			
			
			
			
			RecSet.close();
			 ps001.getMoreResults();
			 RecSet = ps001.getResultSet();
			// System.out.println("m_itm_grp" ); 
			
			if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data m_itm_grp" );
				obj_01.put("m_itm_grp", objnull);
			}
			else {		
				
				  metaData = RecSet.getMetaData();
				 numberOfColumns = metaData.getColumnCount();
			 	while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
					arr.put(obj1);
				}
			 	obj_01.put("m_itm_grp", arr);    ///   3
			 }
			arr = new JSONArray();
			
			RecSet.close();
			 ps001.getMoreResults();
			 RecSet = ps001.getResultSet();
			// System.out.println("m_itm_sgrp" ); 
			
			if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data m_itm_sgrp" );
				obj_01.put("m_itm_sgrp", objnull);
			}
			else {		
				  metaData = RecSet.getMetaData();
				 numberOfColumns = metaData.getColumnCount();
				 
				 
				while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					//System.out.println(numberOfColumns);
					for (int i=1;i<=numberOfColumns;i++ )   {obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
					arr.put(obj1);
				}
				obj_01.put("m_itm_sgrp", arr);    ///   4
			 }
			 arr = new JSONArray();
			 RecSet.close();
			 
			 
			 ps001.getMoreResults();
			 RecSet = ps001.getResultSet();
		//	 System.out.println("m_itm_mfg" ); 
			
			if (!RecSet.isBeforeFirst()) { //	System.out.println("?No Data  m_itm_mfg" );
				obj_01.put("m_itm_mfg", objnull);
			}
			else {		
			//	 System.out.println("11" ); 
				  metaData = RecSet.getMetaData();
				 numberOfColumns = metaData.getColumnCount();
				 
				 
				while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
					arr.put(obj1);
				}
				obj_01.put("m_itm_mfg", arr);    ///   5
			 }
			arr = new JSONArray();
			
			
			
			obj.put("status", "success");
			obj.put("code", 200);
			obj.put("message", "");
			obj_01.put("Module","ITEM");
			obj.put("data",obj_01);
			
			arr = new JSONArray();
			 RecSet.close();;
			
		}
		catch (SQLException e) {
			obj.put("status", "error");
			obj.put("code", 401);
			obj.put("message", e.getLocalizedMessage());
			obj.put("data",objnull);
			Api="99";
		}
		
		
	
	}//ITEM
	
	if ( module.equals("LEDGER") ) {
		 arr = new JSONArray();
		 obj=new JSONObject();
		 
		 
			try {
				String StrSql= " CALL Sp_Data_Exprot_Adroid('LEDGER','"+Ldt+"');";
				
				PreparedStatement ps001 = con.prepareStatement(StrSql);
				ResultSet RecSet = ps001.executeQuery();
				java.sql.ResultSetMetaData metaData = RecSet.getMetaData();

				//System.out.println("api02---01 "+StrSql ); 
				  int numberOfColumns = metaData.getColumnCount();
				//  System.out.println("api02---02 -"+ numberOfColumns ); 
				  
				while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					for (int i=1;i<=numberOfColumns;i++ ) {
						obj1.put(metaData.getColumnName(i), RecSet.getString(i));
					}
					arr.put(obj1);
					
				}
				obj_01.put("Mst_Ledger", arr);  ///   1
				arr = new JSONArray();
				RecSet.close();
				
				
				 ps001.getMoreResults();
				 RecSet = ps001.getResultSet();
				// System.out.println("mst_ledger_l" ); 
				
				if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data mst_ledger_l" );
					obj_01.put("mst_ledger_l", objnull);
				}
				else {		
					
					  metaData = RecSet.getMetaData();
					 numberOfColumns = metaData.getColumnCount();
				 	while (RecSet.next()) {
						JSONObject obj1 = new JSONObject();
						for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
						arr.put(obj1);
					}
				 	obj_01.put("mst_ledger_l", arr);    ///   3
				 }
				arr = new JSONArray();
				RecSet.close();
				
				 ps001.getMoreResults();
				 RecSet = ps001.getResultSet();
				// System.out.println("Mst_group" ); 
				
				if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data Mst_group" );
					obj_01.put("Mst_group", objnull);
				}
				else {		
					
					  metaData = RecSet.getMetaData();
					 numberOfColumns = metaData.getColumnCount();
				 	while (RecSet.next()) {
						JSONObject obj1 = new JSONObject();
						for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
						arr.put(obj1);
					}
				 	obj_01.put("Mst_group", arr);    ///   3
				 }
				arr = new JSONArray();
				RecSet.close();
				
				 ps001.getMoreResults();
				 RecSet = ps001.getResultSet();
				// System.out.println("mst_subgroup" ); 
				
				if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data mst_subgroup" );
					obj_01.put("mst_subgroup", objnull);
				}
				else {		
					
					  metaData = RecSet.getMetaData();
					 numberOfColumns = metaData.getColumnCount();
				 	while (RecSet.next()) {
						JSONObject obj1 = new JSONObject();
						for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
						arr.put(obj1);
					}
				 	obj_01.put("mst_subgroup", arr);    ///   3
				 }
				arr = new JSONArray();
				RecSet.close();
     		
			
			obj.put("status", "success");
			obj.put("code", 200);
			obj.put("message", "");
			obj_01.put("Module",module);
			obj.put("data",obj_01);
			
	}
			
			catch (SQLException e) {
				obj.put("status", "error");
				obj.put("code", 401);
				obj.put("message", e.getLocalizedMessage());
				obj.put("data",objnull);
				Api="99";
			}
	
		}  //LEDGER
	
	
	if ( module.equals("SALE") || module.equals("PUR")) {
		 arr = new JSONArray();
		 obj=new JSONObject();
		 obj_01=new JSONObject();
		 
		 String t1 = "trn_sale";
		 String t2 = "trn_sale_l";
		 String t3 = "trn_sale_ad";
		 
		 if (module.equals("PUR")) {t1 = "trn_purchase"; t2 = "trn_purchase_l"; t3 = "trn_purchase_AD"; }
			try {
				String StrSql= " CALL Sp_Data_Exprot_Adroid('"+module+"','"+Ldt+"');";
				
				PreparedStatement ps001 = con.prepareStatement(StrSql);
				ResultSet RecSet = ps001.executeQuery();
				java.sql.ResultSetMetaData metaData = RecSet.getMetaData();

				System.out.println(module+ ":" +StrSql ); 
				
				  int numberOfColumns = metaData.getColumnCount();
				//  System.out.println("api02---02 -"+ numberOfColumns ); 
				  
				while (RecSet.next()) {
					JSONObject obj1 = new JSONObject();
					for (int i=1;i<=numberOfColumns;i++ ) {
						obj1.put(metaData.getColumnName(i), RecSet.getString(i));
					}
					arr.put(obj1);
				}
				obj_01.put(t1, arr);  ///   1
				arr = new JSONArray();
				RecSet.close();
				
				 ps001.getMoreResults();
				 RecSet = ps001.getResultSet();
				// System.out.println(t2 ); 
				
				if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data mst_ledger_l" );
					obj_01.put(t2, objnull);
				}
				else {		
					  metaData = RecSet.getMetaData();
					  numberOfColumns = metaData.getColumnCount();
				 	while (RecSet.next()) {
						JSONObject obj1 = new JSONObject();
						for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
						arr.put(obj1);
					}
				 	obj_01.put(t2, arr);    ///  2
				 }
				arr = new JSONArray();
				RecSet.close();
				
				 ps001.getMoreResults();
				 RecSet = ps001.getResultSet();
				// System.out.println(t3 ); 
				
				if (!RecSet.isBeforeFirst()) {  //	System.out.println("?No Data Mst_group" );
					obj_01.put(t3, objnull);
				}
				else {		
					
					  metaData = RecSet.getMetaData();
					 numberOfColumns = metaData.getColumnCount();
				 	while (RecSet.next()) {
						JSONObject obj1 = new JSONObject();
						for (int i=1;i<=numberOfColumns;i++ )  	{	obj1.put(metaData.getColumnName(i), RecSet.getString(i));	}
						arr.put(obj1);
					}
				 	obj_01.put(t3, arr);    ///   3
				 }
				arr = new JSONArray();
				RecSet.close();
				
				 
				obj.put("status", "success");
				obj.put("code", 200);
				obj.put("message", "");
				obj_01.put("Module",module);
				obj.put("data",obj_01);
				
				
    		}
			
			
		
			catch (SQLException e) {
				obj.put("status", "error");
				obj.put("code", 401);
				obj.put("message", e.getLocalizedMessage());
				obj.put("data",objnull);
				Api="99";
			}
	
		}  //sALE or Pur

	
	if  (obj.toString().equals("{}")) {
		
		obj.put("status", "error");
		obj.put("code", 401);
		obj.put("message", "Invalid Module name");
		obj.put("data",objnull);
		Api="99";
	}
	
	
	
		
	return obj.toString();
}//End Api 4



public static String getRandomNumberString() {
	Random rnd = new Random();
	int number = rnd.nextInt(99999);
	if (number < 100000) {
		number = number + 100000;
	}
	return String.format("%06d", number);
}
public static String Crc32_(String crcstring) {

	CRC32 crc = new CRC32();
	crc.update(crcstring.getBytes());
	return Long.toHexString(crc.getValue());

}
public static String Get_Data(Connection con1, String StrSql) throws SQLException {
	PreparedStatement ps001 = con1.prepareStatement(StrSql);
	ResultSet RecSet = ps001.executeQuery();
	RecSet.next();

	String result = RecSet.getString(1);
	// System.out.println(result);

	RecSet.close();
	ps001.close();
	return result;
}
private static int SqlExc01(Connection con1, String StrSql)  {
	Statement st = null;
	
	try {
		st = con1.createStatement();
	int a = st.executeUpdate(StrSql);
	return a;
	}
	catch (SQLException e) {
	//	System.out.println(e.getMessage());
		return 0;
	}
}
public static String getMd5(String input)
{
    try {

        // Static getInstance method is called with hashing MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(input.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    } 

    // For specifying wrong message digest algorithms
    catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}


}





/*
 * http://localhost:8084/Gst/AccApi_Adroid?Api=1&username=9825100931&userkey=2da8d4435e2209d52e35a863bea4f08c
 http://localhost:8084/Gst/AccApi_Adroid?&mobile=9825425385&Api=2&otp=123456
 http://localhost:8084/Gst/AccApi_Adroid?&table=mst_compney&Api=3
 http://localhost:8084/Gst/AccApi_Adroid?&module=ITEM&Api=4&Ldt=2020-01-01 18:17:07
 http://localhost:8084/Gst/AccApi_Adroid?&module=LEDGER&Api=4&Ldt=2020-01-01 18:17:07
 http://localhost:8084/Gst/AccApi_Adroid?&module=SALE&Api=4&Ldt=2021-07-14 18:17:07
 http://localhost:8084/Gst/AccApi_Adroid?&module=PUR&Api=4&Ldt=2020-01-01 18:17:07
 http://localhost:8084/Gst/AccApi_Adroid?&Api=logout
 
 
 
 

CREATE TABLE `tbl_session` (
  `Auto_no` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Uid` VARCHAR(100) NOT NULL,
  `dt` DECIMAL(20,0) DEFAULT '0',
  `TimeOut` INT(11) DEFAULT NULL,
  `Active` CHAR(1) DEFAULT NULL,
  PRIMARY KEY (`Uid`),
  KEY `Auto_no` (`Auto_no`)
) ENGINE=INNODB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;





CREATE TABLE `tbl_session_l` (
  `Uid` VARCHAR(100) DEFAULT NULL,
  `F` VARCHAR(100) DEFAULT NULL,
  `d` VARCHAR(1000) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DELIMITER $$

USE `acc_main`$$


CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_session`(
    IN cmd_ VARCHAR(100),
    IN uid_ VARCHAR(100),
    IN Timeout_ INT 
    )
BEGIN
      SET @Timeout = 0;
      IF Timeout_ = 0 THEN
         SET  Timeout_ = (100);
      END IF ;        
     
      IF cmd_ = 'New'   THEN  
         SET uid_ = (SELECT MD5( NOW()+123456789.123));
          INSERT INTO Tbl_Session ( uid,timeout,dt) VALUES  (uid_,Timeout_,NOW()+1);
          
         SELECT  uid_ ;
      END IF; 
    
      IF cmd_ = 'update'  THEN  
           
         SET  @Timeout = (SELECT  timeout- TIMESTAMPDIFF(SECOND,dt,NOW()) FROM Tbl_Session WHERE uid=uid_ );
    
              
	      IF @Timeout > 0 THEN     
	          UPDATE  Tbl_Session  SET dt = NOW()+1    WHERE  uid = uid_;
		  SELECT 'ok' AS uid;
	      ELSE
		  
		   DELETE FROM Tbl_Session  WHERE  uid = uid_;
                   DELETE FROM Tbl_Session_l  WHERE  uid = uid_;
                   
                   SELECT 'Session Time out' AS uid;
	      END IF;    
              
      END IF;
   
     IF cmd_ = 'remove' THEN 
            DELETE FROM Tbl_Session  WHERE  uid = uid_;
            DELETE FROM Tbl_Session_l  WHERE  uid = uid_;
            
            DELETE FROM Tbl_Session  WHERE  timeout- TIMESTAMPDIFF(SECOND,dt,NOW()) < 0;
            DELETE FROM Tbl_Session_l  WHERE  uid NOT IN ( SELECT uid FROM Tbl_Session );
               
            SELECT 'LogOut' AS uid;
     END IF;        
    
    END$$

DELIMITER ;




DELIMITER $$

USE `acc_main`$$



CREATE DEFINER=`root`@`localhost` FUNCTION `Fun_get_Session`(
    uid_ VARCHAR (100),
    Var_ VARCHAR (100)
    ) RETURNS VARCHAR(100) CHARSET utf8
BEGIN
       SET  @Timeout = (SELECT  timeout- TIMESTAMPDIFF(SECOND,dt,NOW()) FROM Tbl_Session WHERE uid=uid_ );
       IF @Timeout  > 0 THEN 
       
        SET @data =( SELECT d FROM tbl_session_L   WHERE uid=uid_  AND f = Var_ );
       END IF ;
       
       
         RETURN @data;
       
    END$$

DELIMITER ;




DELIMITER $$

USE `acc_main`$$

DROP FUNCTION IF EXISTS `Fun_Put_Session`$$

CREATE DEFINER=`root`@`localhost` FUNCTION `Fun_Put_Session`(
      uid_ VARCHAR (100),
      Var_ VARCHAR (100),    
      data_ VARCHAR (1000)    
    
    ) RETURNS VARCHAR(100) CHARSET utf8
BEGIN
       SET  @Timeout =  (SELECT TimeOut -(dt- NOW()+1) FROM Tbl_Session WHERE uid=uid_ );
       IF @Timeout  > 0 THEN 
       
        SET @count = (SELECT COUNT(*) FROM tbl_session_L WHERE uid=uid_  AND f = Var_  )  ;         
        
        IF @count =0 THEN 
            INSERT INTO  tbl_session_L  (f,d,uid) VALUES (var_,data_,uid_);
        ELSE
            UPDATE tbl_session_L SET d= data_  WHERE uid=uid_  AND f = Var_   ;
        END IF ;
         RETURN 'ok';
       ELSE
        RETURN 'Timeout'  ; 
       
       END IF;
       
        
    END$$

DELIMITER ;



  
  * 
 * */

