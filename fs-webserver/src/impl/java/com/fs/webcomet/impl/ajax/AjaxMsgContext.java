/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public class AjaxMsgContext {
	public AjaxComet as;
	public AjaxMsg am;
	public HttpServletRequest req;
	public HttpServletResponse res;
	public int totalMessages = 0;

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

	/**
	 * May 8, 2013
	 */
	public void writeFailure() {
		Path path = this.am.getPath();
		path = path.getSubPath("failure");
		AjaxMsg am = new AjaxMsg(path);
		this.write(am);
	}

}
