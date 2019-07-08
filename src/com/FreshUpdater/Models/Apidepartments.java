package com.FreshUpdater.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "apidepartments")
public class Apidepartments {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id")
	private int id;
	@Column(name = "deptapiid")
	private int deptapiid;
	@Column(name = "deptname")
	private String deptname;
	public Apidepartments() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public int getDeptapiid() {
		return deptapiid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDeptapiid(int deptapiid) {
		this.deptapiid = deptapiid;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
}
