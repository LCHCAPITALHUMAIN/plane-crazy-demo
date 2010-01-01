package com.google.security;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Simple security Filter for Guitar App. 
 * This filter restricts all access to items matching /app/*
 * unless a Authorization Key AUTH_USER is present in the HttpSession.
 * This key is toggled during login/logout.
 */
 
 
public class SecurityFilter implements Filter {
    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String AUTH_USER = "fcauth_token";
        String validuser = null;
        
        HttpSession session = req.getSession(true);        
        
        //If authorization key not in session, redirect to login page.
      //  validuser = (String) session.getAttribute(AUTH_USER);
  
        validuser = (String) session.getAttribute("authorized");
        
        if(validuser != null) {
          //If the user is allowed access to the URI, let the flow proceed as normal
          chain.doFilter(request, response);
          return;
        } 
        else{
          //User not allowed access - redirect to login page
          res.sendRedirect(req.getContextPath() +  "/login.jsp");
          return;
        }

    }
}
