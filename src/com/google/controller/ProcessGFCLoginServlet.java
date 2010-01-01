package com.google.controller;


import java.io.IOException;

import java.util.List;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.*;


//import java.util.*;
import org.opensocial.client.OpenSocialClient;
//import org.opensocial.client.OpenSocialOAuthClient;
import org.opensocial.client.OpenSocialProvider;
//import org.opensocial.client.OpenSocialRequestException;
//import org.opensocial.client.Token;
import org.opensocial.data.OpenSocialPerson;

import com.google.model.PMF;
import com.google.model.UserRegistry;


public class ProcessGFCLoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ProcessGFCLoginServlet.class.getName());

    private static final String siteId = "10935529058185209327"; 
    private String fcauth = "ALhR-_s_kAvP75L85y3R38yxE2R2Rrz9avYfKP8Gx3_KqxttBPNKcMlR5_hQElsnpH_9dRRF2Qbpk66BZ70v2suKXls9bOrQ5w";                              
    private	static final String BASE_URI = "http://www.google.com/friendconnect/api/";

    
    HttpSession session = null;
    
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

    	
    	session = req.getSession(true);
    	
    	// Check for fc_auth token, place into session, then redirect
    	
        String cookieName = "fcauth" + this.siteId;
        Cookie cookies [] = req.getCookies();

        Cookie authCookie = null;

        if (cookies != null){
          for (int i = 0; i < cookies.length; i++) {          
              if (cookies[i].getName().equals(cookieName)) {            	
                authCookie = cookies[i];             
                
                // Store authCookie on session for later use
                String cookieStringValue = authCookie.getValue();
                session.setAttribute("fcauth_token", cookieStringValue); 
                session.setAttribute("authorized", "yes");
                session.setAttribute("GFCUser", "yes");
                                     
                break;
              } else {
            	  // no cookie match if running local, will use backup
            	  System.out.println("Cookie not found. For Debug purposes going with backup.");
            	  session.setAttribute("fcauth_token", "ALhR-_vAANWER71TgCvEHdMpS0KOfcR8scDvQIgoUCgKbR3qTzgFZH0GjiL7tfGMJ8ge5gAep2lx-8aTHpnzWl_m3pabF-ttaQ");
                session.setAttribute("authorized", "yes");
                session.setAttribute("GFCUser", "yes");
              }
             
          }
        }
        getGFCProfileInfo(resp);
        createNewUserEntryIfNew();
       resp.sendRedirect("/app/planelist.jsp");
    }
    
    
    public void getGFCProfileInfo(HttpServletResponse resp) throws IOException{
        // fetch userid, thumbnail and displayname and place onto session
        
        final OpenSocialProvider provider = OpenSocialProvider.valueOf("FRIENDCONNECT");
        final OpenSocialClient client = new OpenSocialClient(provider);

        
        client.setProperty(OpenSocialClient.Property.DEBUG, "true");         
        client.setProperty(OpenSocialClient.Property.TOKEN_NAME, "fcauth");
       
        String auth_token = null;        
        auth_token = (String)session.getAttribute("fcauth_token");
        
        if (auth_token != null){
        	client.setProperty(OpenSocialClient.Property.TOKEN, auth_token);
            
        } else {
        	resp.getWriter().println("error auth_token was null");
        	System.out.println("error auth_token was null");
        	// backup plan if auth_token is null...
        	client.setProperty(OpenSocialClient.Property.TOKEN, "ALhR-_vAANWER71TgCvEHdMpS0KOfcR8scDvQIgoUCgKbR3qTzgFZH0GjiL7tfGMJ8ge5gAep2lx-8aTHpnzWl_m3pabF-ttaQ");
      	                                                     
        }
               
        OpenSocialPerson person;
		try {
			person = client.fetchPerson();
			resp.getWriter().println("Your name is "+person.getDisplayName()+" and your ID is "+person.getId()+"</b>. Thumbnail is " + person.getThumbnailUrl() + ".");

			System.out.println("Your name is "+person.getDisplayName()+" and your ID is "+person.getId()+"</b>. Thumbnail is " + person.getThumbnailUrl() + ".");

			session.setAttribute("gfcid", person.getId());
			session.setAttribute("displayname", person.getDisplayName());
			session.setAttribute("thumbnailurl", person.getThumbnailUrl());
	    session.setAttribute("GFCUser", "yes");
	      	
	    //Get this person's fave item on also place on session	      	
	    String faveitem = getGFCPersonFaveItem(person);
	    session.setAttribute("faveitem", faveitem);
	      	
			
		} catch (Exception e) {
			try {
				resp.getWriter().println("Error: An error occurred while attempting to fetch data from GFC");
				resp.getWriter().println(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
 	
    }
    
    public void createNewUserEntryIfNew(){
      if (!gfcUsernameExists((String)session.getAttribute("gfcid"))){	
    	PersistenceManager pm = PMF.get().getPersistenceManager();
        UserRegistry newUser = new UserRegistry("gfc-user", (String) session.getAttribute("gfcid"), "", "", false, false, true, "http://", "", new Date());

        try {
            pm.makePersistent(newUser);
        } finally {
            pm.close();
        }
      }	
    }
    
    public boolean gfcUsernameExists(String username){
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<UserRegistry> users = null;
        boolean ans = false;
        try {
          String query = "select from " + UserRegistry.class.getName() + " where gfcUsername == '" + username + "'";
          users = (List<UserRegistry>) pm.newQuery(query).execute();
          if (users != null){
            if ( users.size() > 0){
              ans = true;
            }
          }
        } finally {
          pm.close();
        }
		return (ans);   ///users.size() > 0);   	
    }
    
    
    
    public String getGFCPersonFaveItem(OpenSocialPerson person){
       	UserRegistry thisUser = null;
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<UserRegistry> users = null;
        try {

          Query query = pm.newQuery(UserRegistry.class);
                    
          query.setFilter("gfcUsername == '" + person.getId() + "'");
          
          users = (List<UserRegistry>) query.execute(); 
          if (users != null){
            if ( users.size() > 0){
              thisUser = users.get(0);
              return (thisUser.getFavePlane());
            }
          }
        } finally {
          pm.close();
        }

    	return "";
    }
    
}
