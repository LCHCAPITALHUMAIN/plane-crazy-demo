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

<%! String myFaveItem = ""; %>

  <%@ include file="header-sidebar.jsp" %>
        

              <!-- MAIN CONTENT GOES HERE -->
              
<h3>These friends chose the same favorite airplane as you.</h3>

   <%     
    try {

      OpenSocialPerson person = client.fetchPerson();
 
      // get friends list       
      Collection<OpenSocialPerson> friends = client.fetchFriends(person.getId());
 
      // Cross reference friends list with user database and display only those           
       PersistenceManager pm = PMF.get().getPersistenceManager();
 
      // Get your favorite item from my local data
        String query = "";         
        query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + person.getId() + "'";
        List<UserRegistry> results = (List<UserRegistry>) pm.newQuery(query).execute();
        if (results.size() > 0){
          // found me in the registry!
          UserRegistry me = (UserRegistry)results.get(0); // grab first result 
          myFaveItem = me.getFavePlane();
        }
 
 
      %>
      <table  class="planes" width="100%">
      <%           
      int x=0;
      String evenodd = "odd";
            
      for (OpenSocialPerson friend : friends) {
        
        query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + friend.getId() + "'";
        List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();

        if (users.size() > 0){
          // found a friend in the registry!
          UserRegistry u = (UserRegistry)users.get(0); // grab first result 
          
          // Display details of this friend and his/her favorite plane
          // Get details of favorite airplane:
          query = "select from " + Airplane.class.getName() + " where catalogId == '" + u.getFavePlane() + "'";
          List<Airplane> planes = (List<Airplane>) pm.newQuery(query).execute();
          if (planes.size() > 0){
            // found plane in registry!
            Airplane a = (Airplane)planes.get(0); // grab first result 
          
            // Now check that this is the same favorite as me
            if (a.getCatalogId().equals(myFaveItem)){
              x++;         
              if (x % 2 == 0) {
                evenodd = "even"; 
                } else {
                 evenodd = "odd";
                }
              %>
                <tr class="<%= evenodd %>" width="100%"><td> <img src="<%= friend.getThumbnailUrl() %>" />  <%= friend.getDisplayName() %>  </td>
              <%
          
           %>
             <td> <img src="<%= a.getThumbnailUrl() %>" /> <%= a.getName() %> </td></tr>
           <%
           }
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