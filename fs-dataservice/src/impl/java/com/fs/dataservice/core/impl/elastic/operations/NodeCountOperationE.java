/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeCountOperationI;
import com.fs.dataservice.api.core.result.LongResultI;
import com.fs.dataservice.api.core.support.LongResult;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.dataservice.core.impl.elastic.SearchHitNode;
import com.fs.dataservice.core.impl.elastic.support.NodeQueryOperationSupport;

/**
 * @author wu
 * 
 */
public class NodeCountOperationE<W extends NodeWrapper> extends
		NodeQueryOperationSupport<NodeCountOperationI<W>, W, LongResultI> implements
		NodeCountOperationI<W> {


	private static Logger LOG = LoggerFactory.getLogger(NodeCountOperationE.class);

	private ElasticClientI elastic;


	/**
	 * @param ds
	 */
	public NodeCountOperationE(DataServiceI ds) {
		super(ds, new LongResult(ds));
		this.elastic = (ElasticClientI) ds;

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(LongResultI rst) throws Exception {
		Client client = elastic.getClient();
		BoolQueryBuilder qb = this.buildQuery(rst);
		if (rst.hasError()) {
			return;
		}
		String idx = this.elastic.getIndex();
		
		CountRequestBuilder crb = client.prepareCount(idx)
				.setQuery(qb)//
		;
		CountResponse response = crb.execute()//
				.actionGet();
		long ct = response.getCount();
		rst.set(ct);		
	}


}
