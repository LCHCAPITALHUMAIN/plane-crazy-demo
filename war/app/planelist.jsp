<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.model.UserRegistry" %>
<%@ page import="com.google.model.Airplane" %>
<%@ page import="com.google.model.PMF" %>



<%! String faveitem = ""; %>

<%
   PersistenceManager pm = PMF.get().getPersistenceManager();

    faveitem = (String)session.getAttribute("faveitem");
      
    String query = ""; 
    query = "select from " + Airplane.class.getName();
    List<Airplane> airplanes = (List<Airplane>) pm.newQuery(query).execute();
    String evenodd = "";
    
%>

             <%@ include file="header-sidebar.jsp" %>

              <!-- MAIN CONTENT GOES HERE -->
              
<h3>Welcome to Plane Crazy!  Please click on a plane to get more information.</h3>

<table class="planes">
<%
   int x=0;   
   for (Airplane a : airplanes) {
     x++;
     if (x % 2 == 0) {
       evenodd = "even"; 
     } else {
       evenodd = "odd";
}
     
%>
  <tr class="<%= evenodd %>">
    <td> 
      
      <style>
.mycontainerdiv { float: right; position: relative; } 
.myfaveimage { position: absolute; top: 0; left: 0; } 
</style>
<div class="mycontainerdiv">
<a href="detail.jsp?planeid=<%= a.getCatalogId() %>">
<img src="<%= a.getThumbnailUrl() %>" class="planeimage" />
</a>
<%
  if (a.getCatalogId().equals(faveitem)) {
 %> 
<img class="myfaveimage" border="0" src="../images/goldseal-favorite.png" alt="">
<%
  }
%>
<div>
      
      
    </td>
    <td>
      Airplane Model: <a href="detail.jsp?planeid=<%= a.getCatalogId() %>"><%= a.getName() %></a><br/> 
      Airplane Manufacturer: <%= a.getManufacturer() %><br/>
      <br/>
      Description: <%= a.getDescription() %><br/>
    </td>
  </tr>     

<%
   }
   pm.close();
%>

  
</table>

<%@ include file="footer.jsp" %>
