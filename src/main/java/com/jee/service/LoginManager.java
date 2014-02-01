package com.jee.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jee.domain.Account;

@Stateless
public class LoginManager {
	
	@PersistenceContext
	EntityManager em;

	public Account getAccount(String login, String password) {
		try {
			return (Account) em.createNamedQuery("account.login")
					.setParameter("login", login)
					.setParameter("password", password)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	/* public boolean isAccount(String login, String password) {
		try {
			Account account = (Account) em.createNamedQuery("account.login")
					.setParameter("login", login)
					.setParameter("password", password)
					.getSingleResult();
			
			if (account != null)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	} */
}
