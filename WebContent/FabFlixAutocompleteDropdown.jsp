<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/tooltip.js"></script>

<span>
	<span>Movies:</span>
	<c:forEach items="${movies}" var="movie">
		<div class="movieCardAnchor" id="${movie.id}">
		<a href="FabFlixMovieDetails?movieid=${movie.id}">
        	<img src="${movie.banner_url}" width="50" height="75" alt="No Movie Image"/>
        	<span>${movie.title}</span>
      	</a>
      	</div>
		<span class="seperator"></span>
	</c:forEach>
</span> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>

<style>


  #search_results{
	border-width:1px; border-color:#919191; border-style:solid;
	font-size: 11px; 
	font-family:"Lucida Grande","Lucida Sans Unicode",Arial,Verdana,sans-serif;
	background-color: #e4e4e4;
  }

  #movie_title{
	//color: black;
	 
	font-weight:bold; 
	 
	color:#191919;
  }

  .movie_attributes{
	color:#555555;
	//margin: 3px;
  }


  a {
	text-decoration: none;
  }

  #bar_category {
    background-color: #b7b7b7;
    
    
    padding: 5px;
    font-weight: bold;
    font-size:11px; display:block; color:#ffffff;
  }
  
  #margins_div{
    padding: 8px;
  }


  
</style>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/tooltip.js"></script>

<div id="search_results">
	<div id="bar_category">Movies</div>
	<div id="margins_div">
	
	
	<c:forEach items="${movies}" var="movie">
	

	
		<div class="movieCardAnchor" id="${movie.id}">

	<table>
	<tr>
	<td>
	<img src="${movie.banner_url}" style="width: 55px; height: 55px; margin-bottom: 5px;" alt="No Movie Image" />
	</td>
	<td>
	<a href="FabFlixMovieDetails?movieid=${movie.id}">
        	
        	<span id="movie_title">${movie.title}</span> <br />
        	<span class="movie_attributes">${movie.year}</span> <br />
        	<span class="movie_attributes">Director: ${movie.director}</span>
      	</a>
	</td>
	</tr>
	</table>
		
      	</div>
		<span class="seperator"></span><span class="separator"></span>
	</c:forEach>
	
	</div>
</div>