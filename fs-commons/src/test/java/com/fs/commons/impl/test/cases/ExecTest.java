/**
 * Jul 31, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.exec.ExecutorI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ExecTest extends TestBase {

	public void testSP() {
		for (Object ko : System.getProperties().keySet()) {
			String value = System.getProperty((String) ko);
			System.out.println(ko + "=" + value);
		}
	}

	public void testExec() {
		ExecutorI.FactoryI ef = this.container.find(ExecutorI.FactoryI.class,
				true);
		ExecutorI e = ef.create("ls");
		e.addArgument("-l");
		int exit = e.run();
		System.out.println(exit);
	}

	public void testExecInSSH() {
		ExecutorI.FactoryI ef = this.container.find(ExecutorI.FactoryI.class,
				true);
		ExecutorI e = ef.create("ssh");

		String userName = System.getProperty("user.name");
		String host = "localhost";//System.getProperty("host");
		e.addArgument(userName + "@" + host);

		e.addArgument("ls");
		e.addArgument("-l");
		int exit = e.run();
		System.out.println(exit);
	}

}
