package com.jee.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Skill;
import com.jee.service.BuffPlayerManager;
import com.jee.service.SkillManager;

@SessionScoped
@Named("buffPlayerBean")
public class BuffPlayerFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SkillManager sm;

	@Inject
	private BuffPlayerManager bpm;
	
	private Long idPlayer;
	private Long idSkill;
	
	public Long getIdPlayer() {
		return idPlayer;
	}
	
	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
	}
	
	public Long getIdSkill() {
		return idSkill;
	}
	
	public void setIdSkill(Long idSkill) {
		this.idSkill = idSkill;
	}
	
	/* getRestOfSkills */
	
	public List<Skill> getAllSkills() {
		return sm.getAllSkills();
	}
	
//	public String buffPlayer() {
//		if (idPlayer != null && idSkill != null)
//			bpm.buffPlayer(idPlayer, idSkill);
//		
//		return null;
//	}
	
}
