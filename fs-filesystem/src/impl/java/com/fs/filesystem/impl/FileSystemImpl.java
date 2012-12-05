/**
 * Jul 7, 2012
 */
package com.fs.filesystem.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.HdfsConfiguration;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.filesystem.api.FileSystemI;

/**
 * @author wu
 * 
 */
public class FileSystemImpl extends ConfigurableSupport implements FileSystemI {

	private FileSystem fileSystem;

	public FileSystemImpl() {

	}
	@Override
	public void delete(String path, boolean recu) {
		try {
			this.fileSystem.delete(new Path(path), recu);
		} catch (IOException e) {
			throw FsException.valueOf(e);
		}//
	}

	/* */
	@Override
	public List<String> ls(String path) {
		List<String> rt = new ArrayList<String>();
		try {
			RemoteIterator<LocatedFileStatus> it = this.fileSystem.listFiles(
					new Path(path), false);
			for (; it.hasNext();) {
				LocatedFileStatus fs = it.next();
				rt.add(fs.getPath().toString());//
			}
		} catch (FileNotFoundException e) {
			throw FsException.valueOf(e);
		} catch (IOException e) {
			throw FsException.valueOf(e);
		}

		return rt;

	}

	/* */
	@Override
	public DataOutputStream create(String path) {

		Path filenamePath = new Path(path);
		try {
			FSDataOutputStream out = this.fileSystem.create(filenamePath);
			return out;
		} catch (IOException e) {
			throw new FsException(e);
		}

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		Configuration conf = new HdfsConfiguration();
		conf.set(FileSystem.FS_DEFAULT_NAME_KEY, "hdfs://localhost:9000");//
		try {
			fileSystem = FileSystem.get(conf);
		} catch (IOException e) {
			throw new FsException(e);
		}

	}

	/* */
	@Override
	public DataInputStream open(String path) {
		Path filenamePath = new Path(path);
		try {
			FSDataInputStream out = this.fileSystem.open(filenamePath);
			return out;
		} catch (IOException e) {
			throw new FsException(e);
		}

	}
}
