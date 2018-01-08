package session;

import java.util.ArrayList;
import java.util.HashMap;

import app_beans.CartItem;
import data_beans.Movie;

public class SessionCart {

	private HashMap<Integer, CartItem> cartItems;
	
	public SessionCart() 
	{
		cartItems = new HashMap<Integer, CartItem>();
	}
	
	public CartItem addItemToCart(Movie movie, int quantity)
	{
		if (cartItems.containsKey(movie.getId()))
		{
			CartItem existingItem = cartItems.get(movie.getId());
			existingItem.addQuantity(quantity);
			return existingItem;
		}
		else
		{
			CartItem cartItem = new CartItem(movie, quantity);
			cartItems.put(movie.getId(), cartItem);
			return cartItem;
		}
	}
	
	public void updateQuantityOfItemInCart(CartItem item, int quantity)
	{
		if (cartItems.containsKey(item.getMovieId()))
		{
			CartItem existingItem = cartItems.get(item.getMovieId());
			existingItem.setQuantity(quantity);
		}
	}
	
	public void updateQuantityOfItemInCart(Movie movie, int quantity)
	{
		if (cartItems.containsKey(movie.getId()))
		{
			CartItem existingItem = cartItems.get(movie.getId());
			existingItem.setQuantity(quantity);
		}
	}
	
	public void removeItemFromCart(CartItem item)
	{
		if (cartItems.containsKey(item.getMovieId()))
		{
			cartItems.remove(item.getMovieId());
		}
	}
	
	public void removeItemFromCart(Movie movie)
	{
		if (cartItems.containsKey(movie.getId()))
		{
			cartItems.remove(movie.getId());
		}
	}
	
	public void removeAllItemsFromCart()
	{
		cartItems.clear();
	}
	
	public boolean containsItemForMovie(Movie movie)
	{
		return (cartItems.containsKey(movie.getId()));
	}
	
	public ArrayList<CartItem> getCartItems()
	{
		return new ArrayList<CartItem>(cartItems.values());
	}
}
