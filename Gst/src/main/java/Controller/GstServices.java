package Controller;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.zip.CRC32;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.ConnectionUtil;



/**
 * Servlet implementation class GstServices
 */
@WebServlet("/GstServices")
public class GstServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * 
     * @see HttpServlet#HttpServlet()
     */
    public GstServices() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	
		String Result01 ="";
		int DtDiff = 0 ;
		
		
		
		String Clint=request.getParameter("cl");
		String id=request.getParameter("id");
		String Auth=request.getParameter("Auth");
		String Gst=request.getParameter("gst");
		String Reqnm=request.getParameter("Reqnm");
	
		

		
		String ip = request.getHeader("x-forwarded-for");      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    	    ip = request.getHeader("Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    	    ip = request.getHeader("WL-Proxy-Client-IP");      
    	}      
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
    	    ip = request.getRemoteAddr();      
    	}  

    	String os = "";
    	String browser = "";


		String Rslt=null;
		int credit=0;
		   Connection con=ConnectionUtil.getConnection();
		try {
			PrintWriter pw=response.getWriter();
		 
			String Aut=Clint+id+Gst+"SharmaG";
		 
	    CRC32 crc = new CRC32();
        crc.update(Aut.getBytes());
       
        long checksumValue = crc.getValue();
     
        String enc = Long.toHexString(crc.getValue());
     		
		if(enc.equals(Auth)){
			
			//System.out.println(" rr2 CALL Sp_Chek_1('"+Clint+"','"+Gst+"','"+ip+"','"+os+"','"+browser+"');");
				 
			 CallableStatement  stmt=con.prepareCall("CALL Sp_Chek_1('"+Clint+"','"+Gst+"','"+ip+"','"+Reqnm+"','"+browser+"');");
			 

			 ResultSet rss=stmt.executeQuery();
			 if(rss.next()) {
				do {
				String msg=rss.getString("msg");
				if(msg.equals("ok")) {
					credit=rss.getInt("crd");
					
					PreparedStatement ps=con.prepareStatement("Select *,DATEDIFF(NOW(),`datetime`) AS dif from Trn_Api where Gst_No='"+Gst+"'   order by `DateTime` desc  limit 1 ");
					DtDiff = 20;
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {
												
						    DtDiff = rs.getInt("dif");
							Rslt=rs.getString("Responce");
							Result01 = Rslt;
						//	System.out.println(" rr3 From Trn_api 002");
					}
					
					
					if (DtDiff>19) {
						

					//	System.out.println(new Date().toString());
					
					//	System.out.println(" rr4 Try from Gst Server  003"); 
						StringBuilder result = new StringBuilder();
					    URL url = new URL("https://gstapi.charteredinfo.com/commonapi/v1.1/search?aspid=1611881118&password=Krishna%40123&action=TP&Gstin="+Gst);
				    //  System.out.println("this is the maibn ur"+url);
					    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				        connection.setRequestMethod("GET");
				        connection.connect();
				        
				    //	System.out.println(new Date().toString());
				        
				        int code = connection.getResponseCode();
				        if (code==400)
				        {
				        	//pw.print("invalid gstin  ");
				        	//pw.print(connection.getResponseMessage());
				   				        	
				        	InputStream errorstream = connection.getErrorStream();
				        	String response1 = "";
				        	String line1;
				        	BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
				        	while ((line1 = br.readLine()) != null) {
				        	    response1 =response1+ line1;
				        	}
				        	pw.print( response1);
				        }
				        
				    	//System.out.println(new Date().toString());
				        
		                InputStream in = new BufferedInputStream(connection.getInputStream());
		                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				        
				     //   System.out.println(" rr5 Response code of the object is "+code);
				        				    
				        String   tmpString=""; 
				        String line;
		                while ((line = reader.readLine()) != null) {
		                    result.append(line);
		                    Rslt= result.toString();
		                      tmpString = Rslt.replaceAll( "'", "-");
		                     }
		                in.close();
		                
		                Statement st=con.createStatement();
					    String Sqll="insert into Trn_Api (C_cd,User_Cd,Gst_No,Responce)values('0','0','"+Gst+"','"+tmpString+"')";
					   // System.out.println("rr6"+Sqll);
					    int s=st.executeUpdate(Sqll);
		                
					   	}	
					    
					
					    if (Rslt.length()<100) {
					    	Rslt = 	Result01;
					    	
					    }

				       	Rslt+=",LeftCredit:"+credit;	
							
	                      }else {
	                      Rslt= msg;	
	                     // System.out.println("rr7"+Rslt);
	                      }
	                      }while(rss.next());
	}
	 
			 else {
		
			
	Rslt= "invalid Clint";
	response.getWriter().print(Rslt);
	}
	}
			
	else {
    //CallableStatement ccbb=con.prepareCall("CALL Un('Checksum fail','"+ip+"','"+os+"','"+browser+"','"+Clint+"','"+browserDetails+"');");		
	//ResultSet rssss =ccbb.executeQuery();
	//ccbb.close();
	Rslt= "Checksum fail..";
	//response.getWriter().print(Rslt);
	}
	response.getWriter().print(Rslt);
	}catch (Exception e) {
	e.printStackTrace();
	//System.out.println("rr8"+e.getMessage());
	
	}
	try {
	con.close();
	} catch (SQLException e) {
	//System.out.println("sql :"+e.getErrorCode());
	e.printStackTrace();
	}
	}
	}
