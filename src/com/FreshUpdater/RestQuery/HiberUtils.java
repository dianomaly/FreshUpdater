package com.FreshUpdater.RestQuery;

import java.util.Properties;

import javax.imageio.spi.ServiceRegistry;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.swhtphone.Models.Extensionmodel;
import com.swhtphone.Models.FullLineBlock;
import com.swhtphone.Models.sqlrepmod;

public class HiberUtils {
	private static SessionFactory newmeta = null;
	// private static SessionFactory newsession;
	private static StandardServiceRegistry newregistry;

	public static SessionFactory getSession() {

		try {
			/*
			 * Configuration newconf = new Configuration().configure("hibernate.cfg.xml");
			 * newconf.addResource("Usagerepmodel.hbm.xml");
			 
			newregistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

			newmeta = new MetadataSources(newregistry).buildMetadata().buildSessionFactory();

			// newsession = newmeta.getSessionFactoryBuilder().build();
			 *
*/			
			Configuration newconf = new Configuration();
			// Create Properties, can be read from property files too
			Properties props = new Properties();
			props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
			props.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/excel_schema");
			props.put("hibernate.connection.username", "fullbringUS");
			props.put("hibernate.connection.password", "Customer1");
			props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			props.put("hibernate.c3p0.acquire_increment", "1");
			props.put("hibernate.c3p0.idle_test_period", "100");
			props.put("hibernate.c3p0.max_size", "10");
			props.put("hibernate.c3p0.max_statements", "10");
			props.put("hibernate.c3p0.min_size", "10");
			props.put("hibernate.c3p0.timeout", "100");
			props.put("hibernate.id.new_generator_mappings", "true");
			newconf.setProperties(props);
			newconf.addAnnotatedClass(FullLineBlock.class);
			newconf.addAnnotatedClass(sqlrepmod.class);
			newconf.addAnnotatedClass(Extensionmodel.class);
			newregistry = new StandardServiceRegistryBuilder().applySettings(newconf.getProperties()).build();
			//System.out.println("Hibernate Java Config serviceRegistry created");
			SessionFactory sessionFactory = newconf.buildSessionFactory(newregistry);
			return sessionFactory;

		} catch (HibernateException e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();
			//System.out.println("could not get session");

			if (newregistry != null) {
				StandardServiceRegistryBuilder.destroy(newregistry);
			}
		}
		return newmeta;

	}

	/*private static SessionFactory buildSessionJavaConfigFactory() {
		try {
			Configuration newconf = new Configuration();
			// Create Properties, can be read from property files too
			Properties props = new Properties();
			props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			props.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/excel_schema");
			props.put("hibernate.connection.username", "fullbringUS");
			props.put("hibernate.connection.password", "Customer1");
			props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			props.put("hibernate.c3p0.acquire_increment", "1");
			props.put("hibernate.c3p0.idle_test_period", "100");
			props.put("hibernate.c3p0.max_size", "10");
			props.put("hibernate.c3p0.max_statements", "10");
			props.put("hibernate.c3p0.min_size", "10");
			props.put("hibernate.c3p0.timeout", "100");
			newconf.setProperties(props);
			//newconf.addAnnotatedClass(FullLineBlock.class);
			newconf.addAnnotatedClass(sqlrepmod.class);
			newregistry = new StandardServiceRegistryBuilder().applySettings(newconf.getProperties()).build();
			System.out.println("Hibernate Java Config serviceRegistry created");
			SessionFactory sessionFactory = newconf.buildSessionFactory(newregistry);
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}*/

	public static void shutdown() {
		if (newregistry != null) {
			StandardServiceRegistryBuilder.destroy(newregistry);
		}
	}
}
