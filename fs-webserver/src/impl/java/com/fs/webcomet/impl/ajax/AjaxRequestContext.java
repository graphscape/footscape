/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class AjaxRequestContext {

	public int timeoutForFirstMessage = 120 * 1000;// 2 mins.

	public int timeoutForMoreMessage = 100;// should be short enough.

	public HttpServletResponse res;

	public int totalMessages = 0;

	public AjaxComet as;

	private Thread fetchingThread;

	/**
	 * @param req2
	 * @param res2
	 */
	public AjaxRequestContext(AjaxComet as, HttpServletResponse res2) {
		this.res = res2;
		this.as = as;
	}

	/**
	 * May 8, 2013
	 */
	public PrintWriter getWriter() {
		//
		try {
			return this.res.getWriter();
		} catch (IOException e) {
			throw new FsException(e);
		}

	}

	public void writeError(String code, String msg) {
		AjaxMsg am = new AjaxMsg(AjaxMsg.ERROR);
		this.write(am);
	}

	/**
	 * May 8, 2013
	 */
	public void write(AjaxMsg msg) {
		Writer out = this.getWriter();

		try {
			if (this.totalMessages > 0) {
				out.write(",");
			}
			JSONObject json = new JSONObject();
			json.putAll(msg.getAsMap());

			json.writeJSONString(out);
		} catch (IOException e) {
			throw new FsException(e);
		}

		this.totalMessages++;

	}

	public void tryFetchMessage() {

		if (this.as == null) {
			// make the response.
			return;
		}
		while (true) {
			long timeout = 100;
			if (this.totalMessages == 0) {
				// wait a long time for the first message
				timeout = this.timeoutForFirstMessage;
			} else {
				// wait a short time for more messages.
				timeout = this.timeoutForMoreMessage;//
			}
			AjaxMsg msg = null;
			try {
				msg = this.as.getQueue().poll(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {

			}

			if (msg == null || msg.isInterruptMsg()) {// timeout to get the new
														// message, there is no
				// more message
				// any way, break and make the response.
				break;
			}
			// write this message to response
			this.write(msg);

		}
	}

	public void writeMessageStart() {
		Writer writer = this.getWriter();
		try {
			writer.write("[");
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	public void writeMessageEnd() {
		Writer writer = this.getWriter();
		try {
			writer.write("]");
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

}
