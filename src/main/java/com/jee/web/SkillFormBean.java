package com.jee.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import com.jee.domain.Skill;
import com.jee.service.SkillManager;

@SessionScoped
@Named("skillBean")
public class SkillFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Skill skill = new Skill();
	private ListDataModel<Skill> skills = new ListDataModel<Skill>();
	
	@Inject
	private SkillManager sm;
	
	public Skill getSkill() {
		return skill;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	public ListDataModel<Skill> getAllSkills() {
		skills.setWrappedData(sm.getAllSkills());
		
		return skills;
	}
	
	public String addSkill() {
		sm.addSkill(skill);
		
		return "showSkills";
	}
	
	public String deleteSkill() {
		Skill skillToDelete = skills.getRowData();
		sm.deleteSkill(skillToDelete);
		
		return null;
	}
	
}
