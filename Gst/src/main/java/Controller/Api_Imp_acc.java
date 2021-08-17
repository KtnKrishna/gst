package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.zip.CRC32;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Api_Imp_acc
 */
@WebServlet("/Api_Imp_acc")
public class Api_Imp_acc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Api_Imp_acc() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		JSONObject obj=new JSONObject();
		
	
		HttpSession ses = request.getSession();
		ses.setMaxInactiveInterval(300);    // session timeout in seconds
		
		
		String  dbKey =""; 
		String  AuthKey ="";
		String  module ="";
		String	db = "";
		String	tbl = "";
		String	fl = "";
		String	fv = "";
		String	api = "";
		String	Qry = "";
		String	Qtype = "";
		String	flist = "";
		String	vlist = "";
		String	cdlist = "";
		String	yid = "";String	cid = "";
		
			
		    AuthKey = request.getParameter("AuthKey");
		    //if (request.getParameter("dbkey") != null) {db = request.getParameter("dbkey");  }
		    if (request.getParameter("module") != null) {module = request.getParameter("module");  }
		    if (request.getParameter("db") != null)     {db = request.getParameter("db");      }
		    if (request.getParameter("tbl") != null)    {tbl = request.getParameter("tbl");    }
		    if (request.getParameter("fl") != null)     {fl = request.getParameter("fl");      }
		    if (request.getParameter("fv") != null)     {fv = request.getParameter("fv");      }
		    if (request.getParameter("api") != null)    {api = request.getParameter("api");    }
		    if (request.getParameter("Qry") != null)    {Qry = request.getParameter("Qry");    }
		    if (request.getParameter("Qtype") != null)  {Qtype = request.getParameter("Qtype");}
		    if (request.getParameter("flist") != null)  {flist = request.getParameter("flist");}
		    if (request.getParameter("vlist") != null)  {vlist = request.getParameter("vlist");}
		    if (request.getParameter("cdlist") != null)  {cdlist = request.getParameter("cdlist");}
		    
		    if (request.getParameter("yid") != null) {yid = request.getParameter("yid");      }
		    if (request.getParameter("cid") != null) {cid = request.getParameter("cid");      }
		    
		  
		    System.out.println("AuthKey="+api+db+tbl+fl+fv+Qry+Qtype+flist+vlist+cdlist+module+yid);
		    
		    ses.setAttribute("cid",cid );
		    ses.setAttribute("yid",yid );
		
		    
		    System.out.println("----------");
		    System.out.println(ses.getId());
		    System.out.println(ses.getAttribute("cid"));
		    
		PrintWriter out = response.getWriter();

		
		if ( checkdigit(AuthKey,api+db+tbl+fl+fv+Qry+Qtype+flist+vlist+cdlist+module+yid+cid)==true ) 
			
		{   
		
		  JSONObject res;
			try 
			{
				if  (module.equals("group")) 
				{res = Imp_Acc_I_u_d.Import_Group(api,db,Qtype,flist,vlist,Qry,cdlist);
					out.write(res.toString());
				}
				
				if  (module.equals("ledger")) 
				{res = Imp_Acc_I_u_d.Import_Leger(api,db,Qtype,flist,vlist,Qry,cdlist,yid,cid);
					out.write(res.toString());
				}
				if  (module.equals("item")) 
				{res = Imp_Acc_I_u_d.Import_item(api,db,Qtype,flist,vlist,Qry,cdlist,yid,cid);
					out.write(res.toString());
				}
				
								
				if (api.equals("1")) 
				{
				res = Imp_Acc.DataImport(api,db,tbl,fl,fv,Qry,Qtype,flist,vlist);
				out.write(res.toString());
				}
				
			} catch (JSONException | SQLException e) 
			{
				e.printStackTrace();
			}
		      		}
		else 
		{
			 System.out.println("AuthKey="+api+db+tbl+fl+fv+Qry+Qtype+flist+vlist+cdlist+module+yid+cid);
		       System.out.println(AuthKey);
		try {
				
				
				obj.put("Err01","Crc Error");
				obj.put("status", "error");
				obj.put("code", 200);
				obj.put("message", "Ivalid Autho Key");
				obj.put("Data", Crc32_(api+db+tbl+fl+fv+Qry+Qtype+flist+vlist+cdlist+module+yid+cid));
				obj.put("Data1",      (api+db+tbl+fl+fv+Qry+Qtype+flist+vlist+cdlist+module+yid+cid));
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.write(obj.toString());
			// System.out.println(Crc32_(api+db+Qry));
			// out.write(Crc32_(api+db+Qry));
		}
		 
			 
}
	private static boolean checkdigit(String key, String crcstring) {
		
		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		String enc = Long.toHexString(crc.getValue());

	   if (enc.equals(key)) {
			return true;
		}
		return false;
	}


	private static String Crc32_(String crcstring) {

		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		return Long.toHexString(crc.getValue());

	}
}

