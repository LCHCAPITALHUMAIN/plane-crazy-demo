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
<html>
<body>

<h2>User Registry</h2>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name in the registration.</p>
<%
    }
%>


<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + UserRegistry.class.getName();
    List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();
    
    out.println("User is this size: " + users.size());
    
   // pm.close();
    out.println("got here!");
%>

<p>User Registry:</p>


<%
        for (UserRegistry u : users) {
%>
<p>  | <%= u.getUsername() %>  | <%= u.getGfcUsername() %>  | <%= u.getPassword() %>  | <%= u.getEmail() %> | <%= u.isSendEmail() %>  | <%= u.isPostActivity() %>  | <%= u.getThumbnailUrl() %> | <%= u.getFavePlane() %>  </p>
<%
        }
%>




<h2>Airplane Registry</h2>

<%
    query = "select from " + Airplane.class.getName();
    List<Airplane> airplanes = (List<Airplane>) pm.newQuery(query).execute();
    if (airplanes.isEmpty()) {
%>
<p>The Airplane Registry has no entries.</p>
<%
    } else {
%>    

<%
        for (Airplane a : airplanes) {
%>
<p><b><%= a.getName() %></b> | <%= a.getManufacturer() %></b> | <%= a.getId() %>  | <%= a.getCatalogId() %>  |<%= a.getImgUrl() %>  | <%= a.getThumbnailUrl() %> </p>
<%
        }
    }
   // pm.close();
%>



<h2>Favoriate Activity Registry</h2>

<%
    query = "select from " + FavoriteActivity.class.getName();
    List<FavoriteActivity> activities = (List<FavoriteActivity>) pm.newQuery(query).execute();
    if (activities.isEmpty()) {
%>
<p>The FaveActivities Registry has no entries.</p>
<%
    } else {
%>    

<%
        for (FavoriteActivity a : activities) {
%>

<p><b><%= a.getUsername() %></b> | <%= a.getGfcUsername() %></b> | <%= a.getDisplayName() %>  | <%= a.getThumbnailUrl() %>  |<%= a.getActivity() %> |  <%= a.getItemId() %> | <%= a.getDate() %> </p>
<%
        }
    }
    pm.close();
%>



</body>
</html>