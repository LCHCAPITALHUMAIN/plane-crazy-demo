package com.google.controller;


import java.io.IOException;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
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

import javax.jdo.Query;


public class ProcessLoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ProcessLoginServlet.class.getName());
 
    HttpSession session = null;    
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException { 	
	  session = req.getSession(true);
      String username = req.getParameter("username");
      String pwd = req.getParameter("password");
        
	  if (validUser(username, pwd)){	
        resp.sendRedirect("/app/planelist.jsp");
	  } else {
		resp.sendRedirect("login.jsp?error=invalid");
	  }
    }
    
    
    public boolean validUser(String username, String password){
    	UserRegistry thisUser = null;
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<UserRegistry> users = null;
        boolean ans = false;
        try {
         
          Query query = pm.newQuery(UserRegistry.class);
                    
          query.setFilter("username == '" + username + "' && password == '" +  password + "'");
          
          users = (List<UserRegistry>) query.execute(); 
          if (users != null){
            if ( users.size() > 0){
              thisUser = users.get(0);
              session.setAttribute("authorized", "yes");
           	  session.setAttribute("GFCUser", "no");
        	    session.setAttribute("thumbnailurl", thisUser.getThumbnailUrl());
        	    session.setAttribute("displayname", thisUser.getUsername());
        	    session.setAttribute("userid", thisUser.getUsername());
        	    session.setAttribute("faveitem", thisUser.getFavePlane());
              ans = true;
            }
          }
        } finally {
          pm.close();
        }
		return (ans);  
    }
}
