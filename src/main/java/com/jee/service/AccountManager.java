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
	
	public void editAccount(Account account) {
		em.merge(account);
	}

	public void deleteAccount(Account account) {
		account = em.find(Account.class, account.getId());
		em.remove(account);
	}
	
	public Account getAccount(Long accountId) {
		Account account = em.find(Account.class, accountId);
		
		return account;
	}
	
	public String getAccountPassword(Long accountId) {
		String password = (String) em.createNamedQuery("account.getPassword")
			.setParameter("accountId", accountId)
			.getSingleResult();
		
		return password;
	}
	
	public void changePassword(Long accountId, String password) {
		Account account = em.find(Account.class, accountId);
		
		account.setPassword(password);
		
		em.merge(account);
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
	
	public List<Player> getPlayers(Long accountId) {
		Account account = em.find(Account.class, accountId);
		
		// lazy loading //
		List<Player> players = new ArrayList<Player>(account.getPlayers());
		return players;
	}
	
	public boolean isPlayersEmpty(Long accountId) {
//		List<Player> accountPlayers = em.createNamedQuery("account.players").setParameter("accountId", accountId).getResultList();
		if (accountId == null)
			return true;
		
		Account account = em.find(Account.class, accountId);
		
		List<Player> accountPlayers = new ArrayList<Player>(account.getPlayers());
		
//		System.out.println("accountPlayers *size*: " + accountPlayers.size());
		
		if (accountPlayers == null || accountPlayers.isEmpty())
			return true;
		
		return false;
	}
	
	/*
	public void addPlayer(Account account, Player player) {
		account = em.find(Account.class, account.getId());
		player = em.find(Player.class, player.getId());
		
		account.getPlayers().add(player);
	}
	*/
	
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
			player.setAccount(null);
			
			return true;
		}
		
		return false;
	}

}
