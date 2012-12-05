/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.client;

import org.apache.sshd.ClientSession;
import org.apache.sshd.SshClient;
import org.apache.sshd.client.future.ConnectFuture;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.ssh.client.SshClientI;

/**
 * @author wu
 * 
 */
public class SshClientImpl implements SshClientI {

	private SshClient client;

	public SshClientImpl() {
		this.client = SshClient.setUpDefaultClient();
	}

	/* */
	@Override
	public SessionI connect(String host, int port) {
		ConnectFuture cf;
		try {
			cf = this.client.connect(host, port);
		} catch (Exception e) {
			throw FsException.valueOf(e);
		}
		ClientSession s = cf.awaitUninterruptibly().getSession();
		SessionI rt = new SessionImpl(s);
		return rt;

	}

	/* */
	@Override
	public void start() {
		this.client.start();
	}

	/* */
	@Override
	public void stop() {
		this.client.stop();
	}

}
