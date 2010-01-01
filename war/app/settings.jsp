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


<%! String myFaveItem = ""; %>
<%

   String statusMsg = request.getParameter("status");
   
   
   PersistenceManager pm = PMF.get().getPersistenceManager();

    String gfcuser = (String)session.getAttribute("GFCUser");
    String userid = null;
    
    if (gfcuser.equals("yes")){
      userid = (String)session.getAttribute("gfcid");
    } else {
      userid = (String)session.getAttribute("userid");
    }
 
    List<UserRegistry> users = null;
    UserRegistry thisPerson = null;
    String query = null;
        
    
    try {
        if (gfcuser.equals("yes")){
          query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + userid + "'";
        } else {
          query = "select from " + UserRegistry.class.getName() + " where username == '" + userid + "'";
        }  
          users = (List<UserRegistry>) pm.newQuery(query).execute();
          System.out.println("queried for: " + query );
          System.out.println("users size is: " + users.size());
          
          thisPerson = (UserRegistry)users.get(0); // grab first result
          
        } finally {
          pm.close();
     }
        
%>



  <%@ include file="header-sidebar.jsp" %>
        

              <!-- MAIN CONTENT GOES HERE -->
              
<h3>Plane Crazy User Settings</h3>

<%
if (statusMsg != null){
  out.println("<p class=\"status\">" + statusMsg + "</p>");
}
%>

      <table  class="planes" width="100%">
       <tr class="even" width="100%">
       <td>
       
       

<form id="form1" name="form1" method="post" action="/settings">
  <table width="100%" border="0" cellspacing="2" cellpadding="1">
    <tr>
      <td width="200px" valign="top"><strong>Messaging</strong></td>
      <td width="88%" valign="top"><input type="checkbox" name="postactivities" <%  out.print((thisPerson.isPostActivity()==true) ? "checked='yes'" : " " ); %>  />
      Post favorite plane selection to other networks affiliated with Google Friend Connect (ex: Plaxo). </td>
    </tr>
	<tr><td colspan="2"><hr/></td></tr>
    <tr>
      <td valign="top"><strong>Email</strong></td>
      <td valign="top">
          <input type="text" name="emailaddress"  value="<%= thisPerson.getEmail() %>" />
      Current email address
      <p>
        <input type="checkbox" name="sendemail" <% out.print((thisPerson.isSendEmail()==true) ? "checked='yes'" : " "); %>  />
      Send email acknowledgement when favorite plane selected.</p></td>
    </tr>
	<tr><td colspan="2"><hr/></td></tr>
    <tr>
      <td valign="top">&nbsp;</td>
      <td valign="top">
      <input type="submit" name="Submit" value="Save preferences" tabindex="4" /></td>
    </tr>
  </table>
</form>
  
       </td></tr>
        </table>
 
<%@ include file="footer.jsp" %>