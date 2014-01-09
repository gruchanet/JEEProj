package com.jee.web;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.service.AccountManager;

@SessionScoped
@Named("accountBean")
public class AccountFormBean {
	
	private Account account = new Account();
	
	@Inject
	private AccountManager am;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String addAccount() {
		am.addAccount(account);
		return "showAccounts";
	}

}
