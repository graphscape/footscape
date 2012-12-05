/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.client;

import java.io.IOException;

import org.apache.sshd.ClientChannel;
import org.apache.sshd.ClientSession;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mask.Mask;
import com.fs.commons.api.ssh.client.SshClientI.ChannelI;
import com.fs.commons.api.ssh.client.SshClientI.SessionI;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author wu
 * 
 */
public class SessionImpl implements SessionI {

	private static BiMap<Mask, Integer> MASKS = HashBiMap.create();

	static {

		MASKS.put(SessionI.AUTHED, ClientSession.AUTHED);

		MASKS.put(SessionI.CLOSED, ClientSession.CLOSED);
		MASKS.put(SessionI.TIMEOUT, ClientSession.TIMEOUT);
		MASKS.put(SessionI.WAIT_AUTH, ClientSession.WAIT_AUTH);
	}
	private ClientSession cs;

	public SessionImpl(ClientSession cs) {
		this.cs = cs;
	}

	/* */
	@Override
	public ChannelI createChannel(String name) {
		ClientChannel cc;
		try {
			cc = this.cs.createChannel(name);
		} catch (Exception e) {
			throw new FsException(e);
		}
		return new ChannelImpl(cc);

	}

	/* */
	@Override
	public void authPassword(String user, String password) {
		try {
			this.cs.authPassword(user, password);
		} catch (IOException e) {
			throw FsException.valueOf(e);
		}
	}

	/* */
	@Override
	public Mask waitFor(Mask i, long timeout) {
		int mask = i.value();
		int rtI = this.cs.waitFor(mask, timeout);
		Mask rt = Mask.valueOf(rtI);//
		return rt;

	}

	/* */
	@Override
	public void close() {
		this.cs.close(true);
	}

}
