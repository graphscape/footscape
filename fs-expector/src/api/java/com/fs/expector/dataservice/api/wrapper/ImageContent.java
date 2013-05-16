/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class ImageContent extends NodeWrapper {

	public static final String PROTOCOL = "protocol";

	public static final String FORMAT = "format";

	public static final String ENCODE = "encode";

	public static final String DATA = "data";

	/**
	 * @param ntype
	 * @param pts
	 */
	public ImageContent() {
		super(NodeTypes.EXPECTATION);
	}
	
	public void setProtocol(String pro){
		this.setProperty(PROTOCOL, pro);
	}
	
	public String getProtocol(){
		return (String)this.getProperty(PROTOCOL);
	}
	
	public String getEncode() {
		return (String) this.getProperty(ENCODE);
	}

	public void setEncode(String value) {
		this.setProperty(ENCODE, value);
	}

	public String getFormat() {
		return (String) this.getProperty(FORMAT);
	}

	public void setFormat(String value) {
		this.setProperty(FORMAT, value);
	}

	public String getData() {
		return (String) this.getProperty(DATA);
	}

	public void setData(String value) {
		this.setProperty(DATA, value);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.IMAGE_URL, ImageContent.class)//
				.field(FORMAT)//
				.field(ENCODE)//
				.field(DATA)//
				.field(PROTOCOL)//
		;
	}

}
