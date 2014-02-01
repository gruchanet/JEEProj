package com.jee.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.domain.AccountLite;
import com.jee.service.LoginManager;

@SessionScoped
@Named("loginBean")
public class LoginFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private AccountLite loggedUser = new AccountLite();
	
	private String login = null;
	private String password = null;
	
	@Inject
	private LoginManager lm;
	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String loginUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		Account accountToLogin = lm.getAccount(login, password);
		
		if (accountToLogin != null) {
			loggedUser.setId(accountToLogin.getId());
			loggedUser.setLogin(accountToLogin.getLogin());
			loggedUser.setPermissions(accountToLogin.getPermissions());
			
			context.addMessage("loginForm:loginBtn", new FacesMessage(FacesMessage.SEVERITY_INFO, "Login successfully.", null));
		}
		else	
			context.addMessage("loginForm:loginBtn", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed.", null));
		
		clearFields();
		
		return null;
	}
	
	public String logoutUser() {
		clearUserFields();
		
		return null;
	}
	
	private void clearFields() {
		login = null;
		password = null;
	}
	
	private void clearUserFields() {
		loggedUser.clearFields();
	}

}
