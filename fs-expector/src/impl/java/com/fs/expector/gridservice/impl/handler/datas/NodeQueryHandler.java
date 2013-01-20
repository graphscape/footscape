/**
 * Aug 1, 2012
 */
package com.fs.expector.gridservice.impl.handler.datas;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;

/**
 * @author wu <br>
 *         TODO add priviledge on session and signon id<br>
 *         TODO move this to commons-dbo module.
 */
public class NodeQueryHandler<T extends NodeWrapper> extends ExpectorTMREHSupport {

	protected Class<T> cls;

	protected NodeType type;

	protected NodeQueryHandler(NodeType nt, Class<T> cls) {
		this.cls = cls;
		this.type = nt;
	}

	@Handle("query")
	public void handleQueryNode(MessageContext hc, MessageI req) {

		PropertiesI<Object> parameters = (PropertiesI<Object>) req.getPayload("parameters");

		// TODO add relationship to user/signon/priviledge node.

		for (String key : parameters.keyList()) {
			Object obj = parameters.getProperty(key);
			// TODO
		}

		// TODO convert to res

	}

	@Handle("nodeListById")
	public void handleNewestList(MessageContext hc, MessageI req, ResponseI res) {

		String type = (String) req.getPayload("type", true);

		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(NodeType.valueOf(type), true);

		Class<? extends NodeWrapper> wpcls = nc.getWrapperClass();

		this.processGetNewestListById(wpcls, hc);

	}

}
