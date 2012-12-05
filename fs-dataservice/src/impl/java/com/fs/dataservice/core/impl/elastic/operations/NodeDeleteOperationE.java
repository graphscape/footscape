/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeDeleteOperationI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.api.core.support.VoidResult;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public class NodeDeleteOperationE extends
		OperationSupport<NodeDeleteOperationI, VoidResultI> implements
		NodeDeleteOperationI {

	private static final String PK_UNIQUE = "uniqueId";
	private static final String PK_NODETYPE = "nodeType";

	private ElasticClientI elastic;

	/**
	 * @param ds
	 */
	public NodeDeleteOperationE(DataServiceI ds) {
		super(new VoidResult(ds));
		this.elastic = (ElasticClientI) ds;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeDeleteOperationI nodeType(NodeType ntype) {
		this.parameter(PK_NODETYPE, ntype);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeDeleteOperationI uniqueId(String id) {
		this.parameter(PK_UNIQUE, id);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		Client client = elastic.getClient();

		DeleteRequestBuilder drb = client.prepareDelete();
		drb.setIndex(elastic.getIndex());
		drb.setType(this.getNodeType(true).toString());
		drb.setId(this.getUniqueId(true));//

		DeleteResponse gr = drb.execute().actionGet();

		rst.set(!gr.isNotFound());//

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
	public NodeDeleteOperationI execute(NodeType type, String uid) {
		return this.nodeType(type).uniqueId(uid).execute().cast();
	}

}
