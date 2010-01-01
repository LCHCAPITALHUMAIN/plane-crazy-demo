
<%@ include file="../site-id.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<style type="text/css">
		  @import url('../css/planecrazy.css');
		</style>
  </head>
  <body>
    <div id="wrapleft">
      <div id="wrapright">
        
        <div id="header">
          <div id="controls" class="shadow">
            <div class="contentbox">
              <img class="thumbnail" src="<%= session.getAttribute("thumbnailurl") %>">
              Hello,  <%= session.getAttribute("displayname") %> | 
              <a href="#">Invite</a> | 
              <a href="settings.jsp">Settings</a> |
              <a href="../signout.jsp">Sign Out</a>
            </div>
          </div>
        </div>
        
        <div id="content">        
          <div id="rightbar" >
          
 <!-- search link window begin -->
<div  id="links"  class="shadow">   
  <div  class="contentbox"> 

  <h2>Search  Plane  Crazy</h2>

  <form action="http://www.google.com/cse" id="cse-search-box">
    <div>
      <input type="hidden" name="cx" value="005913348296393588934:wfgbqwbz9as" />
      <input type="hidden" name="ie" value="UTF-8" />
      <input type="text" name="q" size="31" />
      <input type="submit" name="sa" value="Search" />
    </div>
  </form>

  <script type="text/javascript" src="http://www.google.com/jsapi"></script>

  <script type="text/javascript">google.load("elements", "1", {packages: "transliteration"});</script>
  <script type="text/javascript" src="http://www.google.com/coop/cse/t13n?form=cse-search-box&t13n_langs=en"></script>
  <script type="text/javascript" src="http://www.google.com/coop/cse/brand?form=cse-search-box&lang=en"></script>
  <br />

  <h2>Links</h2>
  <ul>
    <li><a href="planelist.jsp">Home</a></li>
    <li><a href="planelist.jsp">Browse Planes</a></li>
    <li><a href="users-same-fave.jsp">Users with same Favorite Planes as Me</a></li>
  
<%
      if (session.getAttribute("GFCUser").equals("yes")){
%>    
        <li><a href="friends-faves.jsp">Friend's Favorite Planes</a></li>
        <li><a href="friends-same-fave.jsp">Friends with same Favorite Plane as Me</a></li>
<%
      }
%>   
  
    <li><a href="#">Contact</a></li>
  </ul>

  </div>
</div>
 
 <!-- search links window end -->
 
     <% 
       String gfcCheck = (String) session.getAttribute("GFCUser");
       if (gfcCheck.equals("yes")){ 
     %>        
 <div  id="friends"  class="shadow">       
  <div class="contentbox">
  <!-- SIDEBAR CONTENT GOES HERE -->

              
<!-- Include the Google Friend Connect javascript library. -->
<script type="text/javascript" src="http://www.google.com/friendconnect/script/friendconnect.js"></script>
<!-- Define the div tag where the gadget will be inserted. -->
<div id="div-1901732442100357428" style="width:276px;border:1px solid #cccccc;"></div>
<!-- Render the gadget into a div. -->
<script type="text/javascript">
var skin = {};
                skin['BORDER_COLOR'] = 'transparent';
                skin['ENDCAP_BG_COLOR'] = '#E4E6C9';
                skin['ENDCAP_TEXT_COLOR'] = '#333333';
                skin['ENDCAP_LINK_COLOR'] = '#1B3A5A';
                skin['ALTERNATE_BG_COLOR'] = '#ffffff';
                skin['CONTENT_BG_COLOR'] = '#ffffff';
                skin['CONTENT_LINK_COLOR'] = '#1B3A5A';
                skin['CONTENT_TEXT_COLOR'] = '#333333';
                skin['CONTENT_SECONDARY_LINK_COLOR'] = '#1B3A5A';
                skin['CONTENT_SECONDARY_TEXT_COLOR'] = '#666666';
                skin['CONTENT_HEADLINE_COLOR'] = '#333333';
                skin['NUMBER_ROWS'] = '4';
google.friendconnect.container.setParentUrl('/' /* location of rpc_relay.html and canvas.html */);
google.friendconnect.container.renderMembersGadget(
 { id: 'div-1901732442100357428',
   site: '<%= siteId %>' },
  skin);
</script>
                 
            </div>
     <%
        }
     %>  
          </div>
        </div>

        
          <div id="leftbar" class="shadow">
            <div class="contentbox">