package com.jee.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Account;
import com.jee.domain.Player;

@Stateless
public class AccountManager {
	
	@PersistenceContext
	EntityManager em;
	
	public void addAccount(Account account) {
		account.setId(null);
		em.persist(account);
	}

	public void deleteAccount(Account account) {
		account = em.find(Account.class, account.getId());
		em.remove(account);
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAllAccounts() {
		return em.createNamedQuery("account.all").getResultList();
	}

	public List<Player> getPlayers(Account account) {
		account = em.find(Account.class, account.getId());
		
		// lazy loading //
		List<Player> players = new ArrayList<Player>(account.getPlayers());
		return players;
	}
	
	public void addPlayer(Account account, Player player) {
		account = em.find(Account.class, account.getId());
		player = em.find(Player.class, player.getId());
		
		account.getPlayers().add(player);
	}
	
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
	
}
