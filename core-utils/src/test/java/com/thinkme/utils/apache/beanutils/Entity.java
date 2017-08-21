package com.thinkme.utils.apache.beanutils;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/08/05 下午3:09
 */
public class Entity {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entity() {
	}

	public Entity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void haha(){
		System.out.println("method haha");
	}

	public void sayHello(String name){
		System.out.println("method say hello :"+ name);
	}

	public void countAges(int age1 , int age2){
		System.out.println("count ages"+age1+age2);
	}
}
