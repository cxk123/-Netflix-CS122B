<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*, app_beans.*, session.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FabFlix Shopping Cart</title>

<link href="Style/session_cart.css" rel="stylesheet" />

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

		<table width="100%">
			<tr>
				<td><h3 style="float: left;">Movie</h3></td>
				<td><h3 style="float: left;">Quantity</h3></td>
			</tr>
			<c:forEach items="${session_cart.cartItems}" var="cartItem">
				<tr>
					<td>${cartItem.movie.title}</td>
					<td>
						<form method="POST" action="CartManager">
							<input type="text" name="quantity" value="${cartItem.quantity}" style="width: 40px;"/>
							<input type="hidden" name="movieid" value="${cartItem.movie.id}"/>
							<button name="request" value="update_item_quantity" type="submit" class="button_table">Update Quantity</button>
							<button name="request" value="remove_item" type="submit" class="button_table">Remove</button>
						</form>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td><a href="FabFlixCheckout.jsp"><button class="button_submit">Checkout</button></a></td>
				<td>
					<form method="POST" action="CartManager">
						<input type="hidden" name="movieid" value="${cartItem.movie.id}"/>
						<button name="request" value="remove_all_items" type="submit">Clear Cart</button>
					</form>
				</td>
			</tr>
		</table>

</div>

</body>
</html>