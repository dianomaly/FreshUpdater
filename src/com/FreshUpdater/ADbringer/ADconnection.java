package com.FreshUpdater.ADbringer;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

public class ADconnection {

	private String adusername;
	private String adpassword;
	private String ldapservername;
	private String adsearchbase;
	private String adsearchfilter;
	private String firstname;
	private String cityname;
	private String fullname;
	private String upname;
	private String department;
	private String manager;
	private String titlename;
	private String[] adattrib = { "GivenName", "City", "CN", "userPrincipalName", "Department", "Manager", "Title" };
	
	
	public Hashtable<String, Object>  createadconnection() {
		Hashtable<String,Object> ldapconn = new Hashtable<String, Object>();
		
		if(!(adusername.isEmpty()) && !(adpassword.isEmpty()) && !(ldapservername.isEmpty())) {
		ldapconn.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		ldapconn.put(Context.PROVIDER_URL, ldapservername);
		ldapconn.put(Context.SECURITY_PRINCIPAL, adusername);
		ldapconn.put(Context.SECURITY_CREDENTIALS, adpassword);
		}
		return(ldapconn);
	}
	
	
	public void adsearch() throws NamingException{
		
		
	}
}
