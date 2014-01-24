package com.jee.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.jee.domain.Skill;
import com.jee.service.SkillManager;

@FacesConverter("SkillConverter")
public class SkillConverter implements Converter {
 
    @Inject
    SkillManager sm;
 
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
 
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Skill skill = sm.getSkill(Long.parseLong(value));
            
            return skill;
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format(
                    "Cannot convert %s to Skill", value)), e);
        }
    }
 
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (!(value instanceof Skill)) {
            return null;
        }
        
        return String.valueOf(((Skill) value).getId());
    }
 
}