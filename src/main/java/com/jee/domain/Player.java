package com.jee.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	/*@NamedQuery(name = "player.skillsToGet", query = "SELECT DISTINCT s FROM Skill s "
			+ "OUTER JOIN Player_Skill ps ON ps.skills_id = s.id"
			+ "INNER JOIN Player p ON ps.players.id = p.id"
			+ "WHERE p.id = null"),*/
	@NamedQuery(name = "player.unset", query = "SELECT p FROM Player p WHERE p.account = null"),
	@NamedQuery(name = "player.all", query = "SELECT p FROM Player p")
})
public class Player {
	
	private Long id;
	private String name;
	private int level = 1;
	private int gold = 0;
	
	private Account account = null;
	
	private Set<Skill> skills = new HashSet<Skill>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	// lazy loading //
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	// lazy loading //
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="player_skill")
	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

}
