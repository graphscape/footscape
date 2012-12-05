/**
 * Jul 7, 2012
 */
package com.fs.filesystem.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wu
 * 
 */
public class FileSystemTestSPI extends SPISupport {

	/** */
	public FileSystemTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		HdfsStarter hs = new HdfsStarter();
		ac.activitor().name("HDFS_STARTER").cfgId(HdfsStarter.class.getName())
				.object(hs).active();

	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
