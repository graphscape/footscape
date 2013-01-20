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
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.operations.NodeDeleteOperationI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.api.core.support.VoidResult;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public class NodeDeleteOperationE<W extends NodeWrapper> extends
		OperationSupport<NodeDeleteOperationI<W>, VoidResultI> implements NodeDeleteOperationI<W> {

	private static final String PK_UNIQUE = "uniqueId";
	private static final String PK_NODETYPE = "nodeType";
	private static final String PK_WRAPPER_CLS = "wrapperCls";

	private ElasticClientI elastic;

	protected NodeMeta nodeConfig;

	/**
	 * @param ds
	 */
	public NodeDeleteOperationE(DataServiceI ds) {
		super(new VoidResult(ds));
		this.elastic = (ElasticClientI) ds;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public NodeDeleteOperationI<W> nodeType(Class<W> cls) {
		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(cls, true);
		this.nodeType(nc);
		return this;
	}

	/**
	 * Nov 28, 2012
	 */
	private void nodeType(NodeMeta nc) {
		this.parameter(PK_NODETYPE, nc.getNodeType());
		this.parameter(PK_WRAPPER_CLS, nc.getWrapperClass());
		this.nodeConfig = nc;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeDeleteOperationI<W> uniqueId(String id) {
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
		drb.setRefresh(true);

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

}
