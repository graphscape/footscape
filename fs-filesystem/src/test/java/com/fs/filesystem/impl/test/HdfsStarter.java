/**
 * Jul 8, 2012
 */
package com.fs.filesystem.impl.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.ssh.shell.SshShellI;
import com.fs.filesystem.api.FileSystemI;

/**
 * @author wu
 * 
 */
public class HdfsStarter extends ConfigurableSupport{
	
	private static final Logger LOG = LoggerFactory.getLogger(HdfsStarter.class);
	private boolean ignore = true;
	/* */
	@Override
	public void active(ActiveContext ac) {
		
		super.active(ac);
		
		boolean ok = this.isFileSystemAvailable();
		
		if(!ok){
			LOG.warn("file system not available,trying to ssh login and start it");
			this.startHdfs();
		}
	}

	public void startHdfs() {
		
		SshShellI.FactoryI ssf = this.container.find(SshShellI.FactoryI.class,true);
		
		String ssh = this.config.getProperty("fs.ssh.hadoop",true);
		//user:pass@host
		String[] sshs = ssh.split(":");
		String user = sshs[0];
		sshs = sshs[1].split("@");
		String pass = sshs[0];
		String host = sshs[1];
		
		String hdhome = this.config.getProperty("fs.hadoop.home",true);
		
		SshShellI shell = ssf.open(host, 22, user, pass);
		String cmd = hdhome+"/sbin/hadoop-daemon.sh start namenode";//start namenode
		LOG.info("starting namenode... by cmd:"+cmd+"");
		
		shell.send(cmd);
		shell.waitFor("starting namenode,");
		String cmd2 = hdhome+"/sbin/hadoop-daemon.sh start datanode";//start namenode
		LOG.info("starting datanode... by cmd:"+cmd+"");
		
		shell.send(cmd2);
		
		shell.waitFor("starting datanode,");
		
		LOG.info("fs start done");
		shell.close();
		
	}	
	
	private boolean isFileSystemAvailable(){

		FileSystemI fs = this.container.find(FileSystemI.class);
		String url = this.config.getProperty("filesystem.url");
		try{
			List<String> ls = fs.ls("/");
			
		}catch(Throwable t){
			return false;
		}
		return true;
	}
}
