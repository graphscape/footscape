/**
 Jul 8, 2012
 **/
package com.fs.commons.impl.test.cases;

import java.util.concurrent.Future;

import com.fs.commons.api.ssh.shell.SshShellI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 **/
public class SshShellTest extends TestBase {

	public void test() {

	}

	public void testSshShell() throws Exception {
		String host = "localhost";
		int port = 22;

		SshShellI.FactoryI ssf = this.container.find(SshShellI.FactoryI.class);
		SshShellI shell = ssf.open(host, port, "wu", "wu");
		String pmp = "wu@thinkpad:~$";
		Future<String> pf = shell.waitFor(pmp);
		String msg = pf.get();
		System.out.println("got:" + msg);
		shell.send("ls");
		String files = shell.waitFor(pmp).get();
		System.out.println("ls:" + files);
	}
}
