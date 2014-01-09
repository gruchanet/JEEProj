package com.jee.domain;

public enum SkillType {
	PIERCE("Pierce"),
	CRUSH("Crush"),
	SLASH("Slash"),
	FIRE("Fire"),
	ICE("Ice"),
	EARTH("Earth"),
	ENERGY("Energy"),
	VOID("Void"),
	CHAOS("Chaos"),
	UNIVERSAL("Universal");
	
	private String desc;
	
	private SkillType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
