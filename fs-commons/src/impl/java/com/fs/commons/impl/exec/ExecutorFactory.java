/**
 * Jul 30, 2012
 */
package com.fs.commons.impl.exec;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.exec.ExecutorI;

/**
 * @author wu
 * 
 */
public class ExecutorFactory extends ConfigurableSupport implements ExecutorI.FactoryI {

	/* */
	@Override
	public ExecutorI create(String cmd) {

		return new ExecutorImpl(cmd);

	}

}
