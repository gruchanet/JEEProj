package com.jee.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Account;
import com.jee.domain.Player;
import com.jee.service.AccountManager;
import com.jee.service.PlayerPinnerManager;

@SessionScoped
@Named("playerPinnerBean")
public class PlayerPinnerFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private PlayerPinnerManager ppm;

	@Inject
	private AccountManager am;

	private Long idPlayer;
	private Long idAccount;
	
	public Long getIdPlayer() {
		return idPlayer;
	}
	
	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
	}
	
	public Long getIdAccount() {
		return idAccount;
	}
	
	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}
	
	public List<Player> getUnsetPlayers() {
		return ppm.getUnsetPlayers();
	}

	public List<Account> getAllAccounts() {
		return am.getAllAccounts();
	}

	public String pinPlayer() {
		if (idAccount != null && idPlayer != null)
			ppm.pinPlayer(idAccount, idPlayer);
		
		return null;
	}
	
}
