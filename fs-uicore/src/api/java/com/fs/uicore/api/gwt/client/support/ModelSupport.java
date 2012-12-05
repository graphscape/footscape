/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.efilter.ModelChildEventFilter;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelChildEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.model.ModelEventReplayer;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;
import com.fs.uicore.api.gwt.client.util.UID;

/**
 * @author wu
 * 
 */
public class ModelSupport extends UiObjectSupport implements ModelI {

	protected boolean silent;

	/**
	 * @param defProperty
	 */
	public ModelSupport(String name) {
		this(name, null);
	}

	public ModelSupport(String name, String id) {
		super(name, id);
	}

	public <T extends ModelI> T defaultValue(Object def) {
		this.setValue(L_DEFAULT, def);
		return (T) this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public UiObjectI parent(UiObjectI parent) {
		if (parent != null && !(parent instanceof ModelI)) {
			throw new UiException("parent of model must be model,but is:"
					+ parent);
		}
		super.parent(parent);
		return this;
	}

	@Override
	public void addChild(UiObjectI c) {

		int idx = this.childList.size();// append to last?

		super.addChild(c);

		ModelI mc = (ModelI) c;

		new ModelChildEvent(this, mc, idx, true).dispatch();

	}

	@Override
	public void removeChild(UiObjectI c) {
		int idx = this.childList.indexOf(c);//

		this.childList.remove(c);

		ModelI mc = (ModelI) c;

		new ModelChildEvent(this, mc, idx, false).dispatch();

	}

	@Override
	public void setValue(String pro, Object value) {
		this.setValue(Location.valueOf(pro), value);
	}

	@Override
	public void setValue(String pro, int idx, Object value) {

		Location loc = idx >= 0 ? Location.valueOf(pro, idx) : Location
				.valueOf(pro, Boolean.TRUE);

		this.setValue(loc, value);
	}

	@Override
	public void setValue(Location loc, Object value) {
		String pro = loc.getProperty();
		Location loc2 = loc;
		ValueWrapper valueW = null;
		if (loc.isListItem()) {// a list
			Integer idx = loc.getIndex();

			if (idx != null) {
				throw new UiException("TODO insert");
			}

			Object obj = this.getProperty(loc.getProperty());
			List<ValueWrapper> ls = null;
			if (obj == null) {
				ls = new ArrayList<ValueWrapper>();
				this._setProperty(loc.getProperty(), ls);
			} else {
				if (!(obj instanceof List)) {
					throw new UiException("loc:" + loc
							+ " is point to the none-list property");
				}
				ls = (List<ValueWrapper>) obj;

			}

			if (loc.isLast()) {// append to end of list
				valueW = new ValueWrapper();
				ls.add(valueW);
				loc2 = Location.valueOf(pro, ls.size() - 1, true);//
			} else {// find the value and set it
				throw new UiException("TODO");
			}

		} else if (loc.getKey() != null) {
			throw new UiException("TODO");
		} else {// property itself
			valueW = new ValueWrapper();

			this._setProperty(pro, valueW);

		}
		//

		this.setValue(valueW, value, loc2);
	}

	protected void _setProperty(String key, Object value) {
		super.setProperty(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		throw new UiException("not allowed,please use ModelI.setValue");
	}

	@Override
	public void setProperties(UiPropertiesI<Object> pts) {
		throw new UiException("not allowed,please use ModelI.setValue");

	}

	protected void setValue(ValueWrapper valueW, Object value, Location loc) {
		valueW.setValue(value);

		new ModelValueEvent(this, loc, valueW).dispatch();//
	}

	@Override
	public ValueWrapper getValueWrapper(Location loc) {
		// TODO Auto-generated method stub
		Object obj = this.getProperty(loc.getProperty());
		if (obj == null) {
			return null;
		}

		if (obj instanceof ValueWrapper) {
			return (ValueWrapper) obj;
		} else if (obj instanceof List) {
			List<ValueWrapper> l = (List<ValueWrapper>) obj;
			Integer idx = loc.getIndex();
			if (idx == null || idx < 0 || idx >= l.size()) {
				throw new UiException(
						"loc:"
								+ loc
								+ " contains invalid index infor,but point to a property which is a list");
			}
			return l.get(idx);
		} else {
			throw new UiException("loc:" + loc + " has value:" + obj
					+ " of type unknown");
		}

	}

	@Override
	public Object getValue(Location loc) {
		ValueWrapper vw = this.getValueWrapper(loc);
		return vw == null ? null : vw.getValue();
	}

	@Override
	public Object getValue(String pro) {

		return this.getValue(Location.valueOf(pro));

	}

	@Override
	public Object getValue(Location loc, boolean force) {
		Object rt = this.getValue(loc);
		if (rt == null && force) {
			throw new UiException("no value found for loc:" + loc);
		}
		return rt;
	}

	@Override
	public Object getValue(String pro, int idx) {
		return this.getValue(Location.valueOf(pro, idx));

	}

	@Override
	public <T> T getValue(Class<T> cls, Location loc) {
		return getValue(cls, loc, null);
	}

	@Override
	public <T> T getValue(Class<T> cls, Location loc, T def) {
		Object v = this.getValue(loc);

		if (v == null) {
			return def;
		}
		return (T) v;
	}

	@Override
	public <T> T getValue(Class<T> cls, Location loc, boolean force) {
		T rt = this.getValue(cls, loc, null);
		if (force && rt == null) {
			throw new UiException("no value found for loc:" + loc);
		}
		return rt;
	}

	@Override
	public <T> T getValue(Class<T> cls, String pro, T def) {
		return this.getValue(cls, Location.valueOf(pro), def);
	}

	@Override
	public ModelI addModel(String name) {
		ModelI rt = new SimpleModel(name);
		rt.parent(this);//
		return rt;
	}

	@Override
	public <T extends WidgetI> T getWidget(Class<T> cls, boolean force) {
		Object o = this.getValue(L_WIDGET);
		if (o == null && force) {
			throw new UiException("no widget binded for model:" + this);
		}
		return (T) o;
	}

	public ReplayerI createReplayer() {
		String key = UID.create();//
		return this.createReplayer(key);
	}

	@Override
	public ReplayerI createReplayer(String key) {
		return new ModelEventReplayer(this, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.ModelI#silent(boolean)
	 */
	@Override
	public void silent(boolean s) {
		this.silent = s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.ModelI#isSilent()
	 */
	@Override
	public boolean isSilent() {
		return this.silent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.support.UiObjectSupport#dispatch(com.fs.
	 * uicore.api.gwt.client.core.Event)
	 */
	@Override
	public <E extends Event> void dispatch(E evt) {
		if (this.silent) {
			return;
		}
		super.dispatch(evt);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.ModelI#addDefaultValueHandler(com.fs.uicore
	 * .api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public void addDefaultValueHandler(HandlerI<ModelValueEvent> eh) {
		this.addHandler(new ModelValueEventFilter(ModelI.L_DEFAULT), eh);

	}

	@Override
	public void addValueHandler(Location loc, HandlerI<ModelValueEvent> eh) {
		this.addHandler(new ModelValueEventFilter(loc), eh);

	}

	@Override
	public void addValueHandler(Location loc, Object value,
			HandlerI<ModelValueEvent> eh) {
		this.addHandler(new ModelValueEventFilter(loc, value), eh);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.ModelI#setDefaultValue(java.lang.Object)
	 */
	@Override
	public void setDefaultValue(Object value) {
		this.setValue(L_DEFAULT, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.ModelI#getDefaultValue()
	 */
	@Override
	public Object getDefaultValue() {
		return this.getValue(L_DEFAULT);
	}

	@Override
	public <T> T getDefaultValue(T def) {
		Object rt = this.getValue(L_DEFAULT);
		return rt == null ? def : (T) rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.ModelI#getWidgetFactory()
	 */
	@Override
	public WidgetFactoryI getWidgetFactory() {

		return (WidgetFactoryI) this.getValue(L_WIDGET_FACTORY);
	}

	/*
	 * Oct 23, 2012
	 */
	@Override
	public void handleValueWhenAvailable(Location loc,
			final UiCallbackI<Object, Object> callback) {

		Object value = this.getValue(loc);
		if (value == null) {
			// wait for available.
			this.addHandler(new HandlerI<ModelValueEvent>() {

				@Override
				public void handle(ModelValueEvent e) {
					callback.execute(e.getValue());
				}

			});
		} else {
			callback.execute(value);
		}

	}

	@Override
	public <T extends ModelI> void childProcessor(Class<T> cls, String name,
			final ModelChildProcessorI cp) {

		T c = this.getChild(cls, name, false);
		if (c != null) {
			cp.processChildModelAdd(this, c);//
		}

		// for child adding event
		this.addHandler(new ModelChildEventFilter(cls, true, name),

		new HandlerI<ModelChildEvent>() {

			@Override
			public void handle(ModelChildEvent e) {
				ModelI im = e.getChild();
				cp.processChildModelAdd(ModelSupport.this, im);

			}
		});
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> void addValueProcessor(Location loc, Object value,
			final LazyI<T> lazy) {
		Object v = this.getValue(loc);

		if (ObjectUtil.nullSafeEquals(v, value)) {
			lazy.get();//
		}

		this.addValueHandler(loc, value, new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				lazy.get();
			}
		});
	}

	/*
	 * Dec 2, 2012
	 */
	@Override
	public <X extends ModelI> void addCommitProcessor(
			final UiCallbackI<X, Object> cb) {
		Object v = this.getValue(L_COMMIT);
		if (v != null) {
			cb.execute((X) this);
		}

		this.addValueHandler(L_COMMIT, new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				cb.execute((X) ModelSupport.this);
			}
		});
	}

	/*
	 * Dec 2, 2012
	 */
	@Override
	public void commit() {

		this.setValue(L_COMMIT, DateData.valueOf(System.currentTimeMillis()));//

	}

}
