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

	private String nodeIndex;

	public MetaInfo(Map<String, Object> src) {

		this.nodeIndex = (String) src.get("nodeIndex");
		this.version = (String) src.get("version");

	}

	public static MetaInfo load(Client client) {
		GetRequestBuilder gr = client.prepareGet();

		gr = gr.setIndex("datas-meta").setType("meta-info").setId("0");
		GetResponse res = gr.execute().actionGet();

		Map<String, Object> src = res.getSource();

		if (src == null) {
			throw new FsException("no source is found:");
		}

		return new MetaInfo(src);
	}

	/**
	 * Nov 1, 2012
	 */
	public String getNodeIndex() {
		//
		return this.nodeIndex;
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
		return "version:" + this.version + ",nodeInde:" + this.nodeIndex;
	}

}
