package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

   
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name=request.getParameter("user");
		String pass=request.getParameter("pass");
		String url=null;
		String msg=null;
		
		
		if(name!=null && pass!=null) {
			
			if(name.equals("Krishna") && pass.equals("Kim@123"))
			{
				url="welcome.jsp";
			}else {
				url="index.jsp";
				msg="Invalid User name Password";
			}
			
			
		}else {
			url="index.jsp";
			msg="Invalid User name Password";
		}
		request.setAttribute("msg", msg);
	request.getRequestDispatcher(url).forward(request, response);	
}
}
