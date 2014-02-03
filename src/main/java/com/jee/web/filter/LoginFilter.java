package com.jee.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jee.web.beans.LoginFormBean;
 
@WebFilter("/user/*")
public class LoginFilter implements Filter {
	
//    private FilterConfig fc;
	
//	@Inject
//	private LoginFormBean loginBean;

    public LoginFilter() {}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        LoginFormBean loginBean = (LoginFormBean)((HttpServletRequest)request).getSession().getAttribute("loginBean");
         
        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (loginBean == null || !loginBean.isLogged() || (loginBean.isLogged() && loginBean.getUserPermissions() >= 4)) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/home.jsf");
        }
        else
        	chain.doFilter(request, response);
             
    }
 
    public void init(FilterConfig fConfig) throws ServletException {
//        this.fc = fConfig;
    }
 
    public void destroy() {}  
     
}
