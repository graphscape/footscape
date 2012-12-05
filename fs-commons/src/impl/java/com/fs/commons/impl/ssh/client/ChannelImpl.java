/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sshd.ClientChannel;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.mask.Mask;
import com.fs.commons.api.ssh.client.SshClientI.ChannelI;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author wu
 * 
 */
public class ChannelImpl implements ChannelI {

	private ClientChannel c;

	private static BiMap<Mask, Integer> MASKS = HashBiMap.create();
	static {
		MASKS.put(ChannelI.CLOSED, ClientChannel.CLOSED);
	}

	public ChannelImpl(ClientChannel c) {
		this.c = c;
	}

	/* */
	@Override
	public void setIn(InputStream is) {
		this.c.setIn(is);
	}

	/* */
	@Override
	public void setOut(OutputStream out) {
		this.c.setOut(out);
	}

	/* */
	@Override
	public void setErr(OutputStream err) {
		this.c.setErr(err);
	}

	/* */
	@Override
	public void open() {
		try {
			this.c.open();
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	/* */
	@Override
	public void close() {
		this.c.close(true);//
	}

	/* */
	@Override
	public void waitFor(Mask status) {
		this.waitFor(status, 0);
	}

	/* */
	@Override
	public void waitFor(Mask ms, int i) {

		int mask = MASKS.get(ms);
		this.c.waitFor(mask, 0);
	}
}
