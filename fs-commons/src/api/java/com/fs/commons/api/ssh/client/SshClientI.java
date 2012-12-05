/**
 * Jul 8, 2012
 */
package com.fs.commons.api.ssh.client;

import java.io.InputStream;
import java.io.OutputStream;

import com.fs.commons.api.mask.Mask;

/**
 * @author wu
 * 
 */
public interface SshClientI {
	public static interface FactoryI {
		public SshClientI create();

	}

	public static interface SessionI {

		public static final Mask TIMEOUT = Mask.valueOf(0x0001);

		public static final Mask CLOSED = Mask.valueOf(0x0002);

		public static final Mask WAIT_AUTH = Mask.valueOf(0x0004);

		public static final Mask AUTHED = Mask.valueOf(0x0008);

		public ChannelI createChannel(String name);

		public void authPassword(String user, String password);

		public Mask waitFor(Mask i, long timeout);

		public void close();

	}

	public static interface ChannelI {
		public String CHANNEL_EXEC = "exec";
		public String CHANNEL_SHELL = "shell";
		public String CHANNEL_SUBSYSTEM = "subsystem";

		public Mask TIMEOUT = Mask.valueOf(0x0001);
		public Mask CLOSED = Mask.valueOf(0x0002);
		public Mask STDOUT_DATA = Mask.valueOf(0x0004);
		public Mask STDERR_DATA = Mask.valueOf(0x0008);
		public Mask EOF = Mask.valueOf(0x0010);
		public Mask EXIT_STATUS = Mask.valueOf(0x0020);
		public Mask EXIT_SIGNAL = Mask.valueOf(0x0040);
		public Mask OPENED = Mask.valueOf(0x0080);

		public void setIn(InputStream is);

		public void setOut(OutputStream out);

		public void setErr(OutputStream out);

		public void open();

		public void close();

		public void waitFor(Mask status);

		/**
		 * @param closed2
		 * @param i
		 */
		public void waitFor(Mask closed2, int i);

	}

	/**
	 * 
	 */
	public void start();

	public SessionI connect(String host, int port);

	public void stop();

}
