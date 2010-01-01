package com.google.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.model.PMF;
import com.google.model.UserRegistry;


public class RegisterUserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterUserServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
     //   UserService userService = UserServiceFactory.getUserService();
     //   User user = userService.getCurrentUser();

    	HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        String pwd2 = req.getParameter("pwd2");
        String email = req.getParameter("email");
        String thumbnailUrl = req.getParameter("thumbnailUrl");
        if (thumbnailUrl == null || thumbnailUrl.equals("http://") || thumbnailUrl.length() == 0){
        	thumbnailUrl = "/images/noimage.jpg";
        }
        Date date = new Date();
        
        if (pwd.equals(pwd2)){
          if (!usernameExists(username)) {       
            UserRegistry newUser = new UserRegistry(username, "", pwd, email, false, false, false, thumbnailUrl, "", date);

            PersistenceManager pm = PMF.get().getPersistenceManager();
            try {
                pm.makePersistent(newUser);
            } finally {
                pm.close();
            }
        	session.setAttribute("GFCUser", "no");
        	session.setAttribute("thumbnailurl", newUser.getThumbnailUrl());
        	session.setAttribute("email", newUser.getEmail());
        	session.setAttribute("postActivities", newUser.isPostActivity());
        	session.setAttribute("sendemail", newUser.isSendEmail());
        	session.setAttribute("displayname", newUser.getUsername());
        	session.setAttribute("userid", newUser.getUsername());
        	resp.sendRedirect("/app/planelist.jsp");
            //resp.sendRedirect("/processgfclogin");  
          } else { 
        	 resp.sendRedirect("register.jsp?error=un");
          }
        } else {
          resp.sendRedirect("register.jsp?error=pw");
        }
    }
    
    public boolean usernameExists(String username){
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<UserRegistry> users = null;
        boolean ans = false;
        try {
          String query = "select from " + UserRegistry.class.getName() + " where username == '" + username + "'";
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
    
}
