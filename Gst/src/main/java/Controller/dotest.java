package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class dotest
 */
@WebServlet("/dotest")


public class dotest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "images\\bills";
	String  bllno;
	public dotest() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		
		
		JSONObject obj = new JSONObject();
		
		PrintWriter pw=response.getWriter();
		String a=request.getParameter("aa");
		String b=request.getParameter("bb");
		
		int aa=0;
		aa=a.length()+b.length();
		
		try {
			obj.put("a", a);
		    obj.put("b", b);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
			
		
		
    	pw.print(obj.toString());
		
	}

}
