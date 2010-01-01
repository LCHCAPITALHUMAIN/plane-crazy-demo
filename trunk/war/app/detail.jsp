<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.model.UserRegistry" %>
<%@ page import="com.google.model.FavoriteActivity" %>
<%@ page import="com.google.model.Airplane" %>
<%@ page import="com.google.model.PMF" %>



<%! String planeid = ""; %>
<%! String faveitem = ""; %>
<%! String userid = ""; %>


<%
   String usertype = (String)session.getAttribute("GFCUser");
   if (usertype.equals("yes")){
     userid = (String)session.getAttribute("gfcid");
   } else {
     userid = (String)session.getAttribute("userid");
   }
   
   faveitem = (String)session.getAttribute("faveitem");
    
    planeid = request.getParameter("planeid");
    if (planeid == null) {
      planeid = "abc1";
    } 
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = ""; 
    query = "select from " + Airplane.class.getName() + " where catalogId == '" + planeid + "'";
    List<Airplane> airplanes = (List<Airplane>) pm.newQuery(query).execute();
    
    Airplane thisPlane = (Airplane)airplanes.get(0); // grab first result  

%>

              <%@ include file="header-sidebar.jsp" %>

              <!-- MAIN CONTENT GOES HERE -->

<h1> <%= thisPlane.getName() %></h1>

<table  width="100%"><tr><td>

<style>
.mycontainerdiv { float: left; position: relative; width: 100%; } 
.myfaveimage { position: absolute; top: 0; left: 0; } 
</style>
<div class="mycontainerdiv">
<img src="<%= thisPlane.getImgUrl() %>" class="planeimage" />

<%
  if (thisPlane.getCatalogId().equals(faveitem)) {
 %> 
<img class="myfaveimage" border="0" src="../images/goldseal-favorite.png" alt="">
<%
  }
%>
<div>


<p>      
<%= thisPlane.getDescription() %>
</p>
<p>
 <%= thisPlane.getLongDescription().getValue() %></p>

<table  class="stats">
  <tr class="even">
    <th> 
      Manufacturer:
    </th>

    <td>
      <%= thisPlane.getManufacturer() %> 
    </td>
  </tr>
  <tr class="odd">
    <th>
&nbsp;
    </th>
    <td>
      <form action="/selectfave" method="post">
<input type="hidden" name="user" value="<%= userid %>" />
<input type="hidden" name="faveitem" value="<%= thisPlane.getCatalogId() %>" /> 
<input type="image" src="../images/selectfave-button-sm.png" value="Select" /> 


    </td>

  </tr>   
    
</table>

<%
  if (session.getAttribute("GFCUser").equals("yes")){
%>    


<!-- Comments table -->
<table width="100%"  cellpadding="0" cellspacing="2" bgcolor="#E4E7B4">
  <tr>
    <td><b>Comments</b>
    <hr/></td>
  </tr>
  <tr width="100%">
    <td width="100%" align="center"><textarea name="comment" rows="1" style="width: 90%;">--Comment on why you "favorited" this plane--</textarea>
  <br/>&nbsp;
     </td>
  </tr>
</table>
</form> 
<!-- -->

<%
    pm = PMF.get().getPersistenceManager();
   
    query = "select from " + FavoriteActivity.class.getName() + " where itemId == '" + planeid + "'" +  " order by date desc range 0,5";
    List<FavoriteActivity> activities = (List<FavoriteActivity>) pm.newQuery(query).execute();
%>

<table class="planes">
<%
    for (FavoriteActivity act : activities) {
    
    int month = act.getDate().getMonth();
    int year = act.getDate().getYear();
    int day = act.getDate().getDate();
%>
  <tr>
    <td>     
     <img src="<%= act.getThumbnailUrl() %>" class="thumbnailimage" />
      
      <%= act.getDisplayName() %>: <%= act.getActivity() %> </td><td align="right"> <h5><%= act.getDate() %></h5>  
    </td>
  </tr>     

<%
   }
   pm.close();
%>

</table>

<%
  }
%>  

</table>

<%@ include file="footer.jsp" %>
