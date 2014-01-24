package com.jee.web;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.jee.domain.Skill;
import com.jee.service.SkillManager;
 
@FacesConverter("_SkillsConverter")
public class _SkillsConverter implements Converter {
	
	@Inject
    private SkillManager sm;
 
    public Object getAsObject(FacesContext facesContext, UIComponent component, String s) {
        for (Skill skill : getSkillManager(facesContext).getAllSkills()) {
            if (skill.getName().equals(s)) {
                return skill;
            }
        }
        
        return null;
    }
 
    public String getAsString(FacesContext facesContext, UIComponent component, Object o) {
        if (o == null) return null;
        return ((Skill) o).getName();
    }
 
    private SkillManager getSkillManager(FacesContext facesContext) {
        if (sm == null) {
            ELContext elContext = facesContext.getELContext();
            sm = (SkillManager) elContext.getELResolver().getValue(elContext, null, "sm");
        }
        
        return sm;
    }
}