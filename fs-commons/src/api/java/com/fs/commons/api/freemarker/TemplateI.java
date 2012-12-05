/**
 * Jul 25, 2012
 */
package com.fs.commons.api.freemarker;

import java.io.Writer;

/**
 * @author wu
 * 
 */
public interface TemplateI {
	public static interface FactoryI {

		public TemplateI getTemplate(String name);

	}

	public void process(Object ctx, Writer out);

	public void process(Object ctx, StringBuffer sb);

}
