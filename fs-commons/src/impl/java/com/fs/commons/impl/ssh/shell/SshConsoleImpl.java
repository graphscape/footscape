/**
 * Nov 29, 2011
 */
package com.fs.commons.impl.ssh.shell;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class SshConsoleImpl implements SshConsoleI {

	private static final Logger OLOG = LoggerFactory
			.getLogger("utt.impl.ssh.console.outputstram");

	private int windowSize = 1024 * 1024;// to big enough, otherwise will dead
											// easily.

	private ConsoleInputStream input;//

	private ConsoleOutputStream output;

	private long lastReaded = -1;
	//
	private BlockingQueue<StringBuffer> queue = new LinkedBlockingQueue<StringBuffer>();

	private StringBuffer current;

	private char[] cbuf = new char[windowSize];

	private static Logger LOG = LoggerFactory.getLogger(SshConsoleImpl.class);

	private ExecutorService executorService;

	private boolean open;

	public SshConsoleImpl(Charset cs) {

		//
		this.output = new ConsoleOutputStream(cs);
		this.input = new ConsoleInputStream();
		this.current = new StringBuffer();
		this.executorService = Executors.newFixedThreadPool(1);

	}

	@Override
	public void open() {
		this.open = true;
		this.executorService.submit(new Runnable() {

			@Override
			public void run() {
				SshConsoleImpl.this.runInternal();
			}
		});
	}

	private void runInternal() {
		try {
			Reader r = this.output.getReader();

			while (this.open) {
				int len = r.read(cbuf, 0, cbuf.length);
				StringBuffer bf = new StringBuffer().append(cbuf, 0, len);

				this.queue.put(bf);

				if (OLOG.isInfoEnabled()) {
					OLOG.info(bf.toString());
				}

			}
		} catch (Throwable t) {
			LOG.error("", t);// TODO
		}
	}

	@Override
	public InputStream getInputStream() {
		return this.input;
	}

	@Override
	public Future<String> waitFor(final String msg) {
		FutureTask<String> rt = new FutureTask<String>(new Callable<String>() {

			@Override
			public String call() throws Exception {

				StringBuffer sb = SshConsoleImpl.this.getOutputUntil(msg);

				return sb.toString();

			}
		});
		rt.run();
		return rt;

	}

	@Override
	public OutputStream getOutputStream() {
		return this.output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utt.impl.ssh.HOutputI#getOutput()
	 */
	@Override
	public StringBuffer getOutput() {
		return this.getOutput(true);
	}

	private void assertOpen() {
		if (!this.open) {
			throw new FsException("call open first.");
		}
	}

	public StringBuffer getOutput(boolean clear) {
		return this.getOutput(clear, Integer.MAX_VALUE);
	}

	public StringBuffer getOutput(boolean clear, int timeoutS) {
		this.assertOpen();

		StringBuffer rt = this.current;

		boolean useTimeOut = rt.length() == 0;

		while (true) {
			StringBuffer sb = null;
			if (useTimeOut) {
				try {
					sb = this.queue.poll(timeoutS, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					continue;
				}
				useTimeOut = false;
			} else {
				sb = this.queue.poll();
			}

			if (sb == null) {
				break;
			}

			rt.append(sb);

		}

		if (clear) {
			this.current = new StringBuffer();
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utt.impl.ssh.HOutputI#clear()
	 */
	@Override
	public StringBuffer clear() {

		return this.getOutput(true, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utt.impl.ssh.HOutputI#getOutputUntil(java.lang.String, long)
	 */
	public StringBuffer getOutputUntil(String message) {

		for (;;) {
			StringBuffer all = this.getOutput(false, 1);// not clear,
			String allS = all.toString();
			int idx = allS.indexOf(message);

			if (idx < 0) {
				continue;// coninute to wait the message.
			}
			// got at leat one message.
			idx += message.length();

			String first = allS.substring(0, idx);
			String second = allS.substring(idx, allS.length());
			// push back the remain ,only return that before the first message.
			this.current = new StringBuffer();// clear
			this.current.append(second);// push back.
			// return first
			StringBuffer rt = new StringBuffer();
			rt.append(first);
			return rt;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utt.impl.ssh.HConsoleI#sendln(java.lang.String)
	 */
	@Override
	public void sendln(String line) {
		this.input.sendln(line);

	}

	@Override
	public void close() {
		this.open = false;
	}

}
