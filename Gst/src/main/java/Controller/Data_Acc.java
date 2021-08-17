package Controller;

// For Account Data collection vb6 Account Software



import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.json.JSONObject;

import Util.ConnectionUtil;


@WebServlet("/Data_Acc")
public class Data_Acc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Data_Acc() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws  ServletException, IOException {
    	
    	response.setContentType("text/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Connection con=null;
		
    try {
		
		PrintWriter out = response.getWriter();
		
    	
    	 String x =".";
    	 String Api = "0", authkey = "0", onlineid = "0", pid = "0",key="0";
    	 String md5_01="";
    	 String Sql = "";
    	 String webkey = "";
    	 String compnm = "";
    	 String xy = "00";
    	 String softyp = "0";
    	 String data = "";
    	 String compid = "";
    	 String HddSrno = "";
    	 String os = "";
    	 int ESql = 0 ; 
    	 
    	int o_id = 0;
    	
     	HttpSession session = request.getSession();
    	session.setMaxInactiveInterval(20);
    	md5_01=MD5.getMd5(session.getId(),"SHA-1");
    	
		 con = ConnectionUtil.getConnection("admin_db","localhost");
		
		if (request.getParameter("HddSrno") != null) {
			HddSrno = request.getParameter("HddSrno");
		}
		if (request.getParameter("compid") != null) {
			compid = request.getParameter("compid");
		}
		
		if (request.getParameter("os") != null) {
			os = request.getParameter("os");
		}
		
		if (request.getParameter("softyp") != null) {
			softyp = request.getParameter("softyp");
		}
		if (request.getParameter("compnm") != null) {
			compnm = request.getParameter("compnm");
		}
		if (request.getParameter("Api") != null) {
			Api = request.getParameter("Api");
		}
		if (request.getParameter("authkey") != null) {
			authkey = request.getParameter("authkey");
		}
		if (request.getParameter("onlineid") != null) {
			onlineid = request.getParameter("onlineid");
		}
		if (request.getParameter("pid") != null) {
			pid = request.getParameter("pid");
		}
		if (request.getParameter("webkey") != null) {
			webkey = request.getParameter("webkey");
		}
		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
		}	
		if (request.getParameter("data") != null) {
			data = request.getParameter("data");
		}	
		
		JSONObject obj = new JSONObject();
		
		if (Api.equals("1"))
		{
			if(!onlineid.equals("") && !pid.equals(""))
			{
			 
				Sql = "SELECT oid FROM admin_db.mst_user WHERE online_id = '"+onlineid+"' AND active = 'y'";
				o_id  = Integer.parseInt(API_New.Get_Data(con,Sql));
				
			
			 	if (o_id > 0) {
			 		out.println("O_id: "+o_id);
			 		session.setAttribute("a",md5_01);
			 		out.println("Session ID:" + session.getId());
			 		out.println("Auth Key: " + md5_01);}
			 	else {
			 		
			 		
						obj.put("status", "error");
						obj.put("code", 203);
						obj.put("message", "Invalid Onlineid");
					
		
									
					//Responce1 = obj.toString();
					out.write(obj.toString());
			 		
			 		out.println("Invalid Onlineid "+o_id);
			 		out.println(Sql);
			 	}
			 			
			 	}
	     }
		
		
		
		
		if (Api.equals("2")) {
			if(md5_01.equals(authkey)){
				out.println("Auth Key: OK");
			}
			else
			{
				out.println("Invalid authkey");	
			}
		}
		if (Api.equals("CompData")) 
		{     x=Api+webkey+data+compid+"ketan";
			
			//Sql = " Select count(*) from cli_company where skey = '"+webkey+"'" ;
		    //String dataexsist01 =Get_Data(con, Sql); 
		//System.out.println(data);
		     Sql = data;
		     
		     ESql = SqlExc01(con,Sql);
		   //  System.out.println(ESql);
		     
		     Sql = "Select SendStr from admin_db.cli_pc_qry where LENGTH(SendStr)>0 and  ExecuteTry < Try  and skey = '"+webkey+"' AND compid ='"+compid+"'";
		     String RepSend = API_New.Get_Data(con, Sql);
		     		
		  //   System.out.println(RepSend);
		     	     
		     if (!RepSend.equals("error")) {
		    	    obj.put("status", "ok");
					obj.put("code", 200);
					obj.put("message", "UpdateHDD");
					obj.put("Data", RepSend);
					obj.put("compid", compid);
					  out.write(obj.toString());
					    obj = null;	
					ESql = SqlExc01(con,"UPDATE admin_db.cli_pc_qry SET ExecuteTry=ExecuteTry+1 WHERE  skey = '"+webkey+"' AND compid ='"+compid+"'");
					
		     }else {
		    	 
		    	 Sql = "Select Qry from admin_db.cli_pc_qry where LENGTH(Qry)>0 and  ExecuteTry < Try  and skey = '"+webkey+"' AND compid ='"+compid+"'";
			     String RepSend1 = API_New.Get_Data(con, Sql);
		    	 
			     if (!RepSend1.equals("error")) {
			    	    obj.put("status", "ok");
						obj.put("code", 200);
						obj.put("message", "Qry");
						obj.put("Data", RepSend1);
						obj.put("compid", compid);
						out.write(obj.toString());
						obj = null;	
						ESql = SqlExc01(con,"UPDATE admin_db.cli_pc_qry SET ExecuteTry=ExecuteTry+1 WHERE  skey = '"+webkey+"' AND compid ='"+compid+"'");
			     }
			     else
			     {	 
			    	 obj.put("status", "ok");
			    	 obj.put("code", 200);
			    	 obj.put("message", "CompData");
			    	 obj.put("Data", ESql);
			    	 obj.put("compid", compid);
			    	 
			    	 //	obj.put("Data1", Sql);
		
			    	 out.write(obj.toString());
			    	 obj = null;
			     } 	 
		      }	
		 }
		
		if (Api.equals("Cli_pc")) 
		{   x=Api+webkey+compid+HddSrno+os+"ketan";
		       
			if (API_New.checkdigit(key, x) == true)
			{  
				Sql = " Select id from admin_db.Cli_pc where skey = '"+webkey+"' and compid = '"+compid+"' and Hddno = '"+HddSrno+"'";
				String dataexsist =API_New.Get_Data(con, Sql); 
				
				if (dataexsist.equals("error")) {
					Sql = "insert into admin_db.cli_PC (skey,compid,HDDNo,os) values ('"+webkey+"','"+compid+"','"+HddSrno+"','"+os+"')" ;
					ESql = SqlExc01(con,Sql);
					
					Sql = "SELECT LAST_INSERT_ID()";
					String LAST_INSERT_ID =Con01.Get_Data(con, Sql);
				
					obj.put("status", "ok");
					obj.put("code", 200);
					obj.put("message", "Cli_pc_id");
					obj.put("Data", LAST_INSERT_ID);
				
			    out.write(obj.toString());
				obj = null;	
					}
				else 
				{	
					obj.put("status", "ok");
					obj.put("code", 200);
					obj.put("message", "Cli_pc_id");
					obj.put("Data", dataexsist);
				
			    out.write(obj.toString());
				obj = null;	
				}
			}	
			else {
				obj.put("status", "error");
				obj.put("code", 404);
				obj.put("message", "Cheksum Faild");
				obj.put("Data", x);
				obj.put("crc", API_New.Crc32_(x));
			
		    out.write(obj.toString());
			obj = null;	
			}
		}
		
		
		
		if (Api.equals("Web_crc"))
		{  // System.out.println("Web_crc  Start "+webkey);
		   
			
			x = "Web-crc"+webkey+"ketan";
			
			if (API_New.checkdigit(key, x) == true)
			{Sql = "UPDATE admin_db.Client_Key SET SpicialStr10 = '"+webkey+"',dt = now() WHERE Srno= '"+session.getAttribute("Srno")+"'";	
			  ESql = SqlExc01(con,Sql);
			  
			  //out.write("ok Done");
			  
			  
			    obj.put("status", "ok");
				obj.put("code", 200);
				obj.put("message", "Web_crc");
				obj.put("Data", "ok Done");
			
  
			}
			else
			{
				  
						obj.put("status", "error");
						obj.put("code", 404);
						obj.put("message", "Cheksum Faild");
						obj.put("Data", x);
					
				    out.write(obj.toString());
					obj = null;
			}
     	}
		
		if (Api.equals("wk"))
		{   x = "wk"+webkey+compnm+softyp+"ketan";
			if (!webkey.equals("")) 
		         // out.println(x);	
			      //  out.println(API_New.Crc32_(x));
			{	if (API_New.checkdigit(key, x) == true) 
				{	if (webkey.equals("0")) 
					{
					 Sql = "SELECT skey FROM admin_db.Client_Key WHERE  AssignYN = 9 AND compNm  in ('"+compnm+"') LIMIT 1";
					 webkey  = API_New.Get_Data(con, Sql);
					 if  (webkey.equals("error")) {
						// System.out.println("error01");
					 Sql = "SELECT skey FROM admin_db.Client_Key WHERE typ = 0 AND AssignYN = 0 AND compNm  in ('-','') LIMIT 1";
					 webkey  = Con01.Get_Data(con, Sql);
					 }
					  Sql = "UPDATE admin_db.Client_Key SET AssignYN = 9,compNm='"+compnm+"' WHERE skey ='"+webkey+"'";	
					  ESql = SqlExc01(con,Sql);
					//  System.out.println(Sql);
					//  System.out.println("New Webkey="+webkey);	
					  
					
							obj.put("status", "OK");
							obj.put("code", 202);
							obj.put("message", "New Webkey");
							obj.put("Data", webkey);
						
					    out.write(obj.toString());
						obj = null;
					}
					else if (webkey.length()==14 && softyp.length()>=3)
					{
						Sql = "UPDATE admin_db.Client_Key SET typ ='"+softyp+"', AssignYN = 1,compnm='"+compnm+"' WHERE skey ='"+webkey+"'";	
						//System.out.println(Sql);
						ESql = SqlExc01(con,Sql);
						
						Sql = "select srno from admin_db.Client_Key WHERE skey ='"+webkey+"' ";
						
						String Srno =(API_New.Get_Data(con,Sql));
						if (isNumeric(Srno)) {
							
								obj.put("status", "OK");
								obj.put("code", 202);
								obj.put("message", "Serial No");
								obj.put("Data", Srno);
							
						    out.write(obj.toString());
							obj = null;
							session.setAttribute("Srno", Srno);
							//System.out.println(Srno);
							//System.out.println(session.getAttribute("Srno"));
							 String myPass=(String)session.getAttribute("Srno");
							// System.out.println(myPass);
						}
						else {
							  Sql = "insert into admin_db.Client_Key (skey,typ, AssignYN,compnm ) values "
							  		+ "( '"+webkey+"','"+softyp+"',1,'AutoEntry')";	
						      ESql = SqlExc01(con,Sql);
						      
						      
									obj.put("status", "OK");
									obj.put("code", 202);
									obj.put("message", "New Entry Added");
							    
							    out.write(obj.toString());
								obj = null;
						}
					
					 }
					else
					{
							obj.put("status", "Error");
							obj.put("code", 404);
							obj.put("message", "Invalid softyp or Webkey");
							
						
					    out.write(obj.toString());
						obj = null;
						out.println("");
					}	
				}
				else
				{
					
					
						obj.put("status", "Error");
						obj.put("code", 404);
						obj.put("message", "Checksum");
						obj.put("Data",x+" "+ API_New.Crc32_(x));
						
					
				    out.write(obj.toString());
					obj = null;
				}
			}
		}
		
    }catch (Exception e) {
		e.printStackTrace();
	}
    

	}/*Last*/
    
    
    
    
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
    
    
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
