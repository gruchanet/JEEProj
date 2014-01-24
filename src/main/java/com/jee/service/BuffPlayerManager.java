package com.jee.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Player;
import com.jee.domain.Skill;

@Stateless
public class BuffPlayerManager {
	
	@PersistenceContext
	EntityManager em;
	
	public Set<Skill> getSkills(Player player) {
		player = em.find(Player.class, player.getId());
		
		return player.getSkills();
	}
	
	/* getRestOfSkills */
	
//	public void buffPlayer(Long idPlayer, Long idSkill) {
//		Player player = em.find(Player.class, idPlayer);
//		Skill skill = em.find(Skill.class, idSkill);
//		
//		player.getSkills().add(skill);
//		skill.getPlayers().add(player);
//	}
	
	public void updatePlayerSkills(Player player, List<Skill> skills) {
		player = em.find(Player.class, player.getId());
		
		player.getSkills().clear();
		
		for (Skill skill : skills) {
			skill = em.find(Skill.class, skill.getId());
			
			player.getSkills().add(skill);
			skill.getPlayers().add(player);
		}
	}

}
