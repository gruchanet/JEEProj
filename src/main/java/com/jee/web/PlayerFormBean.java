package com.jee.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Player;
import com.jee.service.PlayerManager;

@SessionScoped
@Named("playerBean")
public class PlayerFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Player player = new Player();
	private ListDataModel<Player> players = new ListDataModel<Player>();
	
	private Player playerToShow = new Player();
	
	@Inject
	private PlayerManager pm;
	
	public Player getPlayer() {
		return player;
	}
	
	public void setAccount(Player player) {
		this.player = player;
	}
	
	public ListDataModel<Player> getAllPlayers() {
		players.setWrappedData(pm.getAllPlayers());
		
		return players;
	}
	
	public String addPlayer() {
		pm.addPlayer(player);
		
		return "showPlayers";
	}
	
	public String deletePlayer() {
		Player playerToDelete = players.getRowData();
		pm.deletePlayer(playerToDelete);
		
		return null;
	}
	
	public String unpinFromAccount() {
		pm.unpinAccount(playerToShow);
		
		return null;
	}
	
	public String showSkills() {
		playerToShow = players.getRowData();
		
		return "playerSkills";
	}
	
}
