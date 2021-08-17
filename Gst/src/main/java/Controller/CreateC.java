package Controller;

import java.io.IOException;
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


import Util.ConnectionUtil;

/**
 * Servlet implementation class CreateC
 */
@WebServlet("/CreateC")
public class CreateC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateC() {
        super();
        // TODO Auto-generated constructor stub
    }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ss=0;
		String  name=request.getParameter("name");
		String  email =request.getParameter("email");
		String  mob =request.getParameter("mob");
		String  credit =request.getParameter("credit");
		String Gst=request.getParameter("gst");
		String   btn=request.getParameter("btn");
		String msg = "Null";
		Connection con=ConnectionUtil.getConnection();
		
		try {
			Statement st=con.createStatement();
			
			if(btn.equals("Update"))
			{int no=Integer.parseInt(request.getParameter("No"));
				 ss=st.executeUpdate("update  user set NAME='"+name+"', Email='"+email+"', Phone='"+mob+"', GST='"+Gst+"', Credit='"+credit+"' where Auto_no='"+no+"' ");
					msg="data Updated..";
			}	else if(btn.equals("Save")){
				PreparedStatement ps=con.prepareStatement("select * from user where  Gst='"+Gst+"'");
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					msg="Data Already Exist plz Change";
				}else {
					 ss=st.executeUpdate("insert into  user (NAME, Email, Phone, GST, Credit, Counter) values('"+name+"','"+email+"','"+mob+"','"+Gst+"','"+credit+"',0)");
					 msg="Data Insert Sucessfully";
				}
				
		 	}else if(btn.equals("Delete")) {
				int no=Integer.parseInt(request.getParameter("No"));
				 ss=st.executeUpdate("delete  from  user where Auto_no='"+no+"'");
				 msg="Data Delete Sucessfully";
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("Clint.jsp?msg="+msg);
	}

}
