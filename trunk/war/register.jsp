<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.model.UserRegistry" %>
<%@ page import="com.google.model.PMF" %>

<%! String regError = null; %>
<%! String errorMsg = null; %>

<%
   regError = request.getParameter("error");
    if (regError != null) {
      if (regError.equals("un")){
        errorMsg = "Registration error: Username already exists! Please select another.";
      } else {
        if (regError.equals("pw")){
          errorMsg = "Registration error: Passwords do not match!";
        }
      }
    } 

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<style type="text/css">
		  @import url('css/planecrazy.css');
		</style>
  </head>
  <body>
    <div id="wrapleft">
      <div id="wrapright">
        
        <div id="header">
        </div>
        
        <div id="content">
        
        
          <div id="loginbar" class="shadow">
            <div class="contentbox">

              <!-- MAIN CONTENT GOES HERE -->
              
<h1>Register for an account</h1>
<%
  if (errorMsg != null){
    out.println("<p class=\"error\">" + errorMsg + "</p>");
  }
%>

<table border="1" class="planes" width="100%">
  <tr >
    <td align="center"> 

		<br/>
		
		    <form action="/register" method="post">
		<table border="0" border="1" >
		   <tr>
		      <td align="right">User Name: </td> <td> <input type="text" id="onlineuser_form" name="username" size="30"></td>
		    </tr>  
		   <tr>
		      <td align="right">Password: </td> <td> <input type="password" id="password_form" name="pwd" size="30"></td>
		    </tr>  
		   <tr>
		      <td align="right">Confirm Password: </td> <td> <input type="password" id="pwd2_form" name="pwd2" size="30"></td>
		    </tr>  
		   <tr>
		      <td align="right">Email address: <br/></td> <td> <input type="text" id="email_form" name="email" size="30" ></td>
		    </tr>  

		   <tr>
		      <td align="right">Profile Photo (enter Web address): <br/></td> <td> <input type="text" id="thumbnailurl_form" name="thumbnailUrl" size="30" value="http://"></td>
		    </tr>  

		     <tr>
		       <td>&nbsp;</td><td><input type="submit" value="Register" /></td>
		     </tr> 

		    </form>		
    </td>
  </tr>     
</table>
<h5>Don't want to create an account? You can still sign in using a Google Friend Connect account! Just <a href="login.jsp">go back to the login page</a> and click Sign in with Google Friend Connect.</h5>

              <!-- END MAIN CONTENT -->
            </div>
          </div>
        
          <div id="footer">
            Header image <a href="http://photoxpress.com/Content/aviation-airplane-aeronautics/1349864">&copy; PhotoXpress</a>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>