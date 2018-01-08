package app_beans;

import data_beans.Movie;

public class CartItem {

	private Movie movie;
	private int quantity;
	
	public CartItem(Movie movie, int quantity) 
	{
		super();
		
		this.movie = movie;
		this.quantity = quantity;
	}

	public Movie getMovie() 
	{
		return movie;
	}
	
	public void setMovie(Movie movie) 
	{
		this.movie = movie;
	}
	
	public int getQuantity() 
	{
		return quantity;
	}
	
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	public void addQuantity(int quantity)
	{
		this.quantity += quantity;
	}
	
	public Integer getMovieId()
	{
		return movie.getId();
	}
}
