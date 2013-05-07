/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.context.support.ContextSupport;

/**
 * @author wu
 * 
 */
public class AjaxSession extends ContextSupport {

	private String id;

	private BlockingQueue<String> queue;

	private long timeoutMs = 1000;

	public AjaxSession(String tid) {
		this.id = tid;
		this.queue = new LinkedBlockingQueue<String>();
	}

	/**
	 * May 7, 2013
	 */
	public void sendMessage(String msg) {
		//

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * May 7, 2013
	 */
	public void fetchMessages(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter writer = res.getWriter();
		// json array,seperator of message.
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
			writer.write(msg);
		}
		writer.write("]");
		writer.flush();
	}

}
