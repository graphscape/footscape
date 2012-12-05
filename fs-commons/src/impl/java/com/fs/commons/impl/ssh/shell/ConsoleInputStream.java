/**
 * Nov 29, 2011
 */
package com.fs.commons.impl.ssh.shell;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuzhen
 * 
 */
public class ConsoleInputStream extends InputStream {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConsoleInputStream.class);

	private static final Logger ILOG = LoggerFactory
			.getLogger("utt.impl.ssh.console.inputstram");

	private BlockingQueue<ByteArrayInputStream> queue = new LinkedBlockingQueue<ByteArrayInputStream>();

	private ByteArrayInputStream current;

	@Override
	public int available() throws IOException {
		int rt = this.current == null ? 0 : this.current.available();
		return rt;
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {

		int a = this.getCurrent().available();

		return super.read(b, off, a);
	}

	private ByteArrayInputStream getCurrent() {

		if (this.current != null && this.current.available() > 0) {
			return this.current;
		}
		this.current = null;

		while (true) {
			try {
				this.current = this.queue.take();
			} catch (InterruptedException e) {
				continue;
			}
			break;
		}

		return this.current;
		// set

	}

	@Override
	public int read() throws IOException {

		int rt = this.getCurrent().read();

		// LOG.debug("R:" + ((char) rt) + rt);
		return rt;

	}

	public void sendln(String line) {
		line += "\r\n";// NOTE
		ByteArrayInputStream is = new ByteArrayInputStream(line.getBytes());

		this.queue.add(is);

		if (ILOG.isInfoEnabled()) {
			ILOG.info(line);//
		}
	}

}
