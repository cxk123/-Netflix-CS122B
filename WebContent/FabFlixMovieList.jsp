<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>
<%@ page import="java.util.ArrayList"%>




<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FabFlix Movie List</title>

<link href="Style/movie_list.css" rel ="stylesheet" />
<link href="Style/movie_card.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/tooltip.js"></script>

</head>
<body>


<%@ include file="NavBar.jsp"%>

<div id="MovieList">
<p>
	Sort By:
	<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page}&sort=titleasc">Title <img src="Images/up_arrow.png" class="order_arrow" /></a> | 
 	<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page}&sort=titledsc">Title <img src="Images/down_arrow.png" class="order_arrow" /></a> |
	<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page}&sort=yearasc">Year <img src="Images/up_arrow.png" class="order_arrow" /></a> |
	<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page}&sort=yeardsc">Year <img src="Images/down_arrow.png" class="order_arrow" /></a> |
</p>

<table id="results">
<thead class=message>
	<tr>
		<th>Picture</th>
		<th>Title&Id</th>
		<th>Year</th>
		<th>Director</th>
		<th>Genres</th>
		<th>Stars</th>
		<th>Others</th>
		<th>Add</th>
	</tr>
</thead>


<tbody>
	<c:forEach items="${movies}" var="movie">  
	<tr>
	<td>
		<img src="${movie.banner_url}" width="150" height="200" alt="No Movie Image"/>
	</td>		
	<td>
		<h3 class="movieCardAnchor" id="${movie.id}"><a href="FabFlixMovieDetails?movieid=${movie.id}">${movie.title}</a></h3>
		<p>${movie.id}</p>
	</td>
	<td>
		<p>${movie.year}</p>
	</td>
	<td>
		<p>${movie.director}</p>
	</td>
	<td>
		<c:forEach items="${movie.genres}" var="genre">
			<p>${genre.name}</p>
		</c:forEach>
	</td>
	<td>
		<c:forEach items="${movie.stars}" var="star">
			<a href="FabFlixStarDetails?starid=${star.id}">${star.first_name} ${star.last_name}</a>, 
		</c:forEach>
	</td>
	<td>
		<p><a href="${movie.trailer_url}">Watch Trailer</a></p>
	</td>
	<td>
		<form name="addMovieToCart" action="CartManager?request=add_item&quantity=1&movieid=${movie.id}" method="POST">
			<button type="submit" class="button_submit">Add Movie to Cart</button>
		</form>
	</td>
	</tr>
	</c:forEach>
</tbody>
</table>



	

<table>
	<tr>
	<td>
	</td>
	<td>
		<c:if test="${page ne 0}">
		<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page - 1}&sort=${sort}">
			<button>Previous</button>
		</a>
		</c:if>
	</td>
	<td>
	<p>
		Results Per Page <!-- 10, 25, 50, 100  -->
		<a href="FabFlixMovieList?${queries}lim=5&page=0&sort=${sort}">5</a>
		<a href="FabFlixMovieList?${queries}lim=10&page=0&sort=${sort}">10</a>
		<a href="FabFlixMovieList?${queries}lim=25&page=0&sort=${sort}">25</a>
		<a href="FabFlixMovieList?${queries}lim=50&page=0&sort=${sort}">50</a>
		<a href="FabFlixMovieList?${queries}lim=100&page=0&sort=${sort}">100</a>
	</p>
	</td>
	<td>
		<c:if test="${page lt maxPage}">
		<a href="FabFlixMovieList?${queries}lim=${lim}&page=${page + 1}&sort=${sort}">
			<button>Next</button>
		</a>
		</c:if>
	</td>
	</tr>
</table>
</div>

</body>
</html>