<%@ include file="site-id.jsp" %>
<%! String regError = null; %>
<%! String errorMsg = null; %>

<%
   regError = request.getParameter("error");
    if (regError != null) {
      if (regError.equals("invalid")){
        errorMsg = "The username or password you entered is incorrect.";
      } 
    } 

%>

<html>
  <head>
    <style type="text/css">
	  @import url('css/planecrazy.css');
	</style>
    <link href="css/guitar.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('friendconnect', '0.8');
    </script>
    <script type="text/javascript">
    
      function onAjaxLoaded(){
        var SITE_ID = '<%= siteId %>';
        google.friendconnect.container.setParentUrl('.' /* location of rpc_relay.html and canvas.html */);      
        google.friendconnect.container.loadOpenSocialApi({ 
            site: SITE_ID,
            onload: function() { renderSignInButton();
             if (!window.timesloaded) {
              window.timesloaded = 1;
             } else {
              window.timesloaded++;
             }
            if (window.timesloaded > 1) {
            window.top.location.href = "/processgfclogin";
            }
             }});
       };      
                 
      function renderSignInButton() {
        google.friendconnect.renderSignInButton({"id":"button_div"});
      };
 
   google.setOnLoadCallback(onAjaxLoaded);                   
 
  </script>
  </head>  
  <body>
    <div id="wrapleft">
      <div id="wrapright">
        <div id="header">      
        </div>
             
          <div id="loginbar" class="shadow">
            <div class="contentbox">
      
<%
  if (errorMsg != null){
    out.println("<p class=\"error\">" + errorMsg + "</p>");
  }
  errorMsg = null;
%>
            <table border="1" class="planes" width="100%">
            <tr>
              <td>Registered Users Sign in Here</td>
              <td>Or use an existing Online Identity </td>
            </tr>
            <tr>
              <td align="right">
              <form id="form1" name="form1" method="post" action="/processlogin">
                User name: <input type="text" name="username" /><br/>
   	            Password:  <input type="password" name="password" /><br/>
	            <input name="" type="submit" value="Sign In" />
             </form>
             <p>Don't have an account? <a href="register.jsp">Register here</a></p>
             </td>
             <td>
               <div id="button_div">Loading GFC login button...</div>
             </td>
            </tr>
            </table>
            
         </div>
    </div>
  </div>
  </div>
<br/>



</body>
</html>