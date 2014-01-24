package com.jee.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.jee.domain.Player;
import com.jee.service.PlayerManager;

@FacesConverter("PlayerConverter")
public class PlayerConverter implements Converter {
 
    @Inject
   	PlayerManager pm;
 
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
 
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Player player = pm.getPlayer(Long.parseLong(value));
            
            return player;
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format(
                    "Cannot convert %s to Player", value)), e);
        }
    }
 
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (!(value instanceof Player)) {
            return null;
        }
        
        return String.valueOf(((Player) value).getId());
    }
 
}