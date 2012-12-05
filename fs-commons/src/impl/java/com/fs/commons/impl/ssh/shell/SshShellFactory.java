/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mask.Mask;
import com.fs.commons.api.ssh.client.SshClientI;
import com.fs.commons.api.ssh.shell.SshShellI;

/**
 * @author wu
 * 
 */
public class SshShellFactory extends ConfigurableSupport implements
		SshShellI.FactoryI {

	// TODO annotation based injection.

	private SshClientI.FactoryI sshClientFactory;

	/* */
	@Override
	public SshShellI open(String host, int port, String user, String pass) {
		SshClientI client = this.sshClientFactory.create();
		client.start();
		SshClientI.SessionI session = client.connect(host, port);

		Mask ret = SshClientI.SessionI.WAIT_AUTH;
		while (!(ret.and(SshClientI.SessionI.WAIT_AUTH).isZero())) {

			session.authPassword(user, pass);
			ret = session.waitFor(
					SshClientI.SessionI.WAIT_AUTH
							.or(SshClientI.SessionI.CLOSED).or(
									SshClientI.SessionI.AUTHED), 0);
		}
		if (!(ret.and(SshClientI.SessionI.CLOSED)).isZero()) {
			throw new FsException("error");
		}
		SshShellI rt = new SshShellImpl(client, session);
		return rt;

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		this.sshClientFactory = ac.getContainer().find(
				SshClientI.FactoryI.class);

	}

}
