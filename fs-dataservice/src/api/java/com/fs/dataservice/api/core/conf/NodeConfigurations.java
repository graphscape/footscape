/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package com.fs.dataservice.api.core.conf;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class NodeConfigurations {
	private Map<NodeType, NodeConfig> nodeCofigMap = new HashMap<NodeType, NodeConfig>();
	private Map<Class<?>, NodeConfig> nodeCofigMap2 = new HashMap<Class<?>, NodeConfig>();

	public NodeConfig getNodeConfig(NodeType ntype, boolean force) {
		NodeConfig rt = this.nodeCofigMap.get(ntype);
		if (rt == null && force) {
			throw new FsException("no config for node type:" + ntype);
		}
		return rt;
	}

	public NodeConfig addConfig(NodeType ntype,
			Class<? extends NodeWrapper> wcls) {
		NodeConfig rt = new NodeConfig(ntype, wcls);

		if (this.getNodeConfig(ntype, false) != null) {
			throw new FsException("already exist config for type:" + ntype);
		}
		this.nodeCofigMap.put(ntype, rt);
		this.nodeCofigMap2.put(wcls, rt);
		return rt;
	}

	/**
	 * Nov 28, 2012
	 */
	public NodeConfig getNodeConfig(Class<? extends NodeWrapper> cls, boolean force) {
		NodeConfig rt = this.nodeCofigMap2.get(cls);
		if (rt == null && force) {
			throw new FsException("no config for wrapper:" + cls);
		}
		return rt;

	}

}
