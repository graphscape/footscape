/**
 * Jul 25, 2012
 */
package com.fs.commons.impl.freemarker;

import java.io.IOException;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.freemarker.TemplateI;
import com.fs.commons.api.lang.FsException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author wu
 * 
 */
public class TemplateFactory extends ConfigurableSupport implements
		TemplateI.FactoryI {
	protected Configuration tcfg;

	/* */
	@Override
	public TemplateI getTemplate(String name) {

		Template ft;
		try {
			ft = this.tcfg.getTemplate(name);
		} catch (IOException e) {
			throw new FsException(e);

		}
		TemplateImpl rt = new TemplateImpl(ft);
		return rt;

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		this.tcfg = new Configuration();
		String prefix = "freemarker.setting.";

		List<String> kL = null;//TODO this.config.keyListStartWith(prefix);
		for (String k : kL) {
			String key = k.substring(prefix.length());
			String value = this.config.getProperty(k);
			try {
				this.tcfg.setSetting(key, value);
			} catch (TemplateException e) {
				throw new FsException(e);
			}
		}
		this.tcfg.setTemplateLoader(new TemplateLoaderImpl());

	}

}
