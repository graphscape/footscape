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
import com.fs.dataservice.api.core.meta.FieldMeta;
import com.fs.dataservice.api.core.meta.FieldType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.support.NodeSupport;
import com.fs.dataservice.api.core.wrapper.PropertyConverterI;
import com.fs.dataservice.core.impl.ElasticTimeFormat;

/**
 * @author wu
 * 
 */
public class SearchHitNode extends NodeSupport {
	private static Map<FieldType, PropertyConverterI> pcMap = new HashMap<FieldType, PropertyConverterI>();
	static {
		pcMap.put(FieldType.DATE, new PropertyConverterI<String, Date>() {

			@Override
			public Date convertFromStore(String s) {
				//
				return ElasticTimeFormat.parse(s);

			}
		});
	}

	/**
	 * @param type
	 * @param uid
	 */
	public SearchHitNode(NodeType nodeType, DataServiceI ds, SearchHit sh) {
		super(nodeType, "todo");

		NodeMeta nc = ds.getConfigurations().getNodeConfig(nodeType, true);
		Map<String, Object> old = sh.sourceAsMap();
		for (Map.Entry<String, Object> me : old.entrySet()) {

			String key = me.getKey();
			Object value = me.getValue();
			FieldMeta fm = nc.getField(key, false);
			if (fm != null) {// not defined,TODO throw expcetion.
				PropertyConverterI pc = this.pcMap.get(fm.getType());
				if (pc != null) {
					value = pc.convertFromStore(value);
				}
			}
			this.setProperty(key, value);
		}

	}

}
