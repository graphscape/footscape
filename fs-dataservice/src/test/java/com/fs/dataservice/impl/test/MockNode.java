/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 30, 2012
 */
package com.fs.dataservice.impl.test;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class MockNode extends NodeWrapper {

	public static NodeType TYPE = NodeType.valueOf("mockNode");

	public static final String FIELD1 = "field1";
	public static final String FIELD2 = "field2";

	/**
	 * @param type
	 */
	public MockNode() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(TYPE, MockNode.class).field("field1").field("field2");
	}

}
