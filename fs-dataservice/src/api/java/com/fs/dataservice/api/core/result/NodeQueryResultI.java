/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.result;

import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

public interface NodeQueryResultI<W extends NodeWrapper> extends
		ListResultI<NodeQueryResultI<W>, W> {
	
	public List<PropertiesI<Object>> propertiesList();
	
}