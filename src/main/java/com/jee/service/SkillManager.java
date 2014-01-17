package com.jee.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Player;
import com.jee.domain.Skill;

@Stateless
public class SkillManager {
	
	@PersistenceContext
	EntityManager em;

	public void addSkill(Skill skill) {
		skill.setId(null);
		em.persist(skill);
	}
	
	public void editSkill(Skill skill) {
		em.merge(skill);
	}

	public void deleteSkill(Skill skill) {
		skill = em.find(Skill.class, skill.getId());
		
		for (Player player : skill.getPlayers())
			player.getSkills().remove(skill);
		
		em.remove(em.merge(skill));
	}
	
	@SuppressWarnings("unchecked")
	public List<Skill> getAllSkills() {
		return em.createNamedQuery("skill.all").getResultList();
	}
	
	/*
	public Set<Player> getPlayers(Skill skill) {
		skill = em.find(Skill.class, skill.getId());
		
		// lazy loading //
		List<Player> players = new ArrayList<Player>(skill.getPlayers());
		return players;
	}
	*/
	
	/* deleteSkillFromPlayer */
}
