package data_beans;

import java.sql.Date;

public class Sale {
	private int id;
	private int customer_id;
	private int movie_id;
	private Date sale_date;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the customer_id
	 */
	public int getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return the movie_id
	 */
	public int getMovie_id() {
		return movie_id;
	}
	/**
	 * @param movie_id the movie_id to set
	 */
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	/**
	 * @return the sale_date
	 */
	public Date getSale_date() {
		return sale_date;
	}
	/**
	 * @param sale_date the sale_date to set
	 */
	public void setSale_date(Date sale_date) {
		this.sale_date = sale_date;
	}
}
