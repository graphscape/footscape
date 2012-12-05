/**
 * Jul 25, 2012
 */
package com.fs.commons.impl.freemarker;

import java.io.IOException;
import java.io.Reader;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * @author wu
 * TODO load support from configuration property
 */
public class TemplateLoaderImpl implements TemplateLoader {
	private ClassTemplateLoader ctl;

	public TemplateLoaderImpl() {
		this.ctl = new ClassTemplateLoader(TemplateImpl.class, "/ftl/");
	}

	/* */
	@Override
	public Object findTemplateSource(String s) throws IOException {

		return ctl.findTemplateSource(s);

	}

	/* */
	@Override
	public long getLastModified(Object obj) {

		return ctl.getLastModified(obj);

	}

	/* */
	@Override
	public Reader getReader(Object obj, String s) throws IOException {

		return ctl.getReader(obj, s);

	}

	/* */
	@Override
	public void closeTemplateSource(Object obj) throws IOException {
		this.ctl.closeTemplateSource(obj);
	}

}
