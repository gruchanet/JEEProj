package com.jee.web.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.jee.domain.Account;
import com.jee.domain.AccountLite;
import com.jee.domain.Player;
import com.jee.domain.Stats;
import com.jee.service.AccountManager;
import com.jee.service.LoginManager;
import com.jee.service.PlayerManager;
import com.jee.service.PlayerPinnerManager;
import com.jee.service.StatsManager;

@SessionScoped
//@Named("loginBean")
@ManagedBean(name = "loginBean")
public class LoginFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private AccountLite loggedUser = new AccountLite();
	
	private Player player = new Player();
	private Stats stats = new Stats();
	
	private ListDataModel<Player> accountPlayers = new ListDataModel<Player>();
	
	private String login = "";
	private String password = "";
	
	private String oldPassword = "";
	private String rptPassword = "";
	
	@Inject
	private LoginManager lm;
	
	@Inject
	private AccountManager am;
	
	@Inject
	private PlayerManager pm;
	
	@Inject
	private StatsManager sm;
	
	@Inject
	private PlayerPinnerManager ppm;
	
	
    public LoginFormBean() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setMaxInactiveInterval(1800); // 30mins before destroying bean (logoff)
    }
	
	public AccountLite getLoggedUser() {
		return loggedUser;
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
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getRptPassword() {
		return rptPassword;
	}

	public void setRptPassword(String rptPassword) {
		this.rptPassword = rptPassword;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public String loginUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		Account accountToLogin = lm.getAccount(login, MD5(password));
		
		clearFields();
		
		if (accountToLogin != null) {
			loggedUser.setId(accountToLogin.getId());
			loggedUser.setLogin(accountToLogin.getLogin());
			loggedUser.setPermissions(accountToLogin.getPermissions());
			// setting 'logged' flag to TRUE
			accountToLogin.setLogged(true);
			am.editAccount(accountToLogin);
			
			context.addMessage("loginForm:loginBtn", 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " Login successfully as " + loggedUser.getLogin() + "!", null));
			
			return "/home";
		}
		else {
			context.addMessage("loginForm:loginBtn", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " Login failed!", null));
			
			return null;
		}
	}
	
	public String logoutUser() {
		Account accountToLogout = am.getAccount(loggedUser.getId());
		// setting 'logged' flag to FALSE
		accountToLogout.setLogged(false);
		am.editAccount(accountToLogout);
		
		loggedUser.clearFields();
		accountPlayers = new ListDataModel<Player>();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("loginForm:loginBtn", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Successfully logged off!", null));
		
		return "/home";
	}
	
	public boolean isLogged() {
		if (loggedUser.getId() != null)
			return true;
		else
			return false;
	}
	
	public int getUserPermissions() {
		return loggedUser.getPermissions();
	}
	
	public String changePassword() {
		am.changePassword(loggedUser.getId(), password);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("passwordForm:submitBtn", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Successfully changed password!", null));
		
		clearPasswordFields();
		
		return "home";
	}
	
	public String createPlayer() {
		sm.addStats(stats);
		player.setStats(stats);
		
		pm.addPlayer(player);
		
		stats.clearFields();
		player.clearFields();
		
		ppm.pinPlayer(loggedUser.getId(), player.getId());
		
		return "user/players";
	}
	
	public String deletePlayer() {
		Player playerToDelete = accountPlayers.getRowData();
		
		pm.deletePlayer(playerToDelete);
		
		return null;
	}
	
	public ListDataModel<Player> getAccountPlayers() {
		accountPlayers.setWrappedData(am.getPlayers(loggedUser.getId()));
		
		return accountPlayers;
	}
	
	public boolean isPlayersEmpty() {
		return am.isPlayersEmpty(loggedUser.getId());
	}
	
	public void passwordsValidate(ComponentSystemEvent event) {
		UIForm form = (UIForm) event.getComponent();
		UIInput oldPassword = (UIInput) form.findComponent("oldPassword");
		UIInput password = (UIInput) form.findComponent("password");
		UIInput rptPassword = (UIInput) form.findComponent("rptPassword");
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (!(MD5((String) oldPassword.getValue())).equals(am.getAccountPassword(loggedUser.getId()))) {
			FacesMessage message = new FacesMessage(" * [Old password] field and current password do not match!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage("passwordForm:oldPassword", message);
			context.renderResponse();
		}
		
		if (!password.getValue().equals(rptPassword.getValue())) {		
			FacesMessage message = new FacesMessage(" * [Password] fields do not match!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage("passwordForm:password", message);
			context.renderResponse();
		}
		else if ((MD5((String) password.getValue())).equals(am.getAccountPassword(loggedUser.getId()))) {
			FacesMessage message = new FacesMessage(" * [Password] field and current password must be different!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage("passwordForm:password", message);
			context.renderResponse();
		}
		
		clearPasswordFields();
	}
	
	private String MD5(String toEncrypt) {
		String md5 = null;
		
		if (toEncrypt == null)
			return null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(toEncrypt.getBytes(), 0, toEncrypt.length());
			
			md5 = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
		return md5;
	}
	
	private void clearFields() {
		login = "";
		password = "";
	}
	
	private void clearPasswordFields() {
		oldPassword = "";
		password = "";
		rptPassword = "";
	}

}
