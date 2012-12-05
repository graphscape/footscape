/**
 * Aug 1, 2012
 */
package com.fs.uiserver.impl.handler.node;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu <br>
 *         TODO add priviledge on session and signon id<br>
 *         TODO move this to commons-dbo module.
 */
public class NodeQueryHandler<T extends NodeWrapper> extends UiHandlerSupport {

	protected Class<T> cls;

	protected NodeType type;

	protected NodeQueryHandler(NodeType nt, Class<T> cls) {
		this.cls = cls;
		this.type = nt;
	}

	@Handle("query")
	public void handleQueryNode(HandleContextI hc, RequestI req) {

		PropertiesI<Object> parameters = (PropertiesI<Object>) req
				.getPayload("parameters");


		// TODO add relationship to user/signon/priviledge node.
		
		for (String key : parameters.keyList()) {
			Object obj = parameters.getProperty(key);
			//TODO
		}

		// TODO convert to res

	}
}
