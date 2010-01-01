<%@ page import="java.util.*" %>
<%@ page import="org.opensocial.client.OpenSocialClient" %>
<%@ page import="org.opensocial.client.OpenSocialProvider" %>


<%
  
  final OpenSocialProvider provider = OpenSocialProvider.valueOf("FRIENDCONNECT");
  final OpenSocialClient client = new OpenSocialClient(provider);

 
  client.setProperty(OpenSocialClient.Property.DEBUG, "true");   
  client.setProperty(OpenSocialClient.Property.TOKEN_NAME, "fcauth");
 
  String auth_token = null;
  
  auth_token = (String)session.getAttribute("fcauth_token");
   
  if (auth_token != null){
    client.setProperty(OpenSocialClient.Property.TOKEN, auth_token);
  } else {
    out.println("token was null - going with backup");
   client.setProperty(OpenSocialClient.Property.TOKEN, "ALhR-_u-UnfjeqBvewxqhGX-VBJM3mG43A9_qQ0lx1KR7Jvm5faFoWaesCQIVFCiUGYn0FCf1MemEDwCR9Ml-ii_Wj4tV84WbQ");
 } 
  
%>