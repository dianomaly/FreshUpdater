package com.FreshUpdater.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "apiusertable")
public class freshapiuserobj {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "ID")
	private Long idkey;
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
	public Long getIdkey() {
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
	public void setIdkey(Long idkey) {
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
	

}
