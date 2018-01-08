
package servlets.login_logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import session.SessionCart;
import data_beans.Customer;

@WebServlet("/Logout")
public class Logout extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Logout() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		Customer customerLoggedIn = (Customer) session.getAttribute("customer_loggedin");
		
		if (customerLoggedIn != null)
		{
			SessionCart cart = (SessionCart) session.getAttribute("session_cart");
			cart.removeAllItemsFromCart();
			
			session.setAttribute("customer_loggedin", null);
			session.setAttribute("connection", null);
			session.setAttribute("session_cart", null);
		}
		response.sendRedirect("Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
