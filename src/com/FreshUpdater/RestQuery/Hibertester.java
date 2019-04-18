package com.FreshUpdater.RestQuery;

import org.hibernate.Session;


public class Hibertester {
	//private static int ID;
	private static String userID;
	private static String active;
	private static String email;
	private static String name;
	private static String address;
	private static String job_title;
	private static String phone;
	private static String location_name;
	private static String deleted;

	public Hibertester() {
	}

	public static void setsqluser() {
		
		System.out.println(active);
		System.out.println(userID);
		System.out.println(name);
		
		Session session = HiberUtils.getSession().openSession();
		session.beginTransaction();

		getallapiuserobj newuser = new getallapiuserobj();

		//newuser.setID(ID);
		newuser.setActive(active);
		newuser.setAddress(address);
		newuser.setDeleted(deleted);
		newuser.setEmail(email);
		newuser.setJobtitle(job_title);
		newuser.setLocationname(location_name);
		newuser.setName(name);
		newuser.setPhone(phone);
		newuser.setUserID(userID);

		session.save(newuser);
		session.getTransaction().commit();

		/*
		 * session.beginTransaction();
		 * 
		 * adUserObj loaduser = (adUserObj) session.get(adUserObj.class, 1);
		 * 
		 * session.getTransaction().commit();
		 */
		session.close();

		HiberUtils.shutdown();

	}

	public static void setUserID(String userID) {
		Hibertester.userID = userID;
	}

	public static void setActive(String active) {
		Hibertester.active = active;
	}

	public static void setEmail(String email) {
		Hibertester.email = email;
	}

	public static void setName(String name) {
		Hibertester.name = name;
	}

	public static void setAddress(String address) {
		Hibertester.address = address;
	}

	public static void setJob_title(String job_title) {
		Hibertester.job_title = job_title;
	}

	public static void setPhone(String phone) {
		Hibertester.phone = phone;
	}

	public static void setLocation_name(String location_name) {
		Hibertester.location_name = location_name;
	}

	public static void setDeleted(String deleted) {
		Hibertester.deleted = deleted;
	}
}
