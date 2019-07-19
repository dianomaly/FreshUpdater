package com.FreshUpdate.HiberModels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "apiagentstb")
public class Apiagent {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id")
	private int id;
	@Column(name = "apiid")
	private int apiid;
	@Column(name = "first_name")
	private String first_name;
	@Column(name = "last_name")
	private String last_name;
	@Column(name = "email")
	private String email;
	@Column(name = "job_title")
	private String job_title;
	@Column(name = "work_phone_number")
	private String work_phone_number;
	@Column(name = "location_id")
	private int location_id;
	@Column(name = "reporting_manager_id")
	private int reporting_manager_id;
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="apilocationid")
	private Apilocationsqlobj apilocation;

	public int getApiid() {
		return apiid;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getEmail() {
		return email;
	}

	public String getJob_title() {
		return job_title;
	}

	public String getWork_phone_number() {
		return work_phone_number;
	}

	public int getLocation_id() {
		return location_id;
	}

	public int getReporting_manager_id() {
		return reporting_manager_id;
	}

	public void setApiid(int apiid) {
		this.apiid = apiid;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public void setWork_phone_number(String work_phone_number) {
		this.work_phone_number = work_phone_number;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public void setReporting_manager_id(int reporting_manager_id) {
		this.reporting_manager_id = reporting_manager_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Apilocationsqlobj getApilocation() {
		return apilocation;
	}

	public void setApilocation(Apilocationsqlobj apilocation) {
		this.apilocation = apilocation;
	}
}
