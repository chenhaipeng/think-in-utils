package com.thinkme.utils.apache.beanutils;

import java.util.Date;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/16 下午9:56
 */
public class ToBean {
	private String name;
	private int age;
	private String address;
	private String idno;
	private double money;

	private Date date;

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ToBean{" +
				"name='" + name + '\'' +
				", age=" + age +
				", address='" + address + '\'' +
				", idno='" + idno + '\'' +
				", money=" + money +
				'}';
	}
}
