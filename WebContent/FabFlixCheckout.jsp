<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FabFlix Checkout</title>

<link href="Style/checkout.css" rel="stylesheet" /> 
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>

  <script type="text/JavaScript">
	function check(form){
	var a = /^(\d{4})-(\d{2})-(\d{2})$/;
	if(document.forms.Checkout.cc_id.value==""){
		alert("Enter valid creditnumber please!");
		document.forms.Checkout.cc_id.focus();
		return false;
		}
	if(document.forms.Checkout.first_name.value==""){
		alert("Enter valid firstname please!");
		document.forms.Checkout.first_name.focus();
		return false;
		}
	if(document.forms.Checkout.last_name.value==""){
		alert("Enter valid lastname please!");
		document.forms.Checkout.last_name.focus();
		return false;
		}
	if(!a.test(document.forms.Checkout.expiration.value)){
		alert("Wrong date format!");
		document.forms.Checkout.expiration.focus();
		return false;
	}
}
</script>
</head>

<body>

<%
	if ((Customer) request.getSession().getAttribute("customer_loggedin") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>

<%@ include file="NavBar.jsp"%>

<div class="login">
	<div class="message">FabFlix Checkout</div>
	<div id="darkbannerwrap"></div>	
	<form name="Checkout" action="Checkout" method="POST">
		<span id="form1">Credit Card Number<br></span>
		<input type="text" name="cc_id" size="50" id="cc_id" maxlength="20" value="" required/>
		<hr class="hr15"/>
		
		<span id="form2">Name on Card<br></span>
		<input type="text" name="first_name" id="first_name" value="" required placeholder="first name" style="width:40%;" value=""/>
		<input type="text" name="last_name" id="last_name" value="" requiredplaceholder="last name" style="width:40%;" value=""/>
		<hr class="hr15"/>
		
		<span id="form3">Expiration Date (YYYY-MM-DD)<br></span>
		<input type="text" name="expiration" size="50" id="expiration" maxlength="10" value="" required/>
		<hr class="hr20"/>
		
		<input type="submit" name="Search Submit" onclick="return check(this);" value="Go" class="button_submit"/>
	</form>
</div>
	<div class="copyright">© MovieBuy by Group64</div>
</body>
</html>