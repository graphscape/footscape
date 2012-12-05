package com.fs.commons.impl.freemarker.wrapper;

import java.util.Calendar;
import java.util.Date;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class CalendarModel implements TemplateDateModel{
	public static final ModelFactory FACTORY = new ModelFactory() {
		public TemplateModel create(Object object, ObjectWrapper wrapper) {
			return new CalendarModel((Calendar) object,
					(BeansWrapper) wrapper);
		}
	};

	private Calendar c;
	private BeansWrapper wrapper;

	public CalendarModel(Calendar c, BeansWrapper w) {
		this.c = c;
		this.wrapper = w;
	}

	public Date getAsDate() throws TemplateModelException {
		
		return this.c.getTime();
	}

	public int getDateType() {
		// TODO Auto-generated method stub
		return TemplateDateModel.DATETIME;
	}

}
