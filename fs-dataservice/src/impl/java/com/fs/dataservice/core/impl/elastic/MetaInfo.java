/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.Map;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class MetaInfo {

	private String version;

	public MetaInfo(String version) {

		this.version = version;

	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/*
	 * Nov 1, 2012
	 */
	@Override
	public String toString() {
		//
		return "version:" + this.version;
	}

}
