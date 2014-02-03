package com.jee.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.jee.domain.Account;
import com.jee.service.AccountManager;

@FacesValidator("uniqueAccountValidator")
public class AccountValidator implements Validator {
	
	@Inject
	private AccountManager am;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object obj) {
		String login = (String) obj;
		
		for (Account account : am.getAllAccounts()) {
			if (account.getLogin().equals(login)) {
				FacesMessage message = new FacesMessage(" * Account with that login already exists!");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}
}
