package com.jee.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

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
	
	private Stats stats;
	
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
	
	@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_stats")
	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	// lazy loading //
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY) // detach
	@JoinColumn(name = "id_account")
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	// lazy loading //
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY) // detach
	@JoinTable(name = "player_skill",
	joinColumns = {@JoinColumn(name = "id_player", referencedColumnName = "ID")},
	inverseJoinColumns = {@JoinColumn(name = "id_skill", referencedColumnName = "ID")})
	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	
	public void clearFields() {
		name = null;
		level = 1;
		gold = 0;
	}

}
