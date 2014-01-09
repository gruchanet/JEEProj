package com.jee.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.domain.Player;
import com.jee.service.AccountManager;

@SessionScoped
@Named("accountBean")
public class AccountFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Account account = new Account();
	private ListDataModel<Account> accounts = new ListDataModel<Account>();
	
	private Account accountToShow = new Account();
	private ListDataModel<Player> accountPlayers = new ListDataModel<Player>();
	
	@Inject
	private AccountManager am;
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public ListDataModel<Account> getAllAccounts() {
		accounts.setWrappedData(am.getAllAccounts());
		
		return accounts;
	}
	
	public ListDataModel<Player> getAccountPlayers() {
		accountPlayers.setWrappedData(am.getPlayers(accountToShow));
		
		return accountPlayers;
	}
	
	public String addAccount() {
		am.addAccount(account);
		
		return "showAccounts";
	}
	
	public String deleteAccount() {
		Account accountToDelete = accounts.getRowData();
		am.deleteAccount(accountToDelete);
		
		return null;
	}
	
	public String showPlayers() {
		accountToShow = accounts.getRowData();
		
		return "accountPlayers";
	}
	
	public String disposePlayer() {
		Player playerToDispose = accountPlayers.getRowData();
		am.disposePlayer(accountToShow, playerToDispose);
		
		return null;
	}

}
