package org.springside.modules.google.base;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author thinkme
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2017/07/09 下午10:37
 */
public class ThrowablesTest {

	public void test() throws IOException, SQLException {
		try {
//			someMethodThatCouldThrowAnything();
		} catch (Exception e) {
//			handle(e);
		} catch (Throwable t) {
			Throwables.propagateIfInstanceOf(t, IOException.class);
			Throwables.propagateIfInstanceOf(t, SQLException.class);
			Throwables.propagateIfPossible(t,RuntimeException.class);
			throw Throwables.propagate(t);
		}

	}
}
