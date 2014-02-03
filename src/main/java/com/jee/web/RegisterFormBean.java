package com.jee.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.service.AccountManager;

@SessionScoped
@Named("registerBean")
public class RegisterFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Account account = new Account();
	
	private String rptPassword = "";
	
	@Inject
	private AccountManager am;
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getRptPassword() {
		return rptPassword;
	}
	
	public void setRptPassword(String rptPassword) {
		this.rptPassword = rptPassword;
	}
	
	public String registerAccount() {
		am.addAccount(account);
		account.clearFields();
		
		return "loginNow";
	}
	
	public void passwordsValidate(ComponentSystemEvent event) {
		UIForm form = (UIForm) event.getComponent();
		UIInput password = (UIInput) form.findComponent("password");
		UIInput rptPassword = (UIInput) form.findComponent("rptPassword");
		
		if (!password.getValue().equals(rptPassword.getValue())) {
			FacesContext context = FacesContext.getCurrentInstance();
			
			FacesMessage message = new FacesMessage(" * [Password] fields do not match!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage("registerForm:password", message);
			context.renderResponse();
		}
		
		this.rptPassword = "";
	}

}
