package servlets.checkout;

import java.io.IOException;
import java.sql.Connection;
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
import data_beans.Movie;

@WebServlet("/CartManager")
public class CartManager extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
	
    public CartManager() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = (Connection) dataSource.getConnection();
			}
			
			HttpSession session = request.getSession(false);
			SessionCart cart = (SessionCart) session.getAttribute("session_cart");
			
			String requestType = request.getParameter("request").toLowerCase();
			String movieId = request.getParameter("movieid");
			
			int quantity = 1;
			
			if (request.getParameter("quantity") != null)
			{
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			
			Movie movie = SearchQuery.searchMovies(movieId, "", "", "", "", "", "", "", false, connection).get(0);
			
			if (requestType.equals("add_item"))
			{
				cart.addItemToCart(movie, quantity);
			}
			else if (requestType.equals("update_item_quantity"))
			{
				cart.updateQuantityOfItemInCart(movie, quantity);
			}
			else if (requestType.equals("remove_item"))
			{
				cart.removeItemFromCart(movie);
			}
			else if (requestType.equals("remove_all_items"))
			{
				cart.removeAllItemsFromCart();
			}
			
			session.setAttribute("session_cart", cart);
			response.sendRedirect("FabFlixSessionCart.jsp");
			
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
