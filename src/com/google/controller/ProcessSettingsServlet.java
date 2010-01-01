package com.google.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

import org.opensocial.client.OpenSocialClient;
import org.opensocial.client.OpenSocialProvider;
import org.opensocial.data.OpenSocialPerson;

import com.google.model.Airplane;
import com.google.model.FavoriteActivity;
import com.google.model.PMF;
import com.google.model.UserRegistry;
import com.google.security.RandomString;

public class ProcessSettingsServlet extends HttpServlet {
  private static final Logger log = Logger.getLogger(ProcessSettingsServlet.class.getName());

  private List<UserRegistry> userlist;

  private HttpSession session = null;

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
    session = req.getSession();

    String postactivities = req.getParameter("postactivities");
    String emailaddress = req.getParameter("emailaddress");
    String sendemail = req.getParameter("sendemail");

    System.out.println("postActivities = " + postactivities);
    System.out.println("emailaddres = " + emailaddress);
    System.out.println("sendemail = " + sendemail);

    boolean emailflag = false;
    if (sendemail != null){
    	emailflag = true;
    }

    boolean activityflag = false;
    if (postactivities != null){
    	activityflag = true;
    }

    
    String statusMsg = setSettings(emailaddress, emailflag, activityflag, session);
   
   // session.setAttribute("faveitem", faveitem);
    resp.sendRedirect("/app/settings.jsp?status=" + statusMsg);
  }

  public String setSettings(String email, boolean sendemail, boolean postactivity, HttpSession session){
	   PersistenceManager pm = PMF.get().getPersistenceManager();

	   String gfcuser = (String)session.getAttribute("GFCUser");
	    String userid = null;
	    String wcid = null;
	
	      RandomString randStrClass = new RandomString(30);	      
	      String rand = randStrClass.nextString();
	    
	    
	    if (gfcuser.equals("yes")){
	      userid = (String)session.getAttribute("gfcid");
	      wcid = " where gfcUsername == '";
	    } else {
	      userid = (String)session.getAttribute("userid");
	      wcid = " where username == '";
	    }
	   
	 
	    List<UserRegistry> users = null;
	    UserRegistry thisPerson = null;
	    String returnMsg = "Settings saved...";
	    
	    try {
	          String query = "select from " + UserRegistry.class.getName() + wcid + userid + "'";
	          users = (List<UserRegistry>) pm.newQuery(query).execute();
	          System.out.println("queried for: " + query );
	          System.out.println("users size is: " + users.size());
	          
	          thisPerson = (UserRegistry)users.get(0); // grab first result
	        
	          if (!thisPerson.getEmail().equals(email)){
	        	  // set email, verify key and send email for this user - but only if changed from before
	        	  thisPerson.setEmail(email);
	        	  // set verification key on this user
	        	  thisPerson.setEmailVerifyKey(rand);
	        	  // reset to verified flag to false 
	        	  thisPerson.setEmailVerified(false);
	        	  // send confirmation email
	        	  sendVerificationEmail(rand, email);
	        	  returnMsg = "Settings saved... An email has also been sent to: " + email + " for verification purposes.";
	          }
	          
	          if (thisPerson.isSendEmail() != sendemail){
	        	  thisPerson.setSendEmail(sendemail);
	          }
	          
	          if (thisPerson.isPostActivity() != postactivity){
	        	  thisPerson.setPostActivity(postactivity);
	          }

	          
	          
	        } finally {
	          pm.close();
	     }

	  return returnMsg;
  }


  public void sendVerificationEmail(String rand, String email){
	  //Send out a verification email in order to verify that the entered email address is correct.
	  String address = email;
	  
	  String messageBody = "";
	  if (address != null){
		  // send email confirmation
	       Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        

	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("cschalk@gmail.com", "Plane-Crazy Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(address, "PlaneCrazy User"));
	            msg.setSubject("Confirmation email from Plane-Crazy Admin");
	            
	            messageBody = "Greetings!\n  Someone (or you) at Plane-Crazy registered this email address. Please " +
	            "click on the below link to verify that this is your email address: " +
	            "\n\n";
	            
	            messageBody += "Click here to verify: \n\n http://plane-crazy.appspot.com/verifyemail?v=" + rand + "\n\n";
	            messageBody += "Thanks, Plane-Crazy Admin";
	            
	            msg.setText(messageBody);
	            Transport.send(msg);

	        } catch (AddressException e) {
	            // ...
	        	System.out.println("addressException: ");
	        	e.printStackTrace();
	        } catch (MessagingException e) {
	            // ...
	        	System.out.println("MessagingException: ");
	        	e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
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

