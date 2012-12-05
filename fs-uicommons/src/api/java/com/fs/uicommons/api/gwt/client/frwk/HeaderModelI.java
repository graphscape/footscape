/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.Position;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public interface HeaderModelI extends ModelI {

	public static class ItemModel extends ModelSupport {

		protected String name;

		public static final Location L_DISPLAY_NAME = Location
				.valueOf("displayName");//

		public static final Location L_ISSELECTED = Location
				.valueOf("_isselected");

		public static final Location L_POSITION = Location.valueOf("_position");

		public static final Location L_TRIGGERED_MS = Location
				.valueOf("_triggeredMs");

		public static final Position P_LEFT = Position.valueOf("left");

		public static final Position P_RIGHT = Position.valueOf("right");

		public static final Position P_CENTER = Position.valueOf("center");

		public ItemModel(String name) {
			super(name);
			this.name = name;
		}

		public void addTriggerHandler(HandlerI<ModelValueEvent> eh) {
			this.addValueHandler(L_TRIGGERED_MS, eh);
		}

		public void addSelectHandler(HandlerI<ModelValueEvent> eh) {
			this.addValueHandler(L_ISSELECTED, eh);
		}

		public void trigger() {
			this.setValue(L_TRIGGERED_MS, System.currentTimeMillis());
		}

		public String getName() {
			return name;
		}

		public ItemModel addItem(String name) {
			ItemModel old = this.getChild(ItemModel.class, name, false);
			if (old != null) {
				throw new UiException("already exist name:" + name
						+ " under item:" + this.getName());
			}
			ItemModel rt = new ItemModel(name);

			rt.parent(this);

			return rt;
		}

		public ItemModel addItem(String name, final ManagedModelI mgd) {
			final ItemModel rt = this.addItem(name);
			rt.addTriggerHandler(new HandlerI<ModelValueEvent>() {

				@Override
				public void handle(ModelValueEvent e) {
					mgd.select(true);//
				}
			});

			return rt;
		}

		public void setPosition(Position p) {
			this.setValue(L_POSITION, p);
		}

		public Position getPosition() {
			return this.getValue(Position.class, L_POSITION, P_LEFT);
		}

		public int getItemDepth() {

			if (null == this.parent || !(this.parent instanceof ItemModel)) {
				return 0;
			}
			ItemModel p = (ItemModel) this.parent;
			return p.getItemDepth() + 1;
		}

		public boolean isSelected() {
			Boolean b = (Boolean) this.getValue(L_ISSELECTED);
			return b == null ? false : b;
		}

		/**
		 * Nov 25, 2012
		 */
		public void select(boolean b) {
			this.setValue(L_ISSELECTED, b);
		}

	}

	public static final String MK_ITEM_LIST = "ITEM_LIST";

	public ItemModel addItem(String name, Position pos);

	public ItemModel addItem(String name, Position pos, ManagedModelI mgd);

	public ItemModel getItem(String name, boolean force);

	public ItemModel getOrAdd(String name, Position pos);

	public ItemModel addItem(String[] path, Position pos);

}
