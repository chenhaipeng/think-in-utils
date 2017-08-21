package com.thinkme.utils.apache.beanutils.dybean;

import java.util.Map;

public class Employee {

	private Map address;

	private Employee[] subordinate;

	private String firstName;

	private String lastName;

	public Map getAddress() {
		return address;
	}

	public void setAddress(Map address) {
		this.address = address;
	}

	public Employee[] getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(Employee[] subordinate) {
		this.subordinate = subordinate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}