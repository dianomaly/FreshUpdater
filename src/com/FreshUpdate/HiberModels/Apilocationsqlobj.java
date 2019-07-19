package com.FreshUpdate.HiberModels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "apilocations")
public class Apilocationsqlobj {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	private int id;
	@Column(name = "apiid")
	private int apiid;
	@Column(name = "name")
	private String name;
	@Column(name = "parentlid")
	private int parentlid;
	@Column(name = "line1")
	private String line1;
	@Column(name = "line2")
	private String line2;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "zipcode")
	private String zipcode;
		public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getParentlid() {
		return parentlid;
	}
	public String getLine1() {
		return line1;
	}
	public String getLine2() {
		return line2;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getCountry() {
		return country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParentlid(int parentlid) {
		this.parentlid = parentlid;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public int getApiid() {
		return apiid;
	}
	public void setApiid(int apiid) {
		this.apiid = apiid;
	}
	
	
	
}
