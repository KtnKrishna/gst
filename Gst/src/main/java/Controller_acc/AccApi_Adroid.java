package Controller_acc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.zip.CRC32;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ini4j.Wini;
import org.json.JSONException;
import org.json.JSONObject;

import Controller.API_New;
import Util.ConnectionUtil;
import Util.inif;


/**
 * Servlet implementation class AccApi_Adroid
 */
@WebServlet("/AccApi_Adroid")
public class AccApi_Adroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccApi_Adroid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		
		JSONObject obj=new JSONObject();
		JSONObject objnull=new JSONObject();
		
		
		     HttpSession  session = request.getSession();
			 session.setMaxInactiveInterval(300);
			 System.out.println(session.getId());
		
			 Connection con=ConnectionUtil.getConnection("acc_main","localhost");
	
		String table="";
		
		String mobile ="";
		String otp ="";
		String module ="";
		String Dbname ="";
		String Ldt ="";
		
		String username = "";
		String password = "";
		
		String Api ="";
		String authkey ="";
			
		String userkey ="";
		String key ="";
		String tokan ="";
		
		if (request.getParameter("tokan") != null) {tokan = request.getParameter("tokan"); }
		
		if (request.getParameter("key") != null) {	key = request.getParameter("key"); }
		
		if (request.getParameter("userkey") != null) {	userkey = request.getParameter("userkey"); }
		if (request.getParameter("username") != null) {	username = request.getParameter("username"); }
		if (request.getParameter("password") != null) {	password = request.getParameter("password"); }
		
		
		if (request.getParameter("Api") != null) {	Api = request.getParameter("Api"); }
		if (request.getParameter("authkey") != null) {	authkey = request.getParameter("authkey"); }
		if (request.getParameter("mobile") != null) {	mobile = request.getParameter("mobile"); }
		if (request.getParameter("otp") != null) {	otp = request.getParameter("otp"); }
		if (request.getParameter("table") != null) {	table = request.getParameter("table"); }
		if (request.getParameter("module") != null) {	module = request.getParameter("module");   module = module.toUpperCase() ;  }
		if (request.getParameter("Ldt") != null) {	Ldt = request.getParameter("Ldt"); }
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {	ip = request.getHeader("Proxy-Client-IP"); 	}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {	ip = request.getHeader("WL-Proxy-Client-IP"); 	}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {	ip = request.getRemoteAddr(); 	}
	
			 try {
	 
				 
				 if  (tokan.equals("") ) {
				      tokan = Api_Acc.Get_Data(con, " CALL  SP_session('new','','300')");
				     
			      
				      //String aaa = inif.Session_New(100);
				      
				      
				      String tkn="100-f4a47daf-6058";
				      
				     //String abc01= inif.Session_Put(tkn,"dbname","ketan");
				     
				     String abc01= inif.Session_Get(tkn,"dbname");
				      
				      
				    // Wini ini = new Wini(new File("inifile.ini"));
			        // String ini01 = ini.get("Hdd", "Hdd", String.class);
				      
				      
				      System.out.println(abc01);
				      
				 }else {
					 String aa123  = Api_Acc.Get_Data(con, " CALL  SP_session('update','"+tokan+"','0')");	
					  String aaa125 =Api_Acc.Get_Data(con, " SELECT `Fun_put_Session`('"+tokan+"','LastAccess','"+java.util.Calendar.getInstance().getTime()+"')");
					 System.out.println("Update session : "+aa123); 
					 
					 
					 if  (!aa123.equals("ok")) {		
						 Api = "99";
						    obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", "Logout successfully");
							objnull.put("data", aa123);
							obj.put("data", objnull);
							 pw.print(obj);
					 
					 }
					 
				 }     
				 
			 
			if  (Api.equals("logout")) {								
					String aa1241  = Api_Acc.Get_Data(con, " CALL SP_session('remove','"+tokan+"','0')");	  
					if (aa1241.equals("LogOut")) {
						obj.put("status", "success");
						obj.put("code", 200);
						obj.put("message", "Logout successfully");
						objnull.put("data", aa1241);
						obj.put("data", objnull);
						
						 pw.print(obj);
					} else { 
						obj.put("status", "error");
					    obj.put("code", 200);
					    obj.put("message", "Logout or invalid tokan");
					    objnull.put("data", aa1241);
					    obj.put("data", objnull);
					    pw.print(obj);
					}
				

			   }else {
				   
		  
				if (Api.equals("1") && !userkey.equals("")  &&  !username.equals("")) {
						// System.out.println("Strat--api-1-"); 
						 	String OutPut = Api_Acc.Account_api_1_userkey(Api,mobile, ip,tokan,username,userkey);
						 	
						 //	System.out.println(OutPut); 
						 	pw.print(OutPut);
						 	//System.out.println(session.getAttribute("DbName"));

					 }	   
				   
				if (Api.equals("1") || Api.equals("11")) {
					 if (userkey.equals(""))
					 { 
					 	String OutPut = Api_Acc.Account_api_1(Api,mobile, ip,tokan,username,password);
					 //	System.out.println(OutPut); 
					 	pw.print(OutPut);
					// 	System.out.println(Api_Acc.Get_Data(con, " SELECT Fun_get_Session('"+tokan+"','dbname')"));
					 }	
				 }
			 
				
				String ss01 =Api_Acc.Get_Data(con, " SELECT ifnull(Fun_get_Session('"+tokan+"','Strat'),'-')");
				//System.out.println(ss01);
			 if (ss01.equals("N")) {
				    obj.put("status", "error");
					obj.put("code", 401);
					obj.put("message", "Session Time Out or Session Not Created ");
					obj.put("data", objnull);
					pw.print(obj);
			 	 }else{
			 		 
			 
			 if (Api.equals("2") || Api.equals("22")) {   //Database table export
					String OutPut = Api_Acc.Account_api_2( Api, tokan, otp, mobile);
					pw.print(OutPut);
					
					
					
			 }	//Database table export
			 
			 
			
			 
			 if (Api.equals("3") || Api.equals("33")) { //Database table export
			    	
				//if (checkdigit(key, Api+table)==true) {	
				    String OutPut = Api_Acc.Account_api_3(Api,tokan,table);
					pw.print(OutPut);
			//	}else {  
			//		    session.invalidate();
			//		    obj.put("status", "error");
			//			obj.put("code", 200);
			//			obj.put("message", "Checksum Failed, User Logout");
			//			obj.put("Data", "[]");
			//			pw.print(obj);
			//	}
					
				
				
			 }	//Database table export
			 
		
			 if (Api.equals("4") || Api.equals("44")) {   //Database table export
				// if (checkdigit(key, Api+module+Ldt)==true) {	
					String OutPut = Api_Acc.Account_api_module_4(Api,tokan,module,Ldt);
					pw.print(OutPut);
			//	 }else {
			//		 session.invalidate();
			//		    obj.put("status", "error");
			//			obj.put("code", 200);
			//			obj.put("message", "Checksum Failed, User Logout");
			//			obj.put("data", "[]");
			//			pw.print(obj);
			//	 }
			 }	//Database table export
			 
			 } 
			   }
		  } catch (JSONException e) { e.printStackTrace(); } 
		 catch (SQLException e) {
		  
		 }
		
	}

	public static String Crc32_(String crcstring) {

		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		return Long.toHexString(crc.getValue());

	}

	
	public static boolean checkdigit(String key, String crcstring) {
		// System.out.println(key);
		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		String enc = Long.toHexString(crc.getValue());

		// System.out.println(enc);
		if (enc.equals(key)) {

			return true;
		}
		return false;
	}

	

}
