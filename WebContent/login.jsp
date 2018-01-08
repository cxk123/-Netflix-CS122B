<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src='https://www.google.com/recaptcha/api.js'></script>
  <title>FabFlix Login</title>
  <link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>
  <link href="Style/login.css" rel="stylesheet" /> 
  <script type="text/JavaScript">
	function check(form){
	if(document.forms.loginform.email.value==""){
	alert("Enter valid email please!");
	document.forms.loginform.email.focus();
	return false;
	}
	if(document.forms.loginform.password.value==""){
	alert("Enter password please!");
	document.forms.loginform.password.focus();
	return false;
	}
}
</script>


</head>
<body>
<!--  The action = is the path ending the name of the servlet -->
<%
	if (request.getSession(false) != null && request.getSession().getAttribute("customer_loggedin") != null)
	{
		response.sendRedirect("FabFlixMain");
	}
%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>FabFlix login</title>
	<link type="text/css" rel="stylesheet" href="Style/login.css"/>
</head>

<body>
<div class="login">
	<div class="message">FabFlix Login</div>
	<div id="darkbannerwrap"></div>	
	<form name="loginform" action="Login" method="POST">
		<input name="email" placeholder="email-address" required type="email"/>
		<hr class="hr15"/>
		<input name="password" placeholder="password" required="required" type="password"/>
		<hr class="hr15"/>
		<select name = "type"><option value = "1">User</option><option value = "2">Administrator</option></select>
		<hr class="hr15"/>
		<input type="submit" value="Login" onclick="return check(this);" style="width:100%;" id="login_button"/>
		<hr class="hr20"/>
		<h4>${login_invalid}</h4>
		
  </form>
</div>
	<div class="copyright">© MovieBuy by Group64</div>
</body>
</html>