package com.thinkme.utils.apache.beanutils;

import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import java.util.Date;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/16 下午9:25
 */
public class BeanUtilsTest {

	@Test
	public void test(){
		FromBean fb = new FromBean();
		fb.setAddress("北京市朝阳区大屯路");
		fb.setAge(20);
		fb.setMoney(30000.111);
		fb.setIdno("110330219879208733");
		fb.setDate(new Date());
//		fb.setName("测试");

		ToBean toBean = new ToBean();
		toBean.setName("xxx");
		BeanCopier copier = BeanCopier.create(FromBean.class,ToBean.class,false);
		copier.copy(fb, toBean, null);
		System.out.println(ToStringBuilder.reflectionToString(toBean));
	}

}
