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
              
<h3>These users chose the same favorite airplane as you.</h3>

   <%     
    try {

      OpenSocialPerson person = client.fetchPerson();
 
 
       PersistenceManager pm = PMF.get().getPersistenceManager();
 
      // get all users list       
        String query = "";         
        query = "select from " + UserRegistry.class.getName();
        List<UserRegistry> allusers = (List<UserRegistry>) pm.newQuery(query).execute();
 
      //
      //        	    session.setAttribute("userid", thisUser.getUsername());
      //  	    session.setAttribute("faveitem", thisUser.getFavePlane());
 
 
 // NEED to fix!
 
      // Get my favorite item from my local data
        query = "";         
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
            
      Airplane a = null;
            
      for (UserRegistry user : allusers) { 
        if (user.getFavePlane().equals(myFaveItem)){
          // found a match! Now get user fave item object
              
          query = "select from " + Airplane.class.getName() + " where catalogId == '" + user.getFavePlane() + "'";
          List<Airplane> planes = (List<Airplane>) pm.newQuery(query).execute();
          if (planes.size() > 0){
            // found plane in registry!
            a = (Airplane)planes.get(0); // grab first result 
          }
              
          // Now check that this is the same favorite as me
          if (a.getCatalogId().equals(myFaveItem)){
            x++;         
            if (x % 2 == 0) {
              evenodd = "even"; 
            } else {
              evenodd = "odd";
            }
                            
              %>
                <tr class="<%= evenodd %>" width="100%"><td> <img src="<%= user.getThumbnailUrl() %>" />  <%= user.getUsername() %>  </td>
              <%          
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