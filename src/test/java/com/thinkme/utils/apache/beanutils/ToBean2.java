package com.thinkme.utils.apache.beanutils;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/16 下午9:56
 */
public class ToBean2 {
	private String name;
	private int age;
	private String address;


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


	@Override
	public String toString() {
		return "ToBean{" +
				"name='" + name + '\'' +
				", age=" + age +
				", address='" + address + '\'' +
				'}';
	}
}
