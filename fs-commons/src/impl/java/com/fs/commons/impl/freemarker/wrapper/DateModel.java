/*
 * Created by wu on 2007-8-3.
 * Shanghai Biheng Software Co., Ltd
 */
package com.fs.commons.impl.freemarker.wrapper;

import java.util.Date;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author wu
 * 
 */
public class DateModel implements TemplateDateModel {
	public static final ModelFactory FACTORY = new ModelFactory() {
		public TemplateModel create(Object object, ObjectWrapper wrapper) {
			return new DateModel((Date) object, (BeansWrapper) wrapper);
		}
	};

	private Date date;
	private BeansWrapper wrapper;

	public DateModel(Date c, BeansWrapper w) {
		this.date = c;
		this.wrapper = w;
	}

	public Date getAsDate() throws TemplateModelException {

		return this.date;
	}

	public int getDateType() {
		return TemplateDateModel.DATE;
	}
}