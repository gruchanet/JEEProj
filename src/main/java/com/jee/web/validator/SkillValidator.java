package com.jee.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.jee.domain.Skill;
import com.jee.service.SkillManager;

@FacesValidator("uniqueSkillValidator")
public class SkillValidator implements Validator {
	
	@Inject
	private SkillManager sm;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object obj) {
		String name = (String) obj;
		
		for (Skill skill : sm.getAllSkills()) {
			if (skill.getName().equals(name)) {
				FacesMessage message = new FacesMessage(" * Skill with that name already exists!");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}
}
