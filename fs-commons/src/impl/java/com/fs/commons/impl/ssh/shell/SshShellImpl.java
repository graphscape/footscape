/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.shell;

import java.nio.charset.Charset;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ssh.client.SshClientI;
import com.fs.commons.api.ssh.client.SshClientI.SessionI;
import com.fs.commons.api.ssh.shell.SshShellI;

/**
 * @author wu
 * 
 */
public class SshShellImpl implements SshShellI {
	private static final Logger LOG = LoggerFactory
			.getLogger(SshShellImpl.class);

	private SshClientI.SessionI session;
	private SshClientI client;
	private SshClientI.ChannelI channel;

	private SshConsoleI console;

	/** */
	public SshShellImpl(SshClientI client, SessionI session) {
		this.session = session;
		this.client = client;
		this.channel = this.session.createChannel("shell");
		this.console = new SshConsoleImpl(Charset.forName("iso-8859-1"));
		this.channel.setIn(this.console.getInputStream());
		this.channel.setOut(this.console.getOutputStream());
		this.channel.setErr(this.console.getOutputStream());//
		//
		this.console.open();
		this.channel.open();
	}

	/* */
	@Override
	public void send(String cmdL) {
		this.console.sendln(cmdL);
	}

	@Override
	public Future<String> waitFor(String msg) {
		return this.console.waitFor(msg);
	}

	/* */
	@Override
	public void close() {
		this.console.close();
		this.channel.close();
		this.session.close();
		this.client.stop();

	}

}
