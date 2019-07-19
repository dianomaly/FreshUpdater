package com.FreshUpdater.Appconfig;

import java.io.FileInputStream;
import java.io.IOException;

public class Appconfig {
	
private static String configlocation = "./config.properties";


	public static String loadapiuserurl() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("ApiUsersUrl").toString();

	}

	public static String loadApikey() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);
		
		return loadconfigs.getProperty("Apikey").toString();

	}

	public static String loadLocationUrl() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("LocationUrl").toString();

	}

	public static String loadUsersFilter() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("UsersFilter").toString();

	}

	public static String loadDeptUrl() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("DeptUrl").toString();

	}

	public static String loadHiberusername() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("Hiber_username").toString();

	}

	public static String loadHiberPassword() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		loadconfigs.getProperty("ApiUsersUrl");

		return loadconfigs.getProperty("Hiber_userpassword").toString();

	}

	public static String loadLdapusername() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		loadconfigs.getProperty("ApiUsersUrl");

		return loadconfigs.getProperty("Ldap_username").toString();

	}

	public static String loadLdapuserpass() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("Ldap_userpassword").toString();

	}
	
	public static String loadAgent() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("AgentUrl").toString();

	}
	public static String loadLdapserver() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("Ldap_servername").toString();

	}

	public static String loadLdapsearchbase() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("Ldap_adsearchbase").toString();

	}

	public static  String loadLdapsearchfilter() throws IOException {
		java.util.Properties loadconfigs = new java.util.Properties();
		FileInputStream configlocal = new FileInputStream(configlocation);
		loadconfigs.load(configlocal);

		return loadconfigs.getProperty("Ldap_adsearchfilter").toString();

	}

	public static String getConfiglocation() {
		return configlocation;
	}

	public static void setConfiglocation(String configlocation) {
		Appconfig.configlocation = configlocation;
	}

}
