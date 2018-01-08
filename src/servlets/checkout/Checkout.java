package servlets.checkout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import queries.SearchQuery;
import session.SessionCart;
import data_beans.Customer;

@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cc_id = request.getParameter("cc_id");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String expiration = request.getParameter("expiration");
		
		try 
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			HttpSession session = request.getSession(false);
			
			String sql = "SELECT COUNT(*) FROM creditcards WHERE id=? AND first_name=? AND last_name=? AND expiration=?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cc_id);
			preparedStatement.setString(2, first_name);
			preparedStatement.setString(3, last_name);
			preparedStatement.setDate(4, Date.valueOf(expiration));

			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			int count = rs.getInt(1);
			
			if (count == 1) 
			{
				Customer customer = (Customer) session.getAttribute("customer_loggedin");
				SessionCart cart = (SessionCart) session.getAttribute("session_cart");
				
				SearchQuery.insertSales(cart, customer, connection);
				
				cart.removeAllItemsFromCart();
				session.setAttribute("session_cart",cart);
				
				request.setAttribute("checkout", true);
				request.getRequestDispatcher("CheckoutConfirmation.jsp").forward(request, response);
			}
			else 
			{
				request.getRequestDispatcher("FabFlixCheckout.jsp").forward(request, response);
			}
			
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
