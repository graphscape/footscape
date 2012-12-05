/**
 * Jul 15, 2012
 */
package com.fs.integrated.daemon;

import java.io.PrintStream;
import java.util.Date;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import com.fs.commons.api.SPIManagerI;

/**
 * @author wu
 * 
 */
public class Main implements Daemon {
	private static final int INFO = 0;
	private static final int ERROR = 1;

	private SPIManagerI spiManager;
	
	private DaemonContext context;

	/* */
	@Override
	public void init(DaemonContext context) throws DaemonInitException,
			Exception {
		this.context = context;
		String[] args = this.context.getArguments();
		
		this.log(INFO, "init,args:" + args);
	}

	protected void error(Throwable t) {
		this.error("", t);
	}

	protected void info(String msg) {
		this.log(INFO, msg);
	}

	protected void error(String obj, Throwable t) {
		this.log(ERROR, obj, t);
	}

	protected PrintStream getLevelStream(int level) {
		switch (level) {
		case ERROR:
			return System.err;
		default:
			return System.out;
		}
	}

	protected String getLevelName(int level) {
		switch (level) {
		case ERROR:
			return "ERROR";
		default:
			return "INFO";
		}
	}

	protected void log(int level, String msg) {
		this.log(level, msg, null);
	}

	protected void log(int level, String msg, Throwable t) {

		PrintStream ps = this.getLevelStream(level);
		ps.println(new Date() + ":" + this.getLevelName(level) + ":" + msg);
		if (t != null) {
			t.printStackTrace(ps);//

		}
	}

	/* */
	@Override
	public void start() throws Exception {
		this.info("start");
		try {
			this.doStart();
		} catch (Throwable t) {
			this.error(t);
		}
	}

	protected void doStart() {
		String res = "/fs-spim.properties";
		this.spiManager = SPIManagerI.FACTORY.get();
		this.spiManager.load(res);

	}

	/* */
	@Override
	public void stop() throws Exception {
		this.info("stop");
	}

	/* */
	@Override
	public void destroy() {
		this.info("destroy");
	}

}
