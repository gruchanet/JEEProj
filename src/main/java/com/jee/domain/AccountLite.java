package com.jee.domain;

public class AccountLite {
	
	private Long id;
	private String login;
	private int permissions = 0;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	public void clearFields() {
		login = null;
		permissions = 0;
	}

}
