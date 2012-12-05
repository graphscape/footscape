/**
 * Jul 7, 2012
 */
package com.fs.filesystem.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

/**
 * @author wu
 * 
 */
public interface FileSystemI {

	public void delete(String path, boolean recu);

	public List<String> ls(String path);

	public DataOutputStream create(String path);

	public DataInputStream open(String path);
}
