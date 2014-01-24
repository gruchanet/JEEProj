package com.jee.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.domain.Player;
import com.jee.service.AccountManager;
import com.jee.service.PlayerPinnerManager;

@SessionScoped
@Named("accountBean")
public class AccountFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Account account = new Account();
	private ListDataModel<Account> accounts = new ListDataModel<Account>();
	
	private Account accountToShow = new Account();
	private ListDataModel<Player> accountPlayers = new ListDataModel<Player>();
	
	private Account accountToEdit = new Account();
	
	private Account accountToPin = new Account();
	
	/* */
	private List<Player> availablePlayers = new ArrayList<Player>();
	private List<Player> pickedPlayers = new ArrayList<Player>();
	/* */
	
	@Inject
	private AccountManager am;
	
	@Inject
	private PlayerPinnerManager ppm;
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccountToEdit() {
		return accountToEdit;
	}
	
	public ListDataModel<Account> getAllAccounts() {
		accounts.setWrappedData(am.getAllAccounts());
		
		return accounts;
	}
	
	public ListDataModel<Player> getAccountPlayers() {
		accountPlayers.setWrappedData(am.getPlayers(accountToShow));
		
		return accountPlayers;
	}
	
	public List<Player> getPickedPlayers() {
		pickedPlayers = am.getPlayers(accountToPin);
		
		return pickedPlayers;
	}
	
	public void setPickedPlayers(List<Player> players) {
		this.pickedPlayers = players;
	}
	
	public List<Player> getAvailablePlayers() {
		availablePlayers = ppm.getIncludeUnsetPlayers(accountToPin.getId());
		
		return availablePlayers;
	}
	
	public void setAvailablePlayers(List<Player> players) {
		this.availablePlayers = players;
	}
	
	public String updatePlayers() {
		ppm.updateAccountPlayers(accountToPin, pickedPlayers);
		
		return null;
	}
	
	public String addAccount() {
		am.addAccount(account);
		account.clearFields();
		
		return null;
	}
	
	public String editAccount() {
		am.editAccount(accountToEdit);
		
		// optional //
		accountToEdit = new Account();
		
		return "showAccounts";
	}
	
	public String deleteAccount() {
		Account accountToDelete = accounts.getRowData();
		am.deleteAccount(accountToDelete);
		
		return null;
	}
	
	public boolean isPlayersEmpty() {
		return am.isPlayersEmpty(accounts.getRowData().getId());
	}
	
	public String showEdit() {
		accountToEdit = accounts.getRowData();
		
		return "editAccount";
	}
	
	public String showPlayers() {
		accountToShow = accounts.getRowData();
		
		return "accountPlayers";
	}
	
	public String pinPlayers() {
		accountToPin = accounts.getRowData();
		
		return "pinPlayers";
	}
	
	public String disposePlayer() {
		Player playerToDispose = accountPlayers.getRowData();
		am.disposePlayer(accountToShow, playerToDispose);
		
		return null;
	}

}
