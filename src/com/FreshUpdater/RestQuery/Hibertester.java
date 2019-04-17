package com.FreshUpdater.RestQuery;

import org.hibernate.Session;

import com.testrest.cooler.sqlapiuserobj;

public class Hibertester {
	private static int ID;
	private static String userID;
	private static String active;
	private static String email;
	private static String name;
	private static String address;
	private static String job_title;
	private static String phone;
	private static String location_name;
	private static String deleted;

	public void Hibertester() {
	}

	public static void setsqluser() {

		Session session = hiberUtils.getSession().openSession();
		session.beginTransaction();

		sqlapiuserobj newuser = new sqlapiuserobj();

		newuser.setID(ID);
		newuser.setActive(active);
		newuser.setAddress(address);
		newuser.setDeleted(deleted);
		newuser.setEmail(email);
		newuser.setJob_title(job_title);
		newuser.setLocation_name(location_name);
		newuser.setName(name);
		newuser.setPhone(phone);
		//newuser.setUserID(userID);

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

		hiberUtils.shutdown();

	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}
