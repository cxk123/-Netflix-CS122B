package xml1;

import java.util.List;

public class Movie {
	
	private int id;
	
	private String title;

	private int year;
	
	private String director;
	
	private String banner_url;

	private String trailer_url;
	
	private List cats;
	
	public Movie(){
		
	}
	
	public Movie(int id, String title, int year,String director, String banner_url,String trailer_url) {
		
		this.setId(id);
		this.setTitle(title);
		this.setYear(year);
		this.setDirector(director);
		this.setBanner_url(banner_url);
		this.setTrailer_url(trailer_url);
		
	}
	
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("Id:" + getId());
		sb.append(", ");
		sb.append("Title:" + getTitle());
		sb.append(", ");
		sb.append("Year:" + getYear());
		sb.append(", ");
		sb.append("Director:" + getDirector());
		sb.append(", ");
		sb.append("Banner:" + getBanner_url());
		sb.append(", ");
		sb.append("Trailer:" + getTrailer_url());
		sb.append(", ");
		sb.append("Categories:" + getCats());
		sb.append(".");
		
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getBanner_url() {
		return banner_url;
	}

	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}

	public String getTrailer_url() {
		return trailer_url;
	}

	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}

	public List getCats() {
		return cats;
	}

	public void setCats(List cats) {
		this.cats = cats;
	}
}
