package com.jee.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.jee.domain.Player;
import com.jee.service.PlayerManager;

@FacesValidator("uniquePlayerValidator")
public class PlayerValidator implements Validator {
	
	@Inject
	private PlayerManager pm;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object obj) {
		String name = (String) obj;
		
		for (Player player : pm.getAllPlayers()) {
			if (player.getName().equals(name)) {
				FacesMessage message = new FacesMessage(" * Player with that name already exists!");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		}
	}
}
