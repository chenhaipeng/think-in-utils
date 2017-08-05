package com.thinkme.utils.apache.beanutils.dybean;

import com.thinkme.utils.json.JsonUtils;
import org.apache.commons.beanutils.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/08/05 下午6:00
 */
public class DynaBeanTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//定义动态属性
		DynaProperty[] props = new DynaProperty[]{
				new DynaProperty("username", String.class),
				new DynaProperty("address", java.util.Map.class)
		};
		//动态类
		BasicDynaClass dynaClass = new BasicDynaClass("person", null, props);
		//动态bean
		DynaBean person = dynaClass.newInstance();
		person.set("username", "jhlishero");//设置值
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("key1", "value1");
		maps.put("key2", "value2");
		person.set("address", maps);//设置值
		person.set("address", "key3", "value3");//第二种方法设置map中的值

		System.out.println(person.get("username"));//获取字符串值
		System.out.println(person.get("address", "key1"));//获取map中值
		System.out.println(person.get("address", "key2"));
		System.out.println(person.get("address", "key3"));
		//使用PropertyUtils工具获取属性值
		System.out.println(PropertyUtils.getSimpleProperty(person, "username"));
		System.out.println(person.toString());
	}

	@Test
	public void testMap() {
		LazyDynaMap dynaBean1 = new LazyDynaMap();

		dynaBean1.set("foo", "bar");                  // simple

		dynaBean1.set("customer", "title", "Mr");        // mapped

		dynaBean1.set("address", 0, "address1");         // indexed

		System.out.println(dynaBean1.get("address", 0));


		Map myMap = dynaBean1.getMap();           // retrieve the Map

		System.out.println(myMap.toString());

	}

	@Test
	public void test3() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		LazyDynaBean hh = new LazyDynaBean();
		hh.set("country", "中国");
		hh.set("city", "北京");
		hh.set("postCode", "100120");
		hh.set("addr", "aaaaaaa");

		LazyDynaBean bb = new LazyDynaBean();
		bb.set("phone", "home", "11011011");
		bb.set("phone", "office", "111111");
		bb.set("email", "sh@126.com");
		bb.set("address", 0, hh);
		bb.set("birthDate", new GregorianCalendar(1990, 3, 29).getTime());

		LazyDynaBean tt = new LazyDynaBean();
		tt.set("userId", new Long(8888888));
		tt.set("gggg", "施杨");
		tt.set("password", "sgsgsgsg");
		tt.set("dddd", bb);

		System.out.println(BeanUtils.getProperty(tt, "gggg"));
		System.out.println(BeanUtils.getProperty(tt, "dddd.birthDate"));
		System.out.println(BeanUtils.getProperty(tt,
				"dddd.address[0].addr"));
		System.out
				.println(BeanUtils.getProperty(tt, "dddd.phone(office)"));
		System.out.println(JsonUtils.object2String(hh.getMap()));

	}
}





