package com.jee.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
//	@NamedQuery(name = "account.players", query = "SELECT acc.players FROM Account acc WHERE acc.id = :accountId"),
	@NamedQuery(name = "account.login", query = "SELECT acc FROM Account acc WHERE acc.login = :login AND acc.password = :password"),
	@NamedQuery(name = "account.all", query = "SELECT acc FROM Account acc")
})
public class Account {
	
	private Long id;
	private String login;
	private String password;
	private Date creationDate = new Date();
	private int permissions = 0;
	
	private List<Player> players = new ArrayList<Player>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	// lazy loading //
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,
			mappedBy="account", targetEntity=Player.class)
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void deletePlayer(Player player) {
		players.remove(player);
	}
	
	public void clearFields() {
		login = null;
		password = null;
		creationDate = new Date();
		permissions = 0;
	}

}
