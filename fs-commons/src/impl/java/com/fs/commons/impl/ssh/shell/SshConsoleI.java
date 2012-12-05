/**
 * Nov 29, 2011
 */
package com.fs.commons.impl.ssh.shell;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;

/**
 * @author wuzhen
 * 
 */
public interface SshConsoleI {

	public OutputStream getOutputStream();

	public InputStream getInputStream();

	public StringBuffer getOutput();

	public StringBuffer clear();

	public StringBuffer getOutputUntil(String message);

	public Future<String> waitFor(String message);

	public void sendln(String cmdL);

	public void open();

	public void close();

}
