package com.fs.commons.impl.freemarker.wrapper;

import com.fs.commons.api.value.PropertiesI;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class PropertiesModel implements TemplateHashModel {
	public static final ModelFactory FACTORY = new ModelFactory() {
		public TemplateModel create(Object object, ObjectWrapper wrapper) {
			return new PropertiesModel((PropertiesI) object,
					(BeansWrapper) wrapper);
		}
	};

	private PropertiesI object;

	private BeansWrapper wrapper;

	public PropertiesModel(PropertiesI t, BeansWrapper w) {
		this.object = t;
		this.wrapper = w;
	}

	public TemplateModel get(String key) throws TemplateModelException {
		Object value = this.object.getProperty(key);
		return this.wrapper.wrap(value);
	}

	public boolean isEmpty() throws TemplateModelException {
		// TODO Auto-generated method stub
		return false;
	}
}
