package com.FreshUpdater.appconfig;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.FreshUpdater.Models.Aduserhibernateobj;
import com.FreshUpdater.Models.Apidepartments;
import com.FreshUpdater.Models.Apilocationsqlobj;
import com.FreshUpdater.Models.Freshapiuserobj;


public class HiberConfig {
	private static SessionFactory newmeta = null;
	private static StandardServiceRegistry newregistry;
	private static String adusername;
	private static String aduserpassword;
	
	public static SessionFactory getSession() throws IOException {

		try {
			setAdusername(Appconfig.loadHiberusername());
			setAduserpassword(Appconfig.loadHiberPassword());
			Configuration newconf = new Configuration();
			// Create Properties, can be read from property files too
			Properties props = new Properties();
			props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
			props.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/freshupdatedb?createDatabaseIfNotExist=true");
			props.put("hibernate.connection.username", adusername);
			props.put("hibernate.connection.password", aduserpassword);
			props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
			props.put("hibernate.c3p0.acquire_increment", "1");
			props.put("hibernate.c3p0.idle_test_period", "100");
			props.put("hibernate.c3p0.max_size", "10");
			props.put("hibernate.c3p0.max_statements", "10");
			props.put("hibernate.c3p0.min_size", "10");
			props.put("hibernate.c3p0.timeout", "100");
			props.put("hibernate.id.new_generator_mappings", "true");
			props.put("hibernate.hbm2ddl.auto", "update");
			newconf.setProperties(props);
			newconf.addAnnotatedClass(Freshapiuserobj.class);
			newconf.addAnnotatedClass(Aduserhibernateobj.class);
			newconf.addAnnotatedClass(Apilocationsqlobj.class);
			newconf.addAnnotatedClass(Apidepartments.class);
			
			newregistry = new StandardServiceRegistryBuilder().applySettings(newconf.getProperties()).build();
			SessionFactory sessionFactory = newconf.buildSessionFactory(newregistry);
			return sessionFactory;

		} catch (HibernateException e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();

			if (newregistry != null) {
				StandardServiceRegistryBuilder.destroy(newregistry);
			}
		}
		return newmeta;

	}

	public static void shutdown() {
		if (newregistry != null) {
			StandardServiceRegistryBuilder.destroy(newregistry);
		}
	}

	public static String getAdusername() {
		return adusername;
	}

	public static String getAduserpassword() {
		return aduserpassword;
	}

	public static void setAdusername(String adusername) {
		HiberConfig.adusername = adusername;
	}

	public static void setAduserpassword(String aduserpassword) {
		HiberConfig.aduserpassword = aduserpassword;
	}
}
