/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MatchQueryBuilder.Type;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.dataservice.core.impl.elastic.SearchHitNode;

/**
 * @author wu
 * 
 */
public class NodeQueryOperationE<W extends NodeWrapper> extends
		OperationSupport<NodeQueryOperationI<W>, NodeQueryResultI<W>> implements NodeQueryOperationI<W> {

	private static Logger LOG = LoggerFactory.getLogger(NodeQueryOperationE.class);
	
	private static class Term {
		String field;
		Object value;
		Boolean mustOrNot;
	}

	private static class Range {
		String field;
		Object from;
		boolean includeFrom;
		Object to;
		boolean includeTo;
	}

	private static class Match {
		String field;
		String pharse;
		int slop;
	}

	private static class Sort {
		private String field;
		private SortOrder order;
	}

	private static final String PK_NODETYPE = "nodeType";

	private static final String PK_WRAPPER_CLS = "wrapperClass";

	private static final String PK_TERMS = "terms";

	private static final String PK_RANGES = "ranges";

	private static final String PK_MATCHES = "matches";

	protected List<Sort> sortList = new ArrayList<Sort>();

	private ElasticClientI elastic;

	protected NodeMeta nodeConfig;
	
	protected boolean explain;

	/**
	 * @param ds
	 */
	public NodeQueryOperationE(DataServiceI ds) {
		super(new NodeQueryResult<W>(ds));
		this.elastic = (ElasticClientI) ds;
		this.parameters.setProperties(PK_TERMS, new ArrayList<Term>());
		this.parameters.setProperties(PK_RANGES, new ArrayList<Range>());
		this.parameters.setProperties(PK_MATCHES, new MapProperties<Match>());

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeQueryOperationI<W> nodeType(NodeType ntype) {

		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(ntype, true);
		this.nodeType(nc);

		return this;
	}
	@Override
	public NodeQueryOperationI<W> explain(boolean expl){
		this.explain = expl;
		return this;
	}
	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(NodeQueryResultI<W> rst) throws Exception {
		Client client = elastic.getClient();
		BoolQueryBuilder qb = new BoolQueryBuilder();
		NodeType ntype = this.getNodeType(true);// type is must.
		String uid = this.getUniqueId();
		// types
		String typeS = ntype.toString();
		if (uid != null) {// id must under type.
			IdsQueryBuilder qb1 = new IdsQueryBuilder(typeS);//

			// ids
			qb1.addIds(uid);//
			qb.must(qb1);
		}
		// type field
		TermQueryBuilder typeQ = new TermQueryBuilder(NodeI.PK_TYPE, typeS);// NOTE,NodeType
																			// is
																			// not
																			// string,cannot
																			// query
																			// out.
		qb.must(typeQ);
		// fields equals
		List<Term> teL = (List<Term>) this.parameters.getProperty(PK_TERMS);

		for (Term tm : teL) {

			this.validateKeyIsConfigedInType(tm.field, rst.getErrorInfo());
			TermQueryBuilder qbi = new TermQueryBuilder(tm.field, tm.value);
			if (tm.mustOrNot == null) {
				qb.should(qbi);
			} else if (tm.mustOrNot) {
				qb.must(qbi);
			} else {
				qb.mustNot(qbi);
			}
		}
		// end of equals
		// ranges

		for (Range rg : this.rangeList()) {
			RangeQueryBuilder qbi = new RangeQueryBuilder(rg.field);
			qbi.from(rg.from);
			qbi.to(rg.to);
			qb.must(qbi);//
		}
		if (rst.hasError()) {
			return;
		}

		// end of ranges
		// matches
		PropertiesI<Match> mts = (PropertiesI<Match>) this.parameters.getProperty(PK_MATCHES);

		this.validateKeyIsConfigedInType(mts.keyList(), rst.getErrorInfo());
		if (rst.hasError()) {
			return;
		}
		for (String key : mts.keyList()) {
			Match rg = mts.getProperty(key);
			if (rg.pharse == null) {
				//
				continue;
			}
			MatchQueryBuilder qbi = new MatchQueryBuilder(key, rg.pharse);
			qbi.type(Type.PHRASE);
			qbi.slop(rg.slop);
			qbi.operator(Operator.AND);//
			qb.must(qbi);//
		}
		String idx = this.elastic.getIndex();
		// end of matches
		SearchRequestBuilder srb = client.prepareSearch(idx)//
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//
				.setQuery(qb)//
				.setFrom(this.getFrom())//
				.setSize(this.getMaxSize())//
				.setExplain(this.explain)//

		;
		for (Sort s : this.sortList) {
			srb.addSort(s.field, s.order);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("query l:" + srb);
		}
		SearchResponse response = srb.execute()//
				.actionGet();
		SearchHits shs = response.getHits();
		List<W> nL = new ArrayList<W>();
		for (SearchHit sh : shs.getHits()) {
			Map<String, SearchHitField> shMap = sh.getFields();

			SearchHitNode shn = new SearchHitNode(ntype, this.dataService, sh);
			W w = (W) this.nodeConfig.newWraper();
			w.forOp(this.dataService);// NOTE
			w.attachTo(shn);
			nL.add(w);
		}
		rst.set(nL);//
	}

	private void validateKeyIsConfigedInType(String field, ErrorInfos errorInfo) {

		if (null == this.nodeConfig.getField(field, false)) {
			errorInfo.add(new ErrorInfo("no field:" + field + ",in type:" + this.nodeConfig.getNodeType()));
		}
	}

	/**
	 * Nov 3, 2012
	 */

	private void validateKeyIsConfigedInType(List<String> keyList, ErrorInfos errorInfo) {

		for (String k : keyList) {

			this.validateKeyIsConfigedInType(k, errorInfo);
		}
	}

	/**
	 * Oct 27, 2012
	 */
	@Override
	public NodeType getNodeType(boolean force) {
		//
		return (NodeType) this.getParameter(PK_NODETYPE, force);
	}

	@Override
	public NodeQueryOperationI<W> propertyNotEq(String key, Object value) {
		//
		Term tm = this.addTerm(key, value);
		tm.mustOrNot = Boolean.FALSE;
		return this;
	}

	@Override
	public NodeQueryOperationI<W> propertyEq(String key, Object value) {
		//
		Term tm = this.addTerm(key, value);
		tm.mustOrNot = Boolean.TRUE;
		return this;
	}

	private List<Term> termList() {
		List<Term> rt = (List<Term>) this.parameters.getProperty(PK_TERMS);
		return rt;
	}

	private Term addTerm(String key, Object value) {

		Term rt = new Term();
		rt.field = key;
		rt.value = value;
		this.termList().add(rt);

		return rt;

	}

	@Override
	public NodeQueryOperationI<W> propertyGt(String key, Object value, boolean include) {
		//
		Range rg = this.addRange(key);
		rg.from = value;
		rg.includeFrom = include;
		return this;
	}

	private List<Range> rangeList() {
		List<Range> tes = (List<Range>) this.parameters.getProperty(PK_RANGES);
		return tes;
	}

	private Range addRange(String key) {

		Range rt = new Range();
		rt.field = key;
		this.rangeList().add(rt);
		return rt;

	}

	@Override
	public NodeQueryOperationI<W> propertyLt(String key, Object value, boolean include) {
		Range rg = this.addRange(key);
		rg.to = value;
		rg.includeTo = include;
		return this;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> uniqueId(String uid) {
		this.parameter(NodeI.PK_UNIQUE_ID, uid);
		return this;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.parameters.getProperty(NodeI.PK_UNIQUE_ID);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> first(int from) {
		//
		this.parameter("first", from);
		return this;

	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> maxSize(int maxs) {
		this.parameter("maxSize", maxs);
		return this;

	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int getFrom() {
		//
		Integer rt = (Integer) this.parameters.getProperty("first");
		return rt == null ? 0 : rt;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int getMaxSize() {
		Integer rt = (Integer) this.parameters.getProperty("maxSize");
		return rt == null ? 100 : rt;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> id(String id) {
		//
		return this.propertyEq(NodeI.PK_ID, id);
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeQueryOperationI<W> sort(String key) {
		//
		return this.sort(key, false);
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeQueryOperationI<W> sort(String key, boolean desc) {
		//
		Sort sort = new Sort();
		sort.field = key;
		sort.order = desc ? SortOrder.DESC : SortOrder.ASC;
		this.sortList.add(sort);
		return this;
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeQueryOperationI<W> sortTimestamp(boolean desc) {
		//
		return this.sort(NodeI.PK_TIMESTAMP, desc);

	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> singleNewest(boolean nf) {
		//
		this.first(0);
		this.maxSize(1);
		this.sortTimestamp(true);//
		return this;
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public NodeQueryOperationI<W> nodeType(Class<W> cls) {
		//
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
	 * Dec 4, 2012
	 */
	@Override
	public NodeQueryOperationI<W> timestampRange(Date from, boolean includeFrom, Date to, boolean includeTo) {
		//
		this.propertyRange(NodeI.PK_TIMESTAMP, from, includeFrom, to, includeTo);
		return this;
	}

	/*
	 * Dec 4, 2012
	 */
	@Override
	public NodeQueryOperationI<W> propertyRange(String key, Object from, boolean includeFrom, Object to,
			boolean includeTo) {
		//
		this.propertyGt(key, from, includeFrom);
		this.propertyLt(key, to, includeTo);//
		return this;
	}

	/*
	 * Jan 19, 2013
	 */
	@Override
	public NodeQueryOperationI<W> propertyMatch(String key, String pharse) {
		return this.propertyMatch(key, pharse, 0);

	}

	@Override
	public NodeQueryOperationI<W> propertyMatch(String key, String pharse, int slop) {
		//
		PropertiesI<Match> tes = (PropertiesI<Match>) this.parameters.getProperty(PK_MATCHES);
		Match m = tes.getProperty(key);
		if (m == null) {
			m = new Match();
			m.field = key;
			m.slop = slop;
			tes.setProperty(key, m);
		}
		m.pharse = pharse;
		return this;
	}

}
