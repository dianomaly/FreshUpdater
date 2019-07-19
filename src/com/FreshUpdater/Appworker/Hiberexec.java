package com.FreshUpdater.Appworker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.FreshUpdate.HiberModels.Aduserhibernateobj;
import com.FreshUpdate.HiberModels.Apiagent;
import com.FreshUpdate.HiberModels.Apidepartments;
import com.FreshUpdate.HiberModels.Apilocationsqlobj;
import com.FreshUpdate.HiberModels.Freshapiuserobj;
import com.FreshUpdater.Appconfig.HiberConfig;
import com.FreshUpdater.Models.Apideptpostobject;
import com.FreshUpdater.Models.Apiuserpostobj;
import com.FreshUpdater.Models.Apiuserputobj;

public class Hiberexec {
	private String apicityname;

	public Hiberexec() {
	}

	public void getalladusers() throws ClientProtocolException, IOException {
		Apicaller newaduserscall = new Apicaller();
		Session adsession = HiberConfig.getSession().openSession();
		adsession.beginTransaction();
		List<String> newuser = new ArrayList<>();
		CriteriaBuilder adcriteria = adsession.getCriteriaBuilder();
		CriteriaQuery<Aduserhibernateobj> adusers = adcriteria.createQuery(Aduserhibernateobj.class);
		Root<Aduserhibernateobj> startentry = adusers.from(Aduserhibernateobj.class);
		CriteriaQuery<Aduserhibernateobj> alladusers = adusers.select(startentry)
				.where(startentry.get("useremail").isNotNull());
		TypedQuery<Aduserhibernateobj> alladuserquery = adsession.createQuery(alladusers);
		for (Aduserhibernateobj aduserget : alladuserquery.getResultList()) {

			newuser.add(aduserget.getUseremail().toString());

		}
		newaduserscall.useremailfilterupdate(newuser);
		System.out.println(alladuserquery.getResultList());
		adsession.getTransaction().commit();
		adsession.close();
		HiberConfig.shutdown();

	}

	public static void setapisqluser(int userID, String first_name, String last_name, String email, String address,
			String job_title, String phone, int locationid, int managerid, int departmentid)
			throws HibernateException, IOException {

		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		Freshapiuserobj newuser = new Freshapiuserobj();
		newuser.setUserID(userID);
		newuser.setFirst_name(first_name);
		newuser.setLast_name(last_name);
		newuser.setAddress(address);
		newuser.setEmail(email);
		newuser.setJobtitle(job_title);
		newuser.setLocationid(locationid);
		newuser.setPhone(phone);
		newuser.setManagerid(managerid);
		newuser.setDepartmentid(departmentid);
		
		@SuppressWarnings("rawtypes")
		org.hibernate.query.Query apilocquery = session
				.createQuery("select user.id from Apilocationsqlobj as user where user.apiid = :apiidvar")
				.setParameter("apiidvar", locationid);
		if (!apilocquery.list().isEmpty()) {
			int apilocid = Integer.valueOf(apilocquery.list().get(0).toString());
			System.out.println(apilocid);
			// System.out.println(apiuserquery.list().get(1).toString());
			newuser.setUserslocation(session.load(Apilocationsqlobj.class, apilocid));
		}

		System.out.println(departmentid);
		@SuppressWarnings("rawtypes")
		org.hibernate.query.Query apideptquery = session
				.createQuery("select user.id from Apidepartments as user where user.deptapiid = :deptapiidvar")
				.setParameter("deptapiidvar", departmentid);

		System.out.println(apideptquery.list());
		if (!apideptquery.list().isEmpty()) {
			int apideptid = Integer.valueOf(apideptquery.list().get(0).toString());
			System.out.println(apideptid);
			// System.out.println(apiuserquery.list().get(1).toString());
			newuser.setUserdept(session.load(Apidepartments.class, apideptid));
		}

		@SuppressWarnings("rawtypes")
		org.hibernate.query.Query validatequery = session
				.createQuery("select user.email from Freshapiuserobj as user where user.email = :objuser")
				.setParameter("objuser", newuser.getEmail());
		System.out.println(validatequery.list());

		if ((validatequery.list().isEmpty())) {

			session.save(newuser);
			session.getTransaction().commit();
			session.close();
		} else {

			org.hibernate.query.Query getapisqlid = session
					.createQuery("select user.id from Freshapiuserobj as user where user.email = :objuser")
					.setParameter("objuser", newuser.getEmail());
			int apiuseridint = Integer.valueOf(getapisqlid.list().get(0).toString());
			Freshapiuserobj updateapiuser = session.load(Freshapiuserobj.class, apiuseridint);

			updateapiuser.setUserID(userID);
			updateapiuser.setFirst_name(first_name);
			updateapiuser.setLast_name(last_name);
			updateapiuser.setAddress(address);
			updateapiuser.setJobtitle(job_title);
			updateapiuser.setLocationid(locationid);
			updateapiuser.setPhone(phone);
			updateapiuser.setManagerid(managerid);
			updateapiuser.setDepartmentid(departmentid);

			if (!apilocquery.list().isEmpty()) {
				int apilocval = Integer.valueOf(apilocquery.list().get(0).toString());

				System.out.println(apilocval);
				updateapiuser.setUserslocation(session.load(Apilocationsqlobj.class, apilocval));
			}

			if (!apideptquery.list().isEmpty()) {
				int apideptidval = Integer.valueOf(apideptquery.list().get(0).toString());
				System.out.println(apideptidval);
				updateapiuser.setUserdept(session.load(Apidepartments.class, apideptidval));
			}
			session.update(updateapiuser);
			session.getTransaction().commit();
			session.close();
		}

		HiberConfig.shutdown();

	}
	
	public static void setagentsqluser(int userID, String first_name, String last_name, String email, String job_title, String phone, int locationid, int managerid)
			throws HibernateException, IOException {

		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		Apiagent newuser = new Apiagent();
		newuser.setApiid(userID);
		newuser.setFirst_name(first_name);
		newuser.setLast_name(last_name);
		newuser.setEmail(email);
		newuser.setJob_title(job_title);
		newuser.setLocation_id(locationid);
		newuser.setWork_phone_number(phone);
		newuser.setReporting_manager_id(managerid);
		
		@SuppressWarnings("rawtypes")
		org.hibernate.query.Query apilocquery = session
				.createQuery("select user.id from Apilocationsqlobj as user where user.apiid = :apiidvar")
				.setParameter("apiidvar", locationid);
		if (!apilocquery.list().isEmpty()) {
			int apilocid = Integer.valueOf(apilocquery.list().get(0).toString());
			System.out.println(apilocid);
			// System.out.println(apiuserquery.list().get(1).toString());
			newuser.setApilocation(session.load(Apilocationsqlobj.class, apilocid));
		}


		@SuppressWarnings("rawtypes")
		org.hibernate.query.Query validatequery = session
				.createQuery("select user.email from Apiagent as user where user.email = :objuser")
				.setParameter("objuser", newuser.getEmail());
		System.out.println(validatequery.list());

		if ((validatequery.list().isEmpty())) {

			session.save(newuser);
			session.getTransaction().commit();
			session.close();
		} else {

			org.hibernate.query.Query getapisqlid = session
					.createQuery("select user.id from Apiagent as user where user.email = :objuser")
					.setParameter("objuser", newuser.getEmail());
			int apiuseridint = Integer.valueOf(getapisqlid.list().get(0).toString());
			Apiagent updateapiuser = session.load(Apiagent.class, apiuseridint);

			updateapiuser.setApiid(userID);
			updateapiuser.setFirst_name(first_name);
			updateapiuser.setLast_name(last_name);
			updateapiuser.setJob_title(job_title);
			updateapiuser.setLocation_id(locationid);
			updateapiuser.setWork_phone_number(phone);
			updateapiuser.setReporting_manager_id(managerid);	

			if (!apilocquery.list().isEmpty()) {
				int apilocval = Integer.valueOf(apilocquery.list().get(0).toString());

				System.out.println(apilocval);
				updateapiuser.setApilocation(session.load(Apilocationsqlobj.class, apilocval));
			}

			
			session.update(updateapiuser);
			session.getTransaction().commit();
			session.close();
		}

		HiberConfig.shutdown();

	}
	
	public static void setadusersqlobj(String fullname, String cityname, String firstname, String lastname,
			String upname, String department, String manager, String useremail, String titlename,
			String distinguishedname, String sAMAccountName, String phonenumber)
			throws HibernateException, IOException {
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		Aduserhibernateobj newaduser = new Aduserhibernateobj();

		newaduser.setFirstname(firstname);
		newaduser.setLastname(lastname);
		newaduser.setCityname(cityname);
		newaduser.setFullname(fullname);
		newaduser.setDistinguishedname(distinguishedname);
		newaduser.setUpname(upname);
		newaduser.setDepartment(department);
		newaduser.setManager(manager);
		newaduser.setTitlename(titlename);
		newaduser.setUseremail(useremail);
		newaduser.setsAMAccountName(sAMAccountName);
		newaduser.setPhonenumber(phonenumber);
		System.out.println(useremail);
		System.out.println(upname);
		org.hibernate.query.Query apiuserquery = session
				.createQuery("select user.id from Freshapiuserobj as user where user.email = :email")
				.setParameter("email", useremail);
		if (!apiuserquery.list().isEmpty()) {
			int apiuserid = Integer.valueOf(apiuserquery.list().get(0).toString());
			System.out.println(apiuserid);
			// System.out.println(apiuserquery.list().get(1).toString());
			newaduser.setNewapiuser(session.load(Freshapiuserobj.class, apiuserid));
		}
		
		org.hibernate.query.Query apiagentuserquery = session
				.createQuery("select user.id from Apiagent as user where user.email = :email")
				.setParameter("email", useremail);
		if (!apiagentuserquery.list().isEmpty()) {
			int apiagentuserid = Integer.valueOf(apiagentuserquery.list().get(0).toString());
			System.out.println("agent id =" + apiagentuserid);
			// System.out.println(apiuserquery.list().get(1).toString());
			newaduser.setNewagent(session.load(Apiagent.class, apiagentuserid));
		}
		org.hibernate.query.Query validatequery = session
				.createQuery(
						"select user.id from Aduserhibernateobj as user where user.sAMAccountName = :sAMAccountName")
				.setParameter("sAMAccountName", sAMAccountName);

		if (validatequery.list().isEmpty()) {
			session.save(newaduser);
			session.getTransaction().commit();
		} else {

			int aduserid = Integer.valueOf(validatequery.list().get(0).toString());
			Aduserhibernateobj updateuser = session.load(Aduserhibernateobj.class, aduserid);

			updateuser.setFirstname(firstname);
			updateuser.setLastname(lastname);
			updateuser.setFullname(fullname);
			updateuser.setDistinguishedname(distinguishedname);
			updateuser.setUpname(upname);
			updateuser.setDepartment(department);
			updateuser.setManager(manager);
			updateuser.setTitlename(titlename);
			updateuser.setUseremail(useremail);

			if (!apiuserquery.list().isEmpty()) {
				int apiuserid = Integer.valueOf(apiuserquery.list().get(0).toString());

				updateuser.setNewapiuser(session.load(Freshapiuserobj.class, apiuserid));

			}
			
			if (!apiagentuserquery.list().isEmpty()) {
				int apiagentuserid = Integer.valueOf(apiagentuserquery.list().get(0).toString());
				System.out.println("agent id =" + apiagentuserid);
				// System.out.println(apiuserquery.list().get(1).toString());
				updateuser.setNewagent(session.load(Apiagent.class, apiagentuserid));
			}
			session.update(updateuser);
			session.getTransaction().commit();
		}
		session.close();
		HiberConfig.shutdown();
	}

	public static void setapideptobj(int deptapiid, String deptname) throws HibernateException, IOException {
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		Apidepartments newdept = new Apidepartments();

		newdept.setDeptapiid(deptapiid);
		newdept.setDeptname(deptname);
		org.hibernate.query.Query validatequery = session
				.createQuery("select dept from Apidepartments as dept where dept.deptapiid = :deptapiidvar")
				.setParameter("deptapiidvar", deptapiid);

		if (validatequery.list().isEmpty()) {
			session.save(newdept);
			session.getTransaction().commit();
		} else {
			String hqllocupdate = "UPDATE Apidepartments " + "SET deptname = :deptnamevar "
					+ "where deptapiid = :deptapiidvar";
			org.hibernate.query.Query deptquery = session.createQuery(hqllocupdate);
			deptquery.setParameter("deptnamevar", deptname);
			deptquery.setParameter("deptapiidvar", deptapiid);

			deptquery.executeUpdate();
			session.getTransaction().commit();

		}
		session.close();
		HiberConfig.shutdown();
	}

	public static void setapilocationobj(int apiid, String name, int parentlid, String line1, String line2, String city,
			String state, String country, String zipcode) throws HibernateException, IOException {
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		Apilocationsqlobj newlocation = new Apilocationsqlobj();

		newlocation.setApiid(apiid);
		newlocation.setName(name);
		newlocation.setParentlid(parentlid);
		newlocation.setLine1(line1);
		newlocation.setLine2(line2);
		newlocation.setCity(city);
		newlocation.setState(state);
		newlocation.setCountry(country);
		newlocation.setZipcode(zipcode);
		org.hibernate.query.Query validatequery = session
				.createQuery("select locations from Apilocationsqlobj as locations where locations.apiid = :apiid")
				.setParameter("apiid", apiid);

		if (validatequery.list().isEmpty()) {
			session.save(newlocation);
			session.getTransaction().commit();
		} else {
			String hqllocupdate = "UPDATE Apilocationsqlobj " + "SET name = :namevar, " + "parentlid = :parentlidvar, "
					+ "line1 = :line1var, " + "line2 = :line2var, " + "city = :cityvar, " + "state = :statevar, "
					+ "country = :countryvar, " + "zipcode = :zipcodevar " + "where apiid = :apiidvar";
			org.hibernate.query.Query locupquery = session.createQuery(hqllocupdate);
			locupquery.setParameter("namevar", name);
			locupquery.setParameter("parentlidvar", parentlid);
			locupquery.setParameter("line1var", line1);
			locupquery.setParameter("line2var", line2);
			locupquery.setParameter("cityvar", city);
			locupquery.setParameter("statevar", state);
			locupquery.setParameter("countryvar", country);
			locupquery.setParameter("zipcodevar", zipcode);
			locupquery.setParameter("apiidvar", apiid);

			locupquery.executeUpdate();
			session.getTransaction().commit();

		}
		session.close();
		HiberConfig.shutdown();
	}

	public void aduserapiuserdiff() throws ClientProtocolException, IOException {
		// Method to update api user with ad values
		// Creating session and transaction

		// Initializing variables
		int manageridval = 0;
		int adlocationval = 0;
		String worknumberval = " ";
		int[] deptvalarr = new int[1];
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		// Setting up criteria to get all the AD users and the API user field
		// corresponding with the AD user.
		// Criteria only goes 1 level deep and will not get the linked location values.
		CriteriaBuilder adcriteria = session.getCriteriaBuilder();
		CriteriaQuery<Aduserhibernateobj> apiusers = adcriteria.createQuery(Aduserhibernateobj.class);
		Root<Aduserhibernateobj> startentry = apiusers.from(Aduserhibernateobj.class);

		CriteriaQuery<Aduserhibernateobj> alladusers = apiusers.select(startentry);

		TypedQuery<Aduserhibernateobj> alladuserquery = session.createQuery(alladusers);
		System.out.println(alladuserquery.getFirstResult());

		for (Aduserhibernateobj adu : alladuserquery.getResultList()) {
			System.out.println("City name" + adu + " " + adu.getCityname());
			if (adu.getCityname() != null) {
				adlocationval = getapilocationidval(adu.getCityname(), session);
			}

			if (adu.getManager() != null) {
				manageridval = transformmanager(adu.getManager(), session);
			}

			if (adu.getDepartment() != null) {
				int deptval = getapideptval(adu.getDepartment(), session);
				deptvalarr[0] = deptval;
			}
			if (adu.getPhonenumber() != null) {
				worknumberval = transformworknumber(adu.getPhonenumber());
			}

			System.out.println("API name " + adu + " " + adu.getNewapiuser());
			if (adu.getNewapiuser() != null) {

				System.out.println("First Name: " + adu.getFirstname() + " = " + adu.getNewapiuser().getFirst_name());
				System.out.println("Last Name: " + adu.getLastname() + " = " + adu.getNewapiuser().getLast_name());
				System.out.println("Job Title: " + adu.getTitlename() + " = " + adu.getNewapiuser().getJobtitle());
				System.out.println("Phone Number: " + worknumberval + " = " + adu.getNewapiuser().getPhone());
				System.out.println("Location ID: " + adlocationval + " = " + adu.getNewapiuser().getLocationid());
				System.out.println("Manager ID: " + manageridval + " = " + adu.getNewapiuser().getManagerid());
				System.out.println("API user ID: " + adu.getNewapiuser().getUserID());

				if (adu.getFirstname() != String.valueOf(adu.getNewapiuser().getFirst_name())
						|| adu.getLastname() != adu.getNewapiuser().getLast_name()
						|| adu.getTitlename() != adu.getNewapiuser().getJobtitle()
						|| worknumberval != adu.getNewapiuser().getPhone()
						|| adlocationval != adu.getNewapiuser().getLocationid()
						|| manageridval != adu.getNewapiuser().getManagerid() && adu.getNewapiuser().getUserID() != 0) {

					if (manageridval > 0) {
						System.out.println(manageridval);
						Apiuserputobj updateapiuser = new Apiuserputobj();
						Apicaller apiputcall = new Apicaller();
						updateapiuser.setFirst_name(adu.getFirstname());
						updateapiuser.setLast_name(adu.getLastname());
						updateapiuser.setJob_title(adu.getTitlename());
						updateapiuser.setReporting_manager_id(manageridval);
						updateapiuser.setLocation_id(adlocationval);
						updateapiuser.setWork_phone_number(worknumberval);
						updateapiuser.setDepartment_ids(deptvalarr);
						System.out.println(adu.getNewapiuser().getUserID());
						System.out.println("Response code: "
								+ apiputcall.updateapiuser(updateapiuser, adu.getNewapiuser().getUserID()));
						System.out.println(adu.getNewapiuser().getUserID());
					}
				}

			}
		}
		session.getTransaction().commit();
		session.close();
		HiberConfig.shutdown();
	}

	public void addnewusers() throws ClientProtocolException, IOException {
		String worknumberval;
		int manageridval;
		int adlocationval;
		int[] deptvalarr = new int[1];
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		CriteriaBuilder adcriteria = session.getCriteriaBuilder();
		CriteriaQuery<Aduserhibernateobj> adusers = adcriteria.createQuery(Aduserhibernateobj.class);
		Root<Aduserhibernateobj> startentry = adusers.from(Aduserhibernateobj.class);
		CriteriaQuery<Aduserhibernateobj> alladusers = adusers.select(startentry)
				.where(startentry.get("newapiuser").isNull());

		TypedQuery<Aduserhibernateobj> alladuserquery = session.createQuery(alladusers);
		System.out.println(alladuserquery.getResultList().size());
		for (Aduserhibernateobj adu : alladuserquery.getResultList()) {
			System.out.println(adu.getFullname());
			if (adu.getManager() != null) {
				manageridval = transformmanager(adu.getManager(), session);
			} else {
				manageridval = 0;
			}
			if (adu.getDepartment() != null) {
				int deptval = getapideptval(adu.getDepartment(), session);
				deptvalarr[0] = deptval;
			}
			if (adu.getCityname() != null) {
				adlocationval = getapilocationidval(adu.getCityname(), session);
			} else {
				adlocationval = 0;
			}
			if (adu.getPhonenumber() != null) {
				worknumberval = transformworknumber(adu.getPhonenumber());
			} else {
				worknumberval = " ";
			}
			Apiuserpostobj newpostuser = new Apiuserpostobj();
			Apicaller apiputcall = new Apicaller();
			if (adu.getUseremail() != null || adu.getUseremail() != "null") {
				newpostuser.setFirst_name(adu.getFirstname());
				newpostuser.setLast_name(adu.getLastname());
				newpostuser.setJob_title(adu.getTitlename());
				newpostuser.setPrimary_email(adu.getUseremail());
				newpostuser.setWork_phone_number(worknumberval);
				newpostuser.setDepartment_ids(deptvalarr);
				newpostuser.setReporting_manager_id(manageridval);
				newpostuser.setLocation_id(adlocationval);
			}
			System.out.println("Response code: " + apiputcall.addapiuser(newpostuser));
		}

		session.getTransaction().commit();
		session.close();
		HiberConfig.shutdown();
	}

	public void addnewdept() throws ClientProtocolException, IOException {
		Apicaller newcall = new Apicaller();
		Session session = HiberConfig.getSession().openSession();
		session.beginTransaction();

		TypedQuery<String> getalldepts = session
				.createQuery("select DISTINCT user.department from Aduserhibernateobj as user");

		System.out.println(getalldepts.getResultList());
		for (String depcheck : getalldepts.getResultList()) {

			System.out.println(depcheck);
			org.hibernate.query.Query getapideptid = session
					.createQuery("select dept.deptapiid from Apidepartments as dept where dept.deptname = :deptidvar")
					.setParameter("deptidvar", depcheck);
			System.out.println(getapideptid.list());
			// System.out.println(depch);
			if (getapideptid.list().isEmpty() && depcheck != null && depcheck != "null") {
				Apideptpostobject newdeptpost = new Apideptpostobject();
				newdeptpost.setName(depcheck);
				System.out.println("Response code: " + newcall.addapidept(newdeptpost));
			}

		}

		session.getTransaction().commit();
		session.close();
		HiberConfig.shutdown();

	}

	public int transformmanager(String managerfield, Session session) {

		String getadmanager = managerfield;
		org.hibernate.query.Query getmanagerquery = session.createQuery(
				"select user.newapiuser.userID from Aduserhibernateobj as user where user.distinguishedname = :managervar")
				.setParameter("managervar", getadmanager);

		if (!getmanagerquery.list().isEmpty()) {
			int managerid = Integer.parseInt(getmanagerquery.list().get(0).toString());

			return managerid;
		}
		return 0;
	}

	public int getapilocationidval(String adusername, Session session) {

		String getapilocationname = transformadusercityfield(adusername);

		org.hibernate.query.Query validatequery = session
				.createQuery(
						"select location.apiid from Apilocationsqlobj as location where location.name = :locationvar")
				.setParameter("locationvar", getapilocationname);

		if (!validatequery.list().isEmpty()) {
			int apicitynameid = Integer.parseInt(validatequery.list().get(0).toString());

			return apicitynameid;
		}
		return 0;
	}

	public int getapideptval(String apideptname, Session session) {
		org.hibernate.query.Query validatequery = session
				.createQuery("select dept.deptapiid from Apidepartments as dept where dept.deptname = :deptnamevar")
				.setParameter("deptnamevar", apideptname);

		if (!validatequery.list().isEmpty()) {
			int apideptnameid = Integer.parseInt(validatequery.list().get(0).toString());

			return apideptnameid;
		}
		return 0;

	}

	public String transformworknumber(String worknumber) {

		if (worknumber.length() > 10) {

			String[] splitworkval = worknumber.split(" ");
			String numberval = splitworkval[0];
			return numberval;

		}

		return " ";
	}

	public String transformadusercityfield(String cityfield) {

		switch (cityfield) {
		case "NYC01":
			apicityname = "New York Office";
			return apicityname;
		case "DET01":
			apicityname = "Chicago Office";
			return apicityname;
		case "ATL01":
			apicityname = "Atlanta";
			return apicityname;
		case "WAR01":
			apicityname = "Europe";
			return apicityname;
		case "LON01":
			apicityname = "London";
			return apicityname;
		case "PAR01":
			apicityname = "France";
			return apicityname;
		case "SAO01":
			apicityname = "Brazil";
			return apicityname;
		case "BRU01":
			apicityname = "Brussels";
			return apicityname;
		case "SLK01":
			apicityname = "Srilanka";
			return apicityname;
		case "REMUS01":
			apicityname = "US";
			return apicityname;
		case "AKR01":
			apicityname = "Akron";
			return apicityname;
		case "MUN01":
			apicityname = "Munich";
			return apicityname;
		case "HYD01":
			apicityname = "India";
			return apicityname;
		case "REM01":
			apicityname = "US";
			return apicityname;

		}
		return (apicityname);
	}
}
