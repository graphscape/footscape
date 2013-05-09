/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class AjaxRequestContext {

	public HttpServletRequest req;
	public HttpServletResponse res;
	public int totalMessages = 0;
	public AjaxComet as;

	/**
	 * @param req2
	 * @param res2
	 */
	public AjaxRequestContext(HttpServletRequest req2, HttpServletResponse res2) {
		this.req = req2;
		this.res = res2;
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
			return;
		}

		while (true) {

			AjaxMsg msg = null;
			try {
				msg = this.as.getQueue().poll(this.as.getTimeoutMs(), TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				continue;
			}

			if (msg == null) {// timeout
				break;
			}

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
