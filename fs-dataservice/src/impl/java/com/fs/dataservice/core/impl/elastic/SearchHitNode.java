/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.search.SearchHit;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfig;
import com.fs.dataservice.api.core.support.NodeSupport;
import com.fs.dataservice.api.core.wrapper.PropertyConverterI;
import com.fs.dataservice.core.impl.ElasticTimeFormat;

/**
 * @author wu
 * 
 */
public class SearchHitNode extends NodeSupport {
	private static Map<String, PropertyConverterI> pcMap = new HashMap<String, PropertyConverterI>();
	static {
		pcMap.put(NodeI.PK_TIMESTAMP, new PropertyConverterI<String, Date>() {

			@Override
			public Date convertFromStore(String s) {
				//
				return ElasticTimeFormat.parse(s);

			}
		});
	}

	protected Object convertFromStore(String key, Object value) {
		PropertyConverterI pc = pcMap.get(key);
		if (pc == null) {
			return value;
		}
		return pc.convertFromStore(value);
	}

	/**
	 * @param type
	 * @param uid
	 */
	public SearchHitNode(NodeType nodeType, DataServiceI ds, SearchHit sh) {
		super(nodeType, "todo");

		NodeConfig nc = ds.getConfigurations().getNodeConfig(nodeType, true);

		Map<String, Object> old = sh.sourceAsMap();
		for (Map.Entry<String, Object> me : old.entrySet()) {
			String key = me.getKey();
			Object value = me.getValue();
			Object v = this.convertFromStore(key, value);
			this.setProperty(key, v);
		}

	}

}
