package com.FreshUpdater.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "apiusertable")
public class Freshapiuserobj {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "ID")
	private int idkey;
	@Column(name = "userID")
	private int userID;
	@Column(name = "first_name")
	private String first_name;
	@Column(name = "last_name")
	private String last_name;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "jobtitle")
	private String jobtitle;
	@Column(name = "locationid")
	private int locationid;
	@Column(name = "phone")
	private String phone;
	@Column(name = "managerid")
	private int managerid;
	@Column(name = "departmentid")
	private int departmentid;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userlocationid" )
	@NotFound(action=NotFoundAction.IGNORE)
	private Apilocationsqlobj userslocation;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userdeptid")
	@NotFound(action=NotFoundAction.IGNORE)	
	private Apidepartments userdept; 
	
	public int getIdkey() {
		return idkey;
	}
	public int getUserID() {
		return userID;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
	}
	public String getJobtitle() {
		return jobtitle;
	}
	public int getLocationid() {
		return locationid;
	}
	public String getPhone() {
		return phone;
	}
	public void setIdkey(int idkey) {
		this.idkey = idkey;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Apilocationsqlobj getUserslocation() {
		return userslocation;
	}
	public void setUserslocation(Apilocationsqlobj userslocation) {
		this.userslocation = userslocation;
	}
	
	
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}
	public Apidepartments getUserdept() {
		return userdept;
	}
	public void setUserdept(Apidepartments userdept) {
		this.userdept = userdept;
	}
}
