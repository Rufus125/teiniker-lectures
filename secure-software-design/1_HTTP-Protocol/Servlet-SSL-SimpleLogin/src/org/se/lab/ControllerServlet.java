package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.internal.logging.Logger;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	Logger logger = Logger.getLogger(ControllerServlet.class);
	
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usergroup = request.getParameter("usergroup");
        String action = request.getParameter("action");
        
        logger.info("request: " + action + "," + username + "," + password + "," + usergroup);
        
        String html = null;
        if(action != null && action.equals("Login"))
        {
        	html = handleLogin(username, password, usergroup);
        }
        else
        {
        	html = ""; // TODO: generate error page
        }
               
        
        // Generate response
        response.setContentType("text/html");
        response.setBufferSize(1024);
        PrintWriter out = response.getWriter();
        out.println(html.toString());
        out.close();
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	
	/*
	 * Action handlers
	 */
	
	protected String handleLogin(String username, String password, String usergroup)
	{
		LoginService service = new LoginServiceImpl();
		LoginViewHelper helper = new LoginViewHelper();
		
		if(service.login(username, password, usergroup))
		{
			return helper.generateLoginSuccess(username, password, usergroup);
		}
		else
		{
			return helper.generateLoginRejected(username, password, usergroup);
		}
	}
}
