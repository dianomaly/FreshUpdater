package com.FreshUpdater.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "alladusers")
public class Aduserhibernateobj {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	private int id;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "cityname")
	private String cityname;
	@Column(name = "fullname")
	private String fullname;
	@Column(name = "distinguishedname")
	private String distinguishedname;
	@Column(name = "upname")
	private String upname;
	@Column(name = "department")
	private String department;
	@Column(name = "manager")
	private String manager;
	@Column(name = "titlename")
	private String titlename;
	@Column(name = "useremail")
	private String useremail;
	@Column(name = "sAMAccountName")
	private String sAMAccountName;
	@Column(name = "phonenumber")
	private String phonenumber;
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="apiuserid")
	private Freshapiuserobj newapiuser;

	public Aduserhibernateobj() {
		}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getCityname() {
		return cityname;
	}
	public String getFullname() {
		return fullname;
	}
	public String getDistinguishedname() {
		return distinguishedname;
	}
	public String getUpname() {
		return upname;
	}
	public String getDepartment() {
		return department;
	}
	public String getManager() {
		return manager;
	}
	public String getTitlename() {
		return titlename;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setDistinguishedname(String distinguishedname) {
		this.distinguishedname = distinguishedname;
	}
	public void setUpname(String upname) {
		this.upname = upname;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	public Freshapiuserobj getNewapiuser() {
		return newapiuser;
	}
	public void setNewapiuser(Freshapiuserobj newapiuser) {
		this.newapiuser = newapiuser;
		
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	
}
