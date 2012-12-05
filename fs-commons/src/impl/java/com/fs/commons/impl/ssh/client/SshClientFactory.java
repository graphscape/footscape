/**
 * Jul 8, 2012
 */
package com.fs.commons.impl.ssh.client;

import com.fs.commons.api.ssh.client.SshClientI;

/**
 * @author wu
 * 
 */

public class SshClientFactory implements SshClientI.FactoryI {

	/* */
	@Override
	public SshClientI create() {

		return new SshClientImpl();

	}

}
