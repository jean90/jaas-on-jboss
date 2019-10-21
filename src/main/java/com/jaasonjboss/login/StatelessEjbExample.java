package com.jaasonjboss.login;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class StatelessEjbExample {

	@Resource
	private SessionContext ctx;
	
	public void testLogedInUser() {
		
		String user = ctx.getCallerPrincipal().getName();
		
		System.out.println("User--:" + user);
		
	}
	
}
