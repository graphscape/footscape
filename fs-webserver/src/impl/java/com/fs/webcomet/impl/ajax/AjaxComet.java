/**
 *  
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.support.CometSupport;

/**
 * @author wu
 * 
 */
public class AjaxComet extends CometSupport {

	private BlockingQueue<String> queue;

	private long timeoutMs = 1000;

	public AjaxComet(String tid) {
		super("ajax", tid);
		this.queue = new LinkedBlockingQueue<String>();
	}

	/**
	 * May 7, 2013
	 */
	public void sendMessage(String msg) {
		//
		try {
			this.queue.put(msg);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}//
	}

	/**
	 * May 7, 2013
	 */
	public void fetchMessages(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter writer = res.getWriter();
		// json array,seperator of message.
		// see JSONArray.writeXXX.
		writer.write("[");
		boolean first = true;
		while (true) {

			String msg = null;
			try {
				msg = this.queue.poll(this.timeoutMs, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				continue;
			}
			if (msg == null) {// timeout
				break;
			}
			if (first) {
				first = false;
			} else {
				writer.write(",");
			}
			JSONValue.writeJSONString(msg, writer);

		}
		writer.write("]");
		writer.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.websocket.api.WebSocketI#addListener(com.fs.websocket.api.WsListenerI
	 * )
	 */
	@Override
	public void addListener(CometListenerI ln) {
		this.addListener(ln);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webcomet.api.CometI#getProtocol()
	 */
	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

}
