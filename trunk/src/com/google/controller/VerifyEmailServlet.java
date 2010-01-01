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


public class VerifyEmailServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(VerifyEmailServlet.class.getName());

       
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

    	String verify = req.getParameter("v");
       if ( verifyEmail(verify)){
         resp.sendRedirect("/verified.jsp"); 
       } // else do nothing..
    }
    
    
    
    public boolean verifyEmail(String verifykey){
    // lookup verifykey in UserRegistry - if found, set verified boolean flag to true.
      boolean response = false;
      
      PersistenceManager pm = PMF.get().getPersistenceManager();
      String query = "select from " + UserRegistry.class.getName() + " where emailVerifyKey == '" + verifykey + "'";
    	    
      List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();

      if (users.size() == 1){
        UserRegistry person = (UserRegistry)users.get(0); // grab result
        person.setEmailVerified(true);
        response = true;
      }

      pm.close();

     return response;	
    }
    
    
    
    
    
}
