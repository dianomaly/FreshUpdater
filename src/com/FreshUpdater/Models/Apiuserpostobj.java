package com.FreshUpdater.Models;

public class Apiuserpostobj {
private String first_name;
private String last_name;
private String job_title;
private String primary_email;
private String work_phone_number;
private int[] department_ids;
private int reporting_manager_id;
private int location_id;
public String getFirst_name() {
	return first_name;
}
public String getLast_name() {
	return last_name;
}
public String getJob_title() {
	return job_title;
}
public String getPrimary_email() {
	return primary_email;
}
public String getWork_phone_number() {
	return work_phone_number;
}
public int[] getDepartment_ids() {
	return department_ids;
}
public int getReporting_manager_id() {
	return reporting_manager_id;
}
public int getLocation_id() {
	return location_id;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}
public void setJob_title(String job_title) {
	this.job_title = job_title;
}
public void setPrimary_email(String primary_email) {
	this.primary_email = primary_email;
}
public void setWork_phone_number(String work_phone_number) {
	this.work_phone_number = work_phone_number;
}
public void setDepartment_ids(int[] department_ids) {
	this.department_ids = department_ids;
}
public void setReporting_manager_id(int reporting_manager_id) {
	this.reporting_manager_id = reporting_manager_id;
}
public void setLocation_id(int location_id) {
	this.location_id = location_id;
}


}
