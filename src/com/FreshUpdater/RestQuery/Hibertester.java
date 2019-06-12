package com.FreshUpdater.RestQuery;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.FreshUpdater.Models.freshapiuserobj;
import com.FreshUpdater.Models.getallapiuserobj;
import com.mysql.cj.Query;


public class Hibertester {
	//private static int ID;
	private static int userID;
	private static String first_name;
	private static String email;
	private static String last_name;;
	private static String address;
	private static String job_title;
	private static String phone;
	private static int locationid;
	

	public Hibertester() {
	}

	public static void setsqluser() {
		
				
		Session session = HiberUtils.getSession().openSession();
		session.beginTransaction();

		freshapiuserobj newuser = new freshapiuserobj();

		//newuser.setID(ID);
		newuser.setUserID(userID);
		newuser.setFirst_name(first_name);
		newuser.setLast_name(last_name);
		newuser.setAddress(address);
		newuser.setEmail(email);
		newuser.setJobtitle(job_title);
		newuser.setLocationid(locationid);
		newuser.setPhone(phone);
		
		org.hibernate.query.Query validatequery = 
				session.createQuery("select user.email from freshapiuserobj as user where user.email = :objuser").setParameter("objuser", newuser.getEmail()); 
		System.out.println(validatequery.list());
		
		
		if(validatequery.list().isEmpty()) {
		session.save(newuser);
		session.getTransaction().commit();
		}
		session.close();

		HiberUtils.shutdown();

	}

	public static void setUserID(int userID) {
		Hibertester.userID = userID;
	}
	
	public static void setEmail(String email) {
		Hibertester.email = email;
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

	public static void setFirst_name(String first_name) {
		Hibertester.first_name = first_name;
	}

	public static void setLast_name(String last_name) {
		Hibertester.last_name = last_name;
	}

	public static void setLocationid(int locationid) {
		Hibertester.locationid = locationid;
	}

	

	}
