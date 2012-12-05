/**
 Jul 8, 2012
 **/
package com.fs.commons.impl.test.cases;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.sshd.common.util.NoCloseInputStream;
import org.apache.sshd.common.util.NoCloseOutputStream;

import com.fs.commons.api.mask.Mask;
import com.fs.commons.api.ssh.client.SshClientI;
import com.fs.commons.api.ssh.client.SshClientI.ChannelI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 **/
public class SshClientTest extends TestBase {

	public void test() {

	}

	public void xtestSshClient() throws Exception {
		String host = "localhost";
		int port = 22;
		SshClientI.FactoryI scf = this.container
				.find(SshClientI.FactoryI.class);
		SshClientI client = scf.create();
		client.start();
		try {
			SshClientI.SessionI session = client.connect(host, port);

			Mask ret = SshClientI.SessionI.WAIT_AUTH;
			while (!(ret.and(SshClientI.SessionI.WAIT_AUTH).isZero())) {
				System.out.print("Password:");
				BufferedReader r = new BufferedReader(new InputStreamReader(
						System.in));
				String password = r.readLine();

				session.authPassword("wu", password);
				ret = session.waitFor(
						SshClientI.SessionI.WAIT_AUTH.or(
								SshClientI.SessionI.CLOSED).or(
								SshClientI.SessionI.AUTHED), 0);
			}
			if (!(ret.and(SshClientI.SessionI.CLOSED)).isZero()) {
			
				System.err.println("error");

			} else {
				SshClientI.ChannelI channel = session.createChannel("shell");
				channel.setIn(new NoCloseInputStream(System.in));
				channel.setOut(new NoCloseOutputStream(System.out));
				channel.setErr(new NoCloseOutputStream(System.err));
				channel.open();
				channel.waitFor(ChannelI.CLOSED, 0);
				session.close();
			}
		} finally {
			client.stop();
		}
	}
}
