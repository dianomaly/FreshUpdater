package com.FreshUpdater.ADbringer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.FreshUpdater.Models.Aduserobject;
import com.FreshUpdater.RestQuery.Hiberexec;
import com.FreshUpdater.appconfig.Appconfig;

public class ADconnection {

	private String adusername;
	private String adpassword;
	private String ldapservername;
	private String adsearchbase;
	private String adsearchfilter;
	private Aduserobject newuser;
	private String[] adattrib = { "name", "physicalDeliveryOfficeName", "givenName", "sn", "userPrincipalName",
			"department", "manager", "mail", "title", "distinguishedName", "sAMAccountName","telephoneNumber"};

	public Hashtable<String, Object> createadconnection() throws IOException {
		try {
		setAdusername(Appconfig.loadLdapusername());
		setAdpassword(Appconfig.loadLdapuserpass());
		setLdapservername(Appconfig.loadLdapserver());
		}
	catch(IOException e) {
		System.err.println("Unable to set config");
		e.printStackTrace();
	}
		System.out.println(adusername + " " + adpassword + " " + ldapservername);
		Hashtable<String, Object> ldapconn = new Hashtable<String, Object>();

		if (!(adusername.isEmpty()) && !(adpassword.isEmpty()) && !(ldapservername.isEmpty())) {
			ldapconn.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapconn.put(Context.PROVIDER_URL, "LDAP://" + ldapservername + ":389");
			ldapconn.put(Context.SECURITY_PRINCIPAL, adusername);
			ldapconn.put(Context.SECURITY_CREDENTIALS, adpassword);
			ldapconn.put(Context.REFERRAL, "follow");
		}
		System.out.println("created ldap connection");
		return (ldapconn);
	}

	public void adsearch() throws NamingException, IOException {
		
		setAdsearchbase(Appconfig.loadLdapsearchbase());
		setAdsearchfilter(Appconfig.loadLdapsearchfilter());

		System.out.println(adsearchbase + adsearchfilter);
		LdapContext newcont = new InitialLdapContext(createadconnection(), null);

		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(adattrib);
		NamingEnumeration results = newcont.search(adsearchbase, adsearchfilter, controls);
		System.out.println(results);

		while (results.hasMore()) {
			System.out.println("Parsing ldap search results");
			SearchResult seresult = (SearchResult) results.next();
			Attributes ADattri = seresult.getAttributes();
			newuser = new Aduserobject();
			if (null != ADattri) {
				for (NamingEnumeration ae = ADattri.getAll(); ae.hasMoreElements();) {

					Attribute atr = (Attribute) ae.next();

					for (Enumeration vals = atr.getAll(); vals.hasMoreElements();) {
						String attributeID = atr.getID();
						
						switch (attributeID) {
						case "name":
							newuser.setFullname(vals.nextElement().toString());
							break;
						case "physicalDeliveryOfficeName":
							newuser.setCityname(vals.nextElement().toString());
							break;
						case "givenName":
							newuser.setFirstname(vals.nextElement().toString());
							break;
						case "sn":
							newuser.setLastname(vals.nextElement().toString());
							break;
						case "userPrincipalName":
							newuser.setUpname(vals.nextElement().toString());
							break;
						case "department":
							newuser.setDepartment(vals.nextElement().toString());
							break;
						case "manager":
							newuser.setManager(vals.nextElement().toString());
							break;
						case "mail":
							newuser.setUseremail(vals.nextElement().toString());
							break;
						case "title":
							newuser.setTitlename(vals.nextElement().toString());
							break;
						case "distinguishedName":
							newuser.setDistinguishedname(vals.nextElement().toString());
							break;
						case "sAMAccountName":
							newuser.setsAMAccountName(vals.nextElement().toString());
							break;
						case "telephoneNumber":
							newuser.setTelephoneNumber(vals.nextElement().toString());
							break;
								
						}

					}

				}
			}
			Hiberexec.setadusersqlobj(newuser.getFullname(), newuser.getCityname(), newuser.getFirstname(),
					newuser.getLastname(), newuser.getUpname(), newuser.getDepartment(), newuser.getManager(),
					newuser.getUseremail(), newuser.getTitlename(), newuser.getDistinguishedname(), newuser.getsAMAccountName(), newuser.getTelephoneNumber());
			
		}

	}

	public String getAdusername() {
		return adusername;
	}

	public String getAdpassword() {
		return adpassword;
	}

	public String getLdapservername() {
		return ldapservername;
	}

	public String getAdsearchbase() {
		return adsearchbase;
	}

	public String getAdsearchfilter() {
		return adsearchfilter;
	}

	public void setAdusername(String adusername) {
		this.adusername = adusername;
	}

	public void setAdpassword(String adpassword) {
		this.adpassword = adpassword;
	}

	public void setLdapservername(String ldapservername) {
		this.ldapservername = ldapservername;
	}

	public void setAdsearchbase(String adsearchbase) {
		this.adsearchbase = adsearchbase;
	}

	public void setAdsearchfilter(String adsearchfilter) {
		this.adsearchfilter = adsearchfilter;
	}
}
