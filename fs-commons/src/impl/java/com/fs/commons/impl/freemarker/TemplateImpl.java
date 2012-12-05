/**
 * Jul 25, 2012
 */
package com.fs.commons.impl.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.fs.commons.api.freemarker.TemplateI;
import com.fs.commons.api.lang.FsException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author wu
 * 
 */
public class TemplateImpl implements TemplateI {

	protected Template ft;

	/** */
	public TemplateImpl(Template ft) {
		this.ft = ft;
	}

	/* */
	@Override
	public void process(Object ctx, Writer out) {
		try {
			this.ft.process(ctx, out);
		} catch (TemplateException e) {
			throw new FsException(e);

		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	/* */
	@Override
	public void process(Object ctx, StringBuffer sb) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Writer w = new OutputStreamWriter(out);
		this.process(ctx, w);
		String s = out.toString();// TODO charset
		sb.append(s);//

	}

}
