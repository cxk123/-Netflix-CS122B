<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FabFlix Movie Details</title>

<link href="Style/movie_details.css" rel="stylesheet" />
<link href="Style/movie_card.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/tooltip.js"></script>

</head>
<body>

<%
	if ((Customer) request.getSession().getAttribute("customer_loggedin") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>

<%@ include file="NavBar.jsp"%>

<div id="main_content">
	<table>
  		<tr>
    		<td>
				<img src="${movie.banner_url}" width="150" height="200" alt="No Movie Image" id="movie_poster"/>
			</td>
			<td>
				<h3 class="movieCardAnchor" id="${movie.id}">Title: ${movie.title}</h3>
				<p>Year Released: ${movie.year}</p>
				<p>Director: ${movie.director}</p>
				<p>Stars: 
					<c:forEach items="${stars}" var="star">
						<a href="FabFlixStarDetails?starid=${star.id}">${star.first_name} ${star.last_name}</a>, 
					</c:forEach>
				</p>
				<p>Genres:
					<c:forEach items="${genres}" var="genre">
						${genre.name}, 
					</c:forEach>
				</p>
				<p>
					<a href="${movie.trailer_url}">Watch Trailer</a>
				</p>
    		</td>
    	</tr>
  		<tr>
    		<td>
				<form name="addMovieToCart" action="CartManager?request=add_item&quantity=1&movieid=${movie.id}" method="POST">
					<button type="submit" class="button_submit">Add Movie to Cart</button>
				</form>
    		</td>
  		</tr>
	</table>
</div>

</body>
</html>