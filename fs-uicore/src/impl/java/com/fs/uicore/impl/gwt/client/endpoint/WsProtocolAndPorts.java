/**
 *  
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.UiCoreConstants;
import com.fs.uicore.api.gwt.client.UiException;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class WsProtocolAndPorts {

	public static class ProtocolPort {

		/**
		 * @param pro
		 * @param port2
		 */
		public ProtocolPort(String pro, int port2) {
			this.protocol = pro;
			this.port = port2;
		}

		public String protocol;

		public int port;

	}

	private List<ProtocolPort> configuredL = new ArrayList<ProtocolPort>();

	private ProtocolPort default_;

	private WsProtocolAndPorts(List<ProtocolPort> ppL, ProtocolPort def) {
		this.configuredL.addAll(ppL);
		this.default_ = def;
	}

	public static WsProtocolAndPorts getInstance() {
		List<ProtocolPort> ppL = new ArrayList<ProtocolPort>();

		String config = Window.Location.getParameter(UiCoreConstants.PK_WS_PROTOCOL_PORT_S);

		if (config != null) {

			String[] ppSs = config.split(",");
			for (int i = 0; i < ppSs.length; i++) {
				String ppS = ppSs[i];
				String[] ppI = ppS.split(":");
				String pro = null;
				String portS = null;
				if (ppI.length == 1) {
					pro = "ws";
					portS = ppI[0];
				} else {//length is 2
					pro = ppI[0];
					portS = ppI[1];
				}
				int port = Integer.parseInt(portS);
				ProtocolPort pp = new ProtocolPort(pro, port);
				ppL.add(pp);
			}
		}

		// the last one is default
		String hpro = Window.Location.getProtocol();
		boolean https = hpro.equals("https:");
		String wsp = https ? "wss" : "ws";
		String portS = Window.Location.getPort();
		int port = Integer.parseInt(portS);
		ProtocolPort def = new ProtocolPort(wsp, port);

		return new WsProtocolAndPorts(ppL, def);
	}

	public List<ProtocolPort> getConfiguredList() {
		return configuredL;
	}

	public ProtocolPort getFirstOrDefault() {
		ProtocolPort rt = this.getFirst(false);
		if (rt != null) {
			return rt;
		}
		return this.default_;
	}

	public ProtocolPort getFirst(boolean force) {
		if (this.configuredL.isEmpty()) {
			if (force) {
				throw new UiException("no any configured");
			}
			return null;
		}
		return this.configuredL.get(0);
	}

	public WsProtocolAndPorts shiftLeft() {
		List<ProtocolPort> ppl = new ArrayList<ProtocolPort>();

		for (int i = 1; i < this.configuredL.size(); i++) {
			ppl.add(this.configuredL.get(i));
		}

		if (!ppl.isEmpty()) {// append the first one to tail
			ppl.add(ppl.get(0));
		}

		return new WsProtocolAndPorts(ppl, this.default_);
	}

	public ProtocolPort getDefault() {
		return this.default_;
	}

	/**
	 * @return
	 */
	public String getAsParameter() {
		String rt = "";

		for (int i = 0; i < this.configuredL.size(); i++) {
			ProtocolPort pp = this.configuredL.get(i);
			if (!pp.protocol.equals("ws")) {// not default protocol
				rt += pp.protocol + ":";
			}
			rt += pp.port;
			if (i < this.configuredL.size() - 1) {
				rt += ",";// CSV
			}
		}

		return rt;
	}

}
