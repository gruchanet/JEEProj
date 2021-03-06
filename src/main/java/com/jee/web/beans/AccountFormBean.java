package com.jee.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
		if (!accountToEdit.isLogged()) {
			
			am.editAccount(accountToEdit);
			
			// optional //
			accountToEdit = new Account();
			
			return "showAccounts";
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			
			context.addMessage("accountForm:login", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " * Cannot edit logged account!", null));
			
			return null;
		}
	}
	
	public String deleteAccount() {
		Account accountToDelete = accounts.getRowData();
		
		if (!accountToDelete.isLogged()) {
			am.deleteAccount(accountToDelete);
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			
			context.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " * Cannot delete logged account!", null));
		}
		
		return null;
	}
	
	public boolean isPlayersEmpty() {
		return am.isPlayersEmpty(accounts.getRowData().getId());
	}
	
	public String showEdit() {
		accountToEdit = accounts.getRowData();
		
		return "/manage/editAccount";
	}
	
	public String showPlayers() {
		accountToShow = accounts.getRowData();
		
		return "/manage/accountPlayers";
	}
	
	public String pinPlayers() {
		accountToPin = accounts.getRowData();
		
		return "/manage/pinPlayers";
	}
	
	public String disposePlayer() {
		Player playerToDispose = accountPlayers.getRowData();
		am.disposePlayer(accountToShow, playerToDispose);
		
		return null;
	}

}
