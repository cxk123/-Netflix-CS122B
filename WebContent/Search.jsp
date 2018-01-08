<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>

<link href="Style/search.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/search_autocomplete.js"></script>


</head>

<body>

 



<%@ include file="NavBar.jsp"%>

<div class="ASearch">
	<div class="message">Search Options</div>
	<div id="darkbannerwrap"></div>	
	<form name="searchform" action="FabFlixMovieList" method="GET">
		Title<br>
	<div id="Div">
		<input type="text" name = "title" id = "keyword" size="50" id="keyword" placeholder="Search..." onkeyup="getMoreContents()"
		onblur="keywordBlur()" onfocus="getMoreContents()" style="width:100%;"/>
		<div id="popDiv">
			<table id="content_table" bgcolor="#FFFAFA" border="0" cellspacing="0" 
			cellpadding="0">
			<tbody id="content_table_body">
			</tbody>
			</table>
		</div>
	</div>
		
	    <hr class="hr15" />
	    Year<br>
		<input type="text" name="year" placeholder="year" style="width:100%;" value=""/>
		<hr class="hr15" />
		Director<br>
		<input type="text" name="director" placeholder="director" style="width:100%;" value=""/>
		<hr class="hr15" />
		Star's Name<br>
		<input type="text" name="first_name" placeholder="first name" style="width:40%;" value=""/>
		<input type="text" name="last_name" placeholder="last name" style="width:40%;" value=""/>
		<hr class="hr15" />
		<input type="checkbox" name="substring_match" value="true"/>Substring Matching and Fuzzy Search<br>
		<hr class="hr15"/>
		<input type="hidden" name="from_search" value="true"/>
		<input type="submit" name="Search Submit" value="Search" class="button_submit"/>
  </form>
</div>
	<div class="copyright">© MovieBuy by Group64</div>
</body>
</html>