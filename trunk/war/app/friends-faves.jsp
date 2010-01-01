<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.opensocial.data.OpenSocialField" %>
<%@ page import="org.opensocial.data.OpenSocialPerson" %>

<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.model.UserRegistry" %>
<%@ page import="com.google.model.Airplane" %>
<%@ page import="com.google.model.PMF" %>

<%@ include file="../opensocial-client.jsp" %>

 
   <%@ include file="header-sidebar.jsp" %>
        

              <!-- MAIN CONTENT GOES HERE -->
              
<h3>Your friends chose the following airplanes as their favorites.</h3>

   <%     
    try {

      OpenSocialPerson person = client.fetchPerson();
   
      // get friends list      
      Collection<OpenSocialPerson> friends = client.fetchFriends(person.getId());
 
      // Cross reference friends list with user database and display only those           
       PersistenceManager pm = PMF.get().getPersistenceManager();
 
 
      %>
      <table class="planes" width="100%">
      <%   
        
      int x=0;  
      String evenodd = "even"; 
              
      for (OpenSocialPerson friend : friends) {
       // out.println("Friend: " + friend.getId() + " - " + friend.getDisplayName() + " <img src=\"" + friend.getThumbnailUrl() + "\"><br/>");        
       
        String query = ""; 
        
        query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + friend.getId() + "'";
        List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();

        if (users.size() > 0){
          // found a friend in the registry!
          UserRegistry u = (UserRegistry)users.get(0); // grab first result 
          
          // Display details of this friend and his/her favorite plane
          x++;
          
          if (x % 2 == 0) {
            evenodd = "even"; 
          } else {
            evenodd = "odd";
          }
          
          %>
          <tr class="<%= evenodd %>"><td> <img src="<%= friend.getThumbnailUrl() %>" />  <%= friend.getDisplayName() %>  </td>
          <%
          
          // Get details of favorite airplane:
          query = "select from " + Airplane.class.getName() + " where catalogId == '" + u.getFavePlane() + "'";
    
          List<Airplane> planes = (List<Airplane>) pm.newQuery(query).execute();
          if (planes.size() > 0){
           // found plane in registry!
           Airplane a = (Airplane)planes.get(0); // grab first result 
           %>
           <td> <img src="<%= a.getThumbnailUrl() %>" /> <%= a.getName() %> </td></tr>
           <%
          }
          
        }

      }    
     pm.close();
                         
          
    } catch (Exception e) {
        out.println("<b>Error:</b> An error occurred while attempting to fetch data from GFC.");
    }
    %>

  </table>
  
<%@ include file="footer.jsp" %>