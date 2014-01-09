package com.jee.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Account;
import com.jee.domain.Player;

@Stateless
public class PlayerPinnerManager {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Player> getUnsetPlayers() {
		return em.createNamedQuery("player.unset").getResultList();
	}

	public void pinPlayer(Long idAccount, Long idPlayer) {
		Account account = em.find(Account.class, idAccount);
		Player player = em.find(Player.class, idPlayer);
		
		account.getPlayers().add(player);
		player.setAccount(account);
	}
	
	/*
	public boolean disposePlayer(Account account, Player player) {
		account = em.find(Account.class, account.getId());
		player = em.find(Player.class, player.getId());
		
		Player toRemove = null;
		
		// lazy loading //
		for (Player p : account.getPlayers())
			if (p.getId().compareTo(player.getId()) == 0) {
				toRemove = p;
				break;
			}
		
		if (toRemove != null) {
			account.getPlayers().remove(toRemove);
			
			return true;
		}
		
		return false;
	}
	*/
	
}
