package com.jee.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Player;
import com.jee.domain.Stats;

@Stateless
public class StatsManager {
	
	@PersistenceContext
	EntityManager em;
	
	public void addStats(Stats stats) {
		stats.setId(null);
//		em.persist(stats);
	}

	@SuppressWarnings("unchecked")
	public List<Stats> getAllStats() {
		return em.createNamedQuery("stats.all").getResultList();
	}

	public Player getPlayer(Stats stats) {
		stats = em.find(Stats.class, stats.getId());
		
		// lazy loading //
		Player player = stats.getPlayer();
		return player;
	}
	
}
