/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 * @deprecated model should not exist,it is too complicated,we should relay on
 *             language's feature:class,method to building things.
 */
public interface ModelI extends UiObjectI {

	/**
	 * Value location,pointer
	 * 
	 * @author wuzhen
	 * 
	 */
	public static class Location {

		private Path path;

		private String property;

		private Integer index;

		private Boolean last;

		private String key;

		public static Location valueOf(String pro) {
			return new Location(pro, null, null, null);
		}

		public static Location valueOf(String pro, Boolean last) {
			return new Location(pro, null, last, null);
		}

		public static Location valueOf(String pro, Integer idx) {
			return valueOf(pro, idx, null);
		}

		public static Location valueOf(String pro, Integer idx, Boolean last) {
			return new Location(pro, idx, last, null);
		}

		private Location(String pro, Integer idx, Boolean last, String key) {
			this(Path.valueOf(new String[] {}), pro, idx, last, key);
		}

		private Location(Path path, String pro, Integer idx, Boolean last, String key) {
			this.path = path;
			this.property = pro;
			this.index = idx;
			this.key = key;
			this.last = last;
		}

		public boolean isListItem() {
			return this.index != null || this.last != null;
		}

		public boolean isLast() {
			return this.last != null && this.last;
		}

		public String getProperty() {
			return property;
		}

		public Integer getIndex() {
			return index;
		}

		public String getKey() {
			return key;
		}

		public boolean isDefaultProperty() {
			return this.isProperty(L_DEFAULT.property);
		}

		public boolean isProperty(String string) {

			return ObjectUtil.nullSafeEquals(this.property, string);

		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Location)) {
				return false;
			}

			Location p2 = (Location) obj;

			return ObjectUtil.nullSafeEquals(p2.index, this.index) && //
					ObjectUtil.nullSafeEquals(p2.key, this.key) && //
					ObjectUtil.nullSafeEquals(p2.property, this.property) && //
					ObjectUtil.nullSafeEquals(p2.path, this.path)//
			;
		}

		/**
		 * @param location
		 * @return
		 */
		public boolean isSubSetOf(Location loc) {
			if (!this.isProperty(loc.property)) {
				return false;
			}
			return (loc.key == null || loc.key.equals(this.key)
					&& (loc.index == null || loc.index.equals(this.index)));

		}

		/*
		 * Nov 8, 2012
		 */
		@Override
		public String toString() {
			//
			return "class:" + this.getClass() + "," + "property:" + this.property + ",key:" + this.key
					+ ",index:" + this.index;
		}

	}

	public static class ValueWrapper {

		private int version;

		private Object value;

		private Object previousValue;

		public Object getValue() {
			return value;
		}

		public <T> T getValue(T def) {
			if (this.value == null) {
				return def;
			} else {
				return (T) this.value;
			}
		}

		public void setValue(Object value) {
			this.previousValue = this.value;
			this.value = value;
			this.version++;
		}

		public int getVersion() {
			return version;
		}

		public boolean isValue(Object value) {
			return ObjectUtil.nullSafeEquals(this.value, value);
		}

		/**
		 * @return the previousValue
		 */
		public Object getPreviousValue() {
			return previousValue;
		}

	}

	public static interface ValueDeliverI {

		public ValueDeliverI start();// attach/init this deliver.

	}

	public static interface ReplayerI {

		public void replay();

		public String getUniqueKey();

	}

	public static final Location L_DEFAULT = Location.valueOf("_default");

	// this can be used for a group of properties are set.

	public static final Location L_COMMIT = Location.valueOf("_commit");

	public static final Location L_WIDGET = Location.valueOf("_widget");// TODO
																		// this
																		// widget
																		// is
																		// for
																		// debuging,
																		// to be
																		// removed
	public static final Location L_WIDGET_FACTORY = Location.valueOf("_widgetFactory");

	public static final Location L_LAST_WIDGET = Location.valueOf("_lastWidget");// related
																					// widiget
																					// for
																					// debuging.if
																					// test
																					// code
																					// want
																					// to
																					// no
																					// the
																					// related
																					// widget,there
																					// can
																					// listen
																					// this
																					// value
																					// for
																					// any
																					// changing.see
																					// WidgetBase.model
																					// for
																					// more
																					// evidence.

	public String getName();

	public void addDefaultValueHandler(EventHandlerI<ModelValueEvent> eh);

	public void addValueHandler(Location loc, EventHandlerI<ModelValueEvent> eh);

	public void addValueHandler(Location loc, Object value, EventHandlerI<ModelValueEvent> eh);

	public void handleValueWhenAvailable(Location loc, UiCallbackI<Object, Object> callback);

	// not report event when set value.

	public void silent(boolean s);

	public boolean isSilent();

	public void setDefaultValue(Object value);

	public Object getDefaultValue();

	public <T> T getDefaultValue(T def);

	public void setValue(String pro, Object value);

	public void setValue(String pro, int idx, Object value);

	public void setValue(Location loc, Object value);

	public ValueWrapper getValueWrapper(Location loc);

	public Object getValue(Location loc);

	public Object getValue(Location loc, boolean force);

	public <T> T getValue(Class<T> cls, Location loc);

	public <T> T getValue(Class<T> cls, Location loc, boolean force);

	public <T> T getValue(Class<T> cls, Location loc, T def);

	public <T> T getValue(Class<T> cls, String pro, T def);

	public Object getValue(String pro);

	public Object getValue(String pro, int idx);

	public ModelI addModel(String name);

	public WidgetFactoryI getWidgetFactory();

	public <T extends WidgetI> T getWidget(Class<T> cls, boolean force);

	// TODO remove
	public ReplayerI createReplayer(String key);

	public <T extends ModelI> void childProcessor(Class<T> cls, String name, ModelChildProcessorI cp);

	/**
	 * When value equals the parameter value. call lazy.get() method. Dec 2,
	 * 2012
	 */
	public <T> void addValueProcessor(Location loc, Object value, LazyI<T> lazy);

	public void commit();

	public <X extends ModelI> void addCommitProcessor(UiCallbackI<X, Object> cb);

}
