/**
 * Jul 8, 2012
 */
package com.fs.commons.api.ssh.shell;

import java.util.concurrent.Future;

/**
 * @author wu
 * 
 */
public interface SshShellI {

	public static interface FactoryI {

		public SshShellI open(String host, int port, String user, String pass);

	}

	public void send(String cmd);

	public Future<String> waitFor(String msg);

	public void close();

}
