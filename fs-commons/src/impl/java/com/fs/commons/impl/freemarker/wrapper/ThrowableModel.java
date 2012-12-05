package com.fs.commons.impl.freemarker.wrapper;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fs.commons.api.lang.FsException;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ThrowableModel implements TemplateHashModel {
	public static final ModelFactory FACTORY = new ModelFactory() {
		public TemplateModel create(Object object, ObjectWrapper wrapper) {
			return new ThrowableModel((Throwable) object,
					(BeansWrapper) wrapper);
		}
	};

	private Throwable throwable;

	private BeansWrapper wrapper;

	public ThrowableModel(Throwable t, BeansWrapper w) {
		this.throwable = t;
		this.wrapper = w;
	}

	public TemplateModel get(String key) throws TemplateModelException {
		if ("stackTrace".equals(key)) {
			StringWriter sw = new StringWriter();
			this.throwable.printStackTrace(new PrintWriter(sw));
			String s = sw.toString();
			s = s.replace("\n", "<br>");
			return this.wrapper.wrap(sw.toString());
		} else if ("message".equals(key)) {
			return this.wrapper.wrap(this.throwable.getMessage());
		} else if ("cause".equalsIgnoreCase(key)) {
			Throwable t = this.throwable.getCause();
			return this.wrapper.wrap(t);
		}
		return this.wrapper.wrap(this.throwable.toString());
	}

	public boolean isEmpty() throws TemplateModelException {
		// TODO Auto-generated method stub
		return false;
	}
}
