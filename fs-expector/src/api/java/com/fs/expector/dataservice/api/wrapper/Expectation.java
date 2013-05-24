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

	public static final String ST_OPEN = "open";

	public static final String ST_CLOSE = "close";

	public static final String BODY = "body";

	public static final String FORMAT = "format";

	public static final String TITLE = "title";

	public static final String SUMMARY = "summary";

	public static final String ICON = "icon";

	public static final String IMAGE = "image";

	public static final String STATUS = "status";

	/**
	 * @param ntype
	 * @param pts
	 */
	public Expectation() {
		super(NodeTypes.EXPECTATION);
	}

	public String getStatus() {
		return (String) this.getProperty(STATUS);
	}

	public void setStatus(String status) {
		this.setProperty(STATUS, status);
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

	public String getIcon() {
		return (String) this.getProperty(ICON);
	}

	public void setIcon(String value) {
		this.setProperty(ICON, value);
	}

	public String getImage() {
		return (String) this.getProperty(IMAGE);
	}

	public void setImage(String value) {
		this.setProperty(IMAGE, value);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		AuthedNode.config(cfs.addConfig(NodeTypes.EXPECTATION, Expectation.class)//
				.field(TITLE, AnalyzerType.TEXT)//
				.field(SUMMARY)//
				.field(FORMAT)//
				.field(ICON)//
				.field(IMAGE)//
				.field(BODY, AnalyzerType.TEXT)//
				.field(STATUS)//
				);
	}

}
