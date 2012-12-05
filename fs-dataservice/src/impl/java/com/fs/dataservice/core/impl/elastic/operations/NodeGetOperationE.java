/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.result.NodeResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.dataservice.core.impl.elastic.GetResponseNodeImpl;

/**
 * @author wu
 * 
 */
public class NodeGetOperationE extends
		OperationSupport<NodeGetOperationI, NodeResultI> implements
		NodeGetOperationI {

	private static final String PK_UNIQUE = "uniqueId";
	private static final String PK_NODETYPE = "nodeType";

	private ElasticClientI elastic;

	private static class GetResult extends ResultSupport<NodeResultI, NodeI>
			implements NodeResultI {

		public GetResult(DataServiceI ds) {
			super(ds);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public NodeI getNode(boolean force) {
			//
			return (NodeI) super.get(force);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public <T extends NodeWrapper> T wrapNode(Class<T> cls, boolean force) {
			//
			//

			//
			NodeI node = this.getNode(force);
			if (node == null) {
				if (force) {
					throw new FsException("force get node by id");
				}
				return null;
			} else {
				T rt = ClassUtil.newInstance(cls);
				rt.attachTo(node, this.dataService);
				return rt;
			}
		}

	}

	/**
	 * @param ds
	 */
	public NodeGetOperationE(DataServiceI ds) {
		super(new GetResult(ds));
		this.elastic = (ElasticClientI) ds;
	}

	@Override
	public NodeResultI getResult() {
		return (com.fs.dataservice.api.core.result.NodeResultI) this.result;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI nodeType(NodeType ntype) {
		this.parameter(PK_NODETYPE, ntype);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI uniqueId(String id) {
		this.parameter(PK_UNIQUE, id);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(NodeResultI rst) throws Exception {
		Client client = elastic.getClient();
		GetResponse gr = client
				.prepareGet(this.elastic.getIndex(),
						this.getNodeType(true).toString(),
						this.getUniqueId(true)).execute().actionGet();
		Map<String, Object> src = gr.getSource();
		if (src == null) {
			rst.set(null);//
		} else {
			NodeI node = new GetResponseNodeImpl(gr);
			rst.set(node);//
		}
	}

	/**
	 * Oct 27, 2012
	 */
	private String getUniqueId(boolean force) {
		//
		return (String) this.getParameter(PK_UNIQUE, force);

	}

	/**
	 * Oct 27, 2012
	 */
	private NodeType getNodeType(boolean force) {
		//
		return (NodeType) this.getParameter(PK_NODETYPE, force);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI execute(NodeType type, String uid) {
		return this.nodeType(type).uniqueId(uid).execute().cast();
	}

}
