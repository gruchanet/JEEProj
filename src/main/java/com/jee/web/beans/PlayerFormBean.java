package com.jee.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Player;
import com.jee.domain.Skill;
import com.jee.domain.Stats;
import com.jee.service.BuffPlayerManager;
import com.jee.service.PlayerManager;
import com.jee.service.SkillManager;
import com.jee.service.StatsManager;

@SessionScoped
@Named("playerBean")
public class PlayerFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Player player = new Player();
	private Stats stats = new Stats();
	private ListDataModel<Player> players = new ListDataModel<Player>();
	
	private Player playerToShow = new Player();
	private ListDataModel<Skill> playerSkills = new ListDataModel<Skill>();
	
	private Player playerToBuff = new Player();
	
	private Player playerToEdit = new Player();
	
	/* */
	private List<Skill> availableSkills = new ArrayList<Skill>();
	private List<Skill> pickedSkills = new ArrayList<Skill>();
	/* */
	
	@Inject
	private PlayerManager pm;
	
	@Inject
	private StatsManager sm;
	
	@Inject
	private SkillManager skm;
	
	@Inject
	private BuffPlayerManager bpm;
	
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayerToEdit() {
		return playerToEdit;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public Player getPlayerToBuff() {
		return playerToBuff;
	}
	
	public ListDataModel<Player> getAllPlayers() {
		players.setWrappedData(pm.getAllPlayers());
		
		return players;
	}
	
	public ListDataModel<Skill> getPlayerSkills() {
		playerSkills.setWrappedData(pm.getSkills(playerToShow));
		
		return playerSkills;
	}
	
	public List<Skill> getPickedSkills() {
		pickedSkills = pm.getSkills(playerToBuff);
		
		return pickedSkills;
	}
	
	public void setPickedSkills(List<Skill> skills) {
		this.pickedSkills = skills;
	}
	
	public List<Skill> getAvailableSkills() {
		availableSkills = skm.getAllSkills();
		
		return availableSkills;
	}
	
	public void setAvailableSkills(List<Skill> skills) {
		this.availableSkills = skills;
	}
	
	public String updateSkills() {
		bpm.updatePlayerSkills(playerToBuff, pickedSkills);
		
		return null;
	}
	
	public String addPlayer() {
		sm.addStats(stats);
		player.setStats(stats);
		
		pm.addPlayer(player);
		
		stats.clearFields();
		player.clearFields();
		
		return null;
	}
	
	public String editPlayer() {
		pm.editPlayer(playerToEdit);
		
		// optional //
		playerToEdit = new Player();
		
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
	
	public boolean isSkillsEmpty() {
		return pm.isSkillsEmpty(players.getRowData().getId());
	}
	
	public String showEdit() {
		playerToEdit = players.getRowData();
		
		return "/manage/editPlayer";
	}
	
	public String showSkills() {
		playerToShow = players.getRowData();
		
		return "/manage/playerSkills";
	}
	
	public String buffPlayer() {
		playerToBuff = players.getRowData();
		
		return "/manage/buffPlayer";
	}
	
	public String disposeSkill() {
		Skill skillToDispose = playerSkills.getRowData();
		pm.unbuffPlayer(playerToShow, skillToDispose);
		
		return null;
	}
	
	/*++++++++++++++++++++++++++++++++++++++++++++++*/
	public List<Player> getPlayerDummy() {
		List<Player> player = new ArrayList<Player>();
		
		player.add(playerToBuff);
		
		return player;
	}
	
}
