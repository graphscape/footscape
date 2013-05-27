/**
 * Jul 30, 2012
 */
package com.fs.commons.api.exec;

import java.io.File;

/**
 * @author wu
 * @deprecated
 */
public interface ExecutorI {
	public interface FactoryI {
		public ExecutorI create(String cmd);
	}

	public void setWorkingDirectory(File wd);

	public ExecutorI addArgument(String arg);

	public int run();

}
