/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.AnalyzerType;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.expector.dataservice.api.AuthedNode;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class Expectation extends AuthedNode {

	public static final String BODY = "body";

	public static final String FORMAT = "format";

	public static final String TITLE = "title";

	public static final String SUMMARY = "summary";

	/**
	 * @param ntype
	 * @param pts
	 */
	public Expectation() {
		super(NodeTypes.EXPECTATION);
	}

	public String getBody() {
		return (String) this.getProperty(BODY);
	}

	public void setBody(String value) {
		this.setProperty(BODY, value);
	}

	public String getTitle() {
		return (String) this.getProperty(TITLE);
	}

	public void setTitle(String value) {
		this.setProperty(TITLE, value);
	}


	public String getSummary() {
		return (String) this.getProperty(SUMMARY);
	}

	public void setSummary(String value) {
		this.setProperty(SUMMARY, value);
	}

	public String getFormat() {
		return (String) this.getProperty(FORMAT);
	}

	public void setFormat(String value) {
		this.setProperty(FORMAT, value);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		AuthedNode.config(cfs.addConfig(NodeTypes.EXPECTATION, Expectation.class)//
				.field(TITLE)//
				.field(SUMMARY)//
				.field(FORMAT)//
				.field(BODY, AnalyzerType.TEXT));
	}

}
