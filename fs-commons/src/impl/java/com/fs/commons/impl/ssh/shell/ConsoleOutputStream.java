/**
 * Dec 7, 2011
 */
package com.fs.commons.impl.ssh.shell;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class ConsoleOutputStream extends OutputStream {

	//
	private PipedOutputStream pos;

	private PipedInputStream pis;

	private Reader reader;// from output

	private long lastWrited = -1;

	public ConsoleOutputStream(Charset cs) {
		this.pos = new PipedOutputStream();
		this.pis = new PipedInputStream();
		try {
			this.pis.connect(pos);
		} catch (IOException e) {
			throw new FsException(e);
		}
		this.reader = new InputStreamReader(pis, cs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {

		this.pos.write(b);
		this.lastWrited = System.currentTimeMillis();
	}

	/**
	 * @return the more
	 */
	public long getLastWrited() {
		return this.lastWrited;
	}

	/**
	 * @return the reader
	 */
	public Reader getReader() {
		return reader;
	}

}
