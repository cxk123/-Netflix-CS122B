<%@ page import="data_beans.*" %>

<%
	String welcomeMessage = "Please Sign In";
	if ((Customer) request.getSession().getAttribute("customer_loggedin") != null)
	{
		Customer customer = (Customer) request.getSession().getAttribute("customer_loggedin");
		welcomeMessage = "Welcome " + customer.getFirst_name() + " " + customer.getLast_name();
	}
%>

<link href="Style/master.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="javascript/search_autocomplete.js"></script>

<body>
	<div id="account_info_tab">
		<p id="welcome_message_text"><%=welcomeMessage%><p>
		<a href="FabFlixSessionCart.jsp">
			<img src="Images/shoppng_cart.png" style="width:55%; position: relative; right: 10px;" />
		</a>
		<ul>
  			<li><a href="FabFlixMain"><button type="button" class="buttons_navbar">Home</button></a></li>
  			<li><a href="Logout"><button type="button" class="buttons_navbar">Logout</button></a></li>
		</ul>
		<br/>
	</div>
</body>

