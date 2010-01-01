package com.google.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



import org.opensocial.client.OpenSocialClient;
import org.opensocial.client.OpenSocialProvider;
import org.opensocial.data.OpenSocialPerson;

import com.google.model.Airplane;
import com.google.model.FavoriteActivity;
import com.google.model.PMF;
import com.google.model.UserRegistry;

public class SelectFaveServlet extends HttpServlet {
  private static final Logger log = Logger.getLogger(SelectFaveServlet.class.getName());

  private List<UserRegistry> userlist;

  private HttpSession session = null;

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
    session = req.getSession();

    String user = req.getParameter("user");
    String faveitem = req.getParameter("faveitem");
    String comment = req.getParameter("comment");

    setFaveItem(user, faveitem, comment, session);
    session.setAttribute("faveitem", faveitem);
    resp.sendRedirect("/app/detail.jsp?planeid=" + faveitem);
  }

  public void setFaveItem(String userId, String itemId, String comment, HttpSession session){
    // Update the users table with the new favorite item id

    // 1. Fetch the specific user from the User Registry
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = null;
    String gfcuser = (String)session.getAttribute("GFCUser");
    if (gfcuser.equals("yes")){
      query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + userId + "'";
    } else {
      query = "select from " + UserRegistry.class.getName() + " where username == '" + userId + "'";
    }

    List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();

    UserRegistry person = (UserRegistry)users.get(0); // grab first result
    person.setFavePlane(itemId);

    pm.close();

    // Share activities if warranted
    shareActivity(gfcuser, person, userId, itemId, comment );
  }

  
void shareActivity(String gfcuser, UserRegistry person, String userId, String itemId, String comment){
  //post activity to other networks if warranted
  if (gfcuser.equals("yes") && person.isPostActivity() ){
    postGFCActivity(userId, itemId, comment, session);
  }  
  
  //send email confirmation if warranted
  if ( person.isSendEmail() && person.isEmailVerified() ){
    try {
		sendConfEmail(person, itemId, comment);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }  

	
}
  
  public void sendConfEmail(UserRegistry person, String itemId, String comment) throws UnsupportedEncodingException{
	  // Send confirmation email to user's email account
	  String address = person.getEmail();
	  if (address != null){
		  // send email confirmation
	       Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        
	        String postComment = "";
	        String itemName = getItemNameFromId(itemId);

	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("cschalk@gmail.com", "Plane-Crazy Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(address, "PlaneCrazy User"));
	            msg.setSubject("Confirmation: You have just selected a favorite airplane at Plane-Crazy");
	            
	            if (!comment.equals("--Comment on why you \"favorited\" this plane--")){
	                postComment = comment;
	              } else {
	                postComment = "This is an email confirmation that you just selected a " + itemName + 
	                " as your favorite plane " +
	                " at the Plane-Crazy website! \n\n Sincerely, \nPlane-Crazy Admin";
	              }
	            
	            msg.setText(postComment);
	            Transport.send(msg);

	        } catch (AddressException e) {
	            // ...
	        	System.out.println("addressException: ");
	        	e.printStackTrace();
	        } catch (MessagingException e) {
	            // ...
	        	System.out.println("MessagingException: ");
	        	e.printStackTrace();
	        }
	  }
  }
 
  

  public void postGFCActivity(String userid, String itemId, String comment, HttpSession session){

    final OpenSocialProvider provider = OpenSocialProvider.valueOf("FRIENDCONNECT");
    final OpenSocialClient client = new OpenSocialClient(provider);


    client.setProperty(OpenSocialClient.Property.DEBUG, "true");         
    client.setProperty(OpenSocialClient.Property.TOKEN_NAME, "fcauth");

    String auth_token = null;        
    auth_token = (String)session.getAttribute("fcauth_token");

    if (auth_token != null){
      client.setProperty(OpenSocialClient.Property.TOKEN, auth_token);

    } else {
      // backup plan...
      client.setProperty(OpenSocialClient.Property.TOKEN, "ALhR-_u-UnfjeqBvewxqhGX-VBJM3mG43A9_qQ0lx1KR7Jvm5faFoWaesCQIVFCiUGYn0FCf1MemEDwCR9Ml-ii_Wj4tV84WbQ");       	                                                     
    }

    OpenSocialPerson person = null;
    String personName = null;
    String postComment = null;
    String thumbnailUrl = null;

    try {
      person = client.fetchPerson();
      personName = person.getDisplayName();
      thumbnailUrl = person.getThumbnailUrl();

      String itemName = getItemNameFromId(itemId);

      if (!comment.equals("--Comment on why you \"favorited\" this plane--")){
        postComment = comment;
      } else {
        postComment = "I just selected " + itemName + " as my favorite plane!";
      }

      client.createActivity(personName + " just selected a favorite plane!", postComment);  
      logGFCActivity(userid, thumbnailUrl, personName, postComment, itemId);


    } catch (Exception e) {
      e.printStackTrace();			
    }
  }

  public String getItemNameFromId(String itemId){
    // 1. Fetch item name from Airplane Registry
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + Airplane.class.getName() + " where catalogId == '" + itemId + "'";

    List<Airplane> planes = (List<Airplane>) pm.newQuery(query).execute();

    Airplane plane = (Airplane)planes.get(0); // grab first result
    String returnName = null;

    returnName = plane.getName();

    if (returnName != null){
      return returnName;
    } else {
      return "unable to find plane name for: " + itemId;
    }

  }

  public void logGFCActivity(String userid, String thumbnailUrl, String personName, String postComment, String itemId){

    FavoriteActivity activity = new FavoriteActivity("", userid, personName, thumbnailUrl, postComment , itemId, new Date());

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent((FavoriteActivity)activity);

    } finally {
      pm.close();
    }
  }


} 

