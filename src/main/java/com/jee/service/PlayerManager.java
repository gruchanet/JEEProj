package com.jee.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Account;
import com.jee.domain.Player;

@Stateless
public class PlayerManager {

	@PersistenceContext
	EntityManager em;
	
	public void addPlayer(Player player) {
		player.setId(null);
		em.persist(player);
	}

	public void deletePlayer(Player player) {
		player = em.find(Player.class, player.getId());
		em.remove(player);
	}

	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers() {
		return em.createNamedQuery("player.all").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> getUnsetPlayers() {
		return em.createNamedQuery("player.unset").getResultList();
	}
	
	public Account getAccount(Player player) {
		player = em.find(Player.class, player.getId());
		
		// lazy loading //
		Account account = player.getAccount();
		return account;
	}
	
	public void pinAccount(Long idPlayer, Long idAccount) {
		if (idPlayer != null && idAccount != null) {
			Player player = em.find(Player.class, idPlayer);
			Account account = em.find(Account.class, idAccount);
			
			account.getPlayers().add(player);
		}
	}
	
	public void unpinAccount(Player player) {
		player = em.find(Player.class, player.getId());
		
		player.setAccount(null);
	}
	
}
