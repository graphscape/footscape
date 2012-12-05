/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package com.fs.dataservice.api.core.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class NodeConfig {

	private Map<String, FieldConfig> fieldConfigMap = new HashMap<String, FieldConfig>();

	private Class<? extends NodeWrapper> wrapperClass;

	private NodeType nodeType;

	public NodeConfig(NodeType ntype, Class<? extends NodeWrapper> wcls) {
		this.nodeType = ntype;
		this.wrapperClass = wcls;
		this.field(NodeI.PK_UNIQUE_ID, false);
		this.field(NodeI.PK_ID, false);
		this.field(NodeI.PK_TIMESTAMP, false);
	}

	public NodeConfig field(String name) {
		return field(name, true);
	}

	public NodeConfig field(String name, boolean mand) {
		this.addField(name, mand);
		return this;
	}

	public FieldConfig addField(String fname) {
		return this.addField(fname, true);

	}

	public FieldConfig addField(String fname, boolean mand) {

		FieldConfig rt = new FieldConfig(this, fname, mand);
		if (null != this.getField(fname, false)) {
			throw new FsException("field already exist:" + fname + " for type:"
					+ this.nodeType);
		}
		this.fieldConfigMap.put(fname, rt);

		return rt;
	}

	/**
	 * @return the nodeType
	 */
	public NodeType getNodeType() {
		return nodeType;
	}

	/**
	 * @return the wrapperClass
	 */
	public Class<? extends NodeWrapper> getWrapperClass() {
		return wrapperClass;
	}

	public Set<String> keySet() {
		return this.fieldConfigMap.keySet();
	}

	public NodeWrapper newWraper() {
		NodeWrapper rt = ClassUtil.newInstance(this.wrapperClass);

		return rt;
	}

	/**
	 * Nov 2, 2012
	 */
	public FieldConfig getField(String fname, boolean force) {
		//
		FieldConfig rt = this.fieldConfigMap.get(fname);
		if (force && rt == null) {
			throw new FsException("no field:" + fname + " found for type:"
					+ this.nodeType);
		}
		return rt;
	}
}
