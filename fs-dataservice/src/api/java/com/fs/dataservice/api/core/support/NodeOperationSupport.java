/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeOperationI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.conf.NodeConfig;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public abstract class NodeOperationSupport<O extends NodeOperationI<O, T>, T extends ResultI<T, ?>>
		extends OperationSupport<O, T> implements NodeOperationI<O, T> {

	public static final String PK_NODETYPE = "nodeType";

	public static final String PK_WRAPPER_CLS = "wrapperClass";

	public static final String PK_PROPERTIES = "properties";// node
															// propertiesKey

	protected NodeConfig nodeConfig;
	/**
	 * @param ds
	 */
	public NodeOperationSupport(T rst) {
		super(rst);
		this.parameter(PK_PROPERTIES, new MapProperties<Object>());
	}

	@Override
	public O nodeType(NodeType ntype) {

		NodeConfig nc = this.dataService.getConfigurations().getNodeConfig(
				ntype, true);

		return this.nodeType(nc);
	}

	@Override
	public O nodeType(Class<? extends NodeWrapper> cls) {
		NodeConfig nc = this.dataService.getConfigurations().getNodeConfig(cls,
				true);

		return this.nodeType(nc);
	}

	protected O nodeType(NodeConfig nc) {
		this.nodeConfig = nc;
		this.parameter(PK_NODETYPE, nc.getNodeType());
		this.parameter(PK_WRAPPER_CLS, nc.getWrapperClass());
		return this.cast();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O uniqueId(String uid) {
		this.parameter(NodeI.PK_UNIQUE_ID, uid);
		return this.cast();
	}

	public NodeType getNodeType(boolean force) {
		return (NodeType) this.getParameter(PK_NODETYPE, force);
	}

	public String getUniqueId(boolean force) {
		return (String) this.getParameter(NodeI.PK_UNIQUE_ID, force);
	}

	public String getId(boolean force) {
		return (String) this.getParameter(NodeI.PK_ID, force);
	}

	public void setId(String id) {
		this.parameter(NodeI.PK_ID, id);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O properties(PropertiesI<Object> pts) {
		this.getNodeProperties().setProperties(pts);
		return this.cast();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O property(String key, Object value) {
		this.getNodeProperties().setProperty(key, value);
		return this.cast();
	}

	@Override
	public PropertiesI<Object> getNodeProperties() {
		//
		return (PropertiesI<Object>) this.getParameter(PK_PROPERTIES);
	}

}
