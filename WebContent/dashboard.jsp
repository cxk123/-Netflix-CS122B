<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DASHBOARD</title>
</head>
<body style="background-image:url(Images/web_login_bg.jpg); background-repeat:no-repeat; background-position:center; background-size:cover;">	
	
	<center>
    <h3 class="success">${success}</h3>
  	</center>
  	<center>
	<table style="margin:auto;width:50%;">
  <tr>
    <td style="width:100%;">
      <form action="AddStar" method="get">
        <fieldset class="field_entry">
          <legend>Add Star</legend>
          <table>
            <tr>
              <th>Star First Name</th>
              <td><input type="text" name="first_name"/></td>
            </tr>
            <tr>
              <th>Star Last Name (or full name)</th>
              <td><input type="text" name="last_name" required /></td>
            </tr>
          </table>
          </center>
          <CENTER>
          <input type="submit" value="Add" class="submit"/>
          </CENTER>
        </fieldset>
      </form>
    </td>
  </tr>
  <tr>
    <td style="width:100%;">
	<FORM ACTION="AddMovie"METHOD="GET">
	  <fieldset class="field_entry">
          <legend>Add Movie</legend>
          <table>
           <tr>
              <th>Star name:</th>
              <td><INPUT TYPE="TEXT" NAME="star" required/></td>
           </tr>
            <tr>
              <th>Star Date of birth:</th>
              <td><INPUT TYPE="TEXT" NAME="dob" required /></td>
           </tr>
           <tr>
              <th>Star Photo_url:</th>
              <td><INPUT TYPE="TEXT" NAME="photo" required /></td>
           </tr>
  		    <tr>
              <th>Movie name:</th>
              <td><INPUT TYPE="TEXT" NAME="name" required /></td>
           </tr>
            <tr>
              <th>Movie year:</th>
              <td><INPUT TYPE="TEXT" value="2017" required min ="1" max ="2017" NAME="year"></td>
           </tr>
            <tr>
              <th>Movie director:</th>
              <td><INPUT TYPE="TEXT" NAME="director" required /></td>
           </tr>
            <tr>
              <th>Banner_url:</th>
              <td><INPUT TYPE="TEXT" NAME="banner"></td>
           </tr>
            <tr>
              <th>trailer_url:</th>
              <td><INPUT TYPE="TEXT" NAME="trailer"></td>
           </tr>
           <tr>
              <th>Genre:</th>
              <td><INPUT TYPE="TEXT" NAME="genre" required/></td>
           </tr>
  	  </table>
  	<CENTER>
    	<INPUT TYPE="SUBMIT" VALUE="Submit Order">
  	</CENTER>
	</FORM>
    </td>
  </tr>
    <tr>
    <td style="width:100%;">
  
      <form action="ProvideMeta" method="get">
      
        <fieldset class="field_entry">
          <legend>Provide meta data</legend>
          <CENTER>
           <h7> Please input the table name of the metadata you want to search:</h7>
          </CENTER>
          <CENTER>
          <table>
          <FORM ACTION="ProvideMeta" METHOD="GET">
          <tr>
         	<th> Table Name:</th>
         	<td> <select name = "type">
         			<option value = "movies">movies</option>
         			<option value = "stars">stars</option>
         			<option value = "genres">genres</option>
         			<option value = "customers">customers</option>
         			<option value = "sales">sales</option>
         			<option value = "creditcards">creditcards</option>
         			<option value = "employees">employees</option>
         		</select></td>	
          </tr> 
          </table>
          </CENTER>
          <CENTER>
          <input type="submit" value="click here" class="submit"/>
          </CENTER>
        </fieldset>
      </form>
    </td>
  </tr>
</table>
</body>
</html>