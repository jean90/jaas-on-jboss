package com.jaasonjboss.login;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;

public class CustomLoginModule implements LoginModule{

	private static final String ADMIN_USER = "testinguser";
	private static final String CLIENT_USER = "luan";
	
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
	
	private String user, password, rol;
	
	private boolean loginOk = false;
	
	public CustomLoginModule() {}
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		
		System.out.println("Entering to initialize...");
		
		
	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("Entering to login...");
		
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("name:");
		callbacks[1] = new PasswordCallback("password:", false);
		
		try {
			this.callbackHandler.handle(callbacks);
		} catch (IOException | UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NameCallback nameCallback = (NameCallback) callbacks[0];
		PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
		
		user = nameCallback.getName();		
		password = new String(passwordCallback.getPassword());
		
		if(user.equals(ADMIN_USER)) {
			rol = "ADMIN";
		}else if(user.equals(CLIENT_USER)) {
			rol = "CLIENT";
		}
		
		loginOk = true;
		
		return loginOk;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("Entering to commit...");
		
		if(!loginOk) {
			return loginOk;
		}
		
		Set principals = subject.getPrincipals();
		
		Principal principal = new SimplePrincipal(user);		
		principals.add(principal);
				
		Group rolesGroup = new SimpleGroup("Roles");
		rolesGroup.addMember(new SimplePrincipal(rol));		
		principals.add(rolesGroup);
		
	
		return loginOk;
	}
	
	@Override
	public boolean abort() throws LoginException {
		System.out.println("Entering to abort...");
		boolean logoutOk = logout();
		
		return logoutOk;
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("Entering to logout...");
		this.user = null;
		this.password = null;
		this.subject.getPrincipals().clear();
		
		return true;
	}

}
