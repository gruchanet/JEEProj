package com.jee.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Account;
import com.jee.domain.Player;
import com.jee.domain.Skill;

@Stateless
public class PlayerManager {

	@PersistenceContext
	EntityManager em;
	
	public void addPlayer(Player player) {
		player.setId(null);
		em.persist(player);
	}
	
	public void editPlayer(Player player) {
		em.merge(player);
	}

	public void deletePlayer(Player player) {
		player = em.find(Player.class, player.getId());
		
		if (player.getAccount() != null)
			player.getAccount().deletePlayer(player);
		
		em.remove(em.merge(player));
	}

	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers() {
		return em.createNamedQuery("player.all").getResultList();
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<Player> getUnsetPlayers() {
		return em.createNamedQuery("player.unset").getResultList();
	}
	*/
	
	public Account getAccount(Player player) {
		player = em.find(Player.class, player.getId());
		
		// lazy loading //
		Account account = player.getAccount();
		return account;
	}
	
	public List<Skill> getSkills(Player player) {
		player = em.find(Player.class, player.getId());
		
		// lazy loading //
		List<Skill> skills = new ArrayList<Skill>(player.getSkills());
		return skills;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isSkillsEmpty(Long playerId) {
		List<Skill> playerSkills = em.createNamedQuery("player.skills").setParameter("playerId", playerId).getResultList();
		
		if (playerSkills.isEmpty())
			return true;
		
		return false;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Skill> getRestSkills(Player player) {
//		player = em.find(Player.class, player.getId());
//		
//		List<Skill> restSkills = em.createNamedQuery("skill.all").getResultList();
//		List<Skill> playerSkills = new ArrayList<Skill>(player.getSkills());
//		
//		// remove from restSkills
//		for (Skill skill : playerSkills)
//			restSkills.remove(skill);
//		
//		return restSkills;
////		Query query = em.createNamedQuery("player.restSkills");
////		query.setParameter("IDPlayer", player.getId());
////		
////		return query.getResultList();
//	}
	
	/*
	public void pinAccount(Long idPlayer, Long idAccount) {
		if (idPlayer != null && idAccount != null) {
			Player player = em.find(Player.class, idPlayer);
			Account account = em.find(Account.class, idAccount);
			
			account.getPlayers().add(player);
		}
	}
	*/
	
	public void unpinAccount(Player player) {
		player = em.find(Player.class, player.getId());
		
		player.setAccount(null);
	}
	
	public void unbuffPlayer(Player player, Skill skill) {
		player = em.find(Player.class, player.getId());
		skill = em.find(Skill.class, skill.getId());
		
		player.getSkills().remove(skill); 
		skill.getPlayers().remove(player); // needed?
	}
	
	public Player getPlayer(Long id) {
		Player player = em.find(Player.class, id);
		
		return player;
	}
	
}
