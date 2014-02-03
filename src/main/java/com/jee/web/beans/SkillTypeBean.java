package com.jee.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.jee.domain.SkillType;

@ManagedBean
@Named("skillTypeBean")
public class SkillTypeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public SelectItem[] getSkillTypeValues() {
		SelectItem[] items = new SelectItem[SkillType.values().length];

		int i = 0;
		for (SkillType st : SkillType.values()) {
			items[i++] = new SelectItem(st, st.getDesc());
		}

		return items;
	}

}
