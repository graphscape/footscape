/**
 * Jul 30, 2012
 */
package com.fs.commons.impl.exec;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

import com.fs.commons.api.exec.ExecutorI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ExecutorImpl implements ExecutorI {

	private CommandLine cmdl;
	private DefaultExecutor executor;

	/** */
	public ExecutorImpl(String cmd) {
		this.cmdl = new CommandLine(cmd);
		executor = new DefaultExecutor();

	}

	/* */
	@Override
	public void setWorkingDirectory(File wd) {
		executor.setWorkingDirectory(wd);

	}

	/* */
	@Override
	public ExecutorI addArgument(String arg) {
		this.cmdl.addArgument(arg);
		return this;

	}

	/* */
	@Override
	public int run() {

		ExecuteWatchdog watchdog = new ExecuteWatchdog(
				ExecuteWatchdog.INFINITE_TIMEOUT);
		// watchdog.
		executor.setWatchdog(watchdog);
		executor.setExitValue(0);
		try {
			int rt = executor.execute(this.cmdl);
			return rt;
		} catch (ExecuteException e) {
			throw new FsException(e);
		} catch (IOException e) {
			throw new FsException(e);
		}

	}

}
