/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class ModelChildEvent extends ModelEvent {

	public static Type<ModelChildEvent> TYPE = new Type<ModelChildEvent>();

	private ModelI child;

	private int idx;

	private boolean add;

	public ModelChildEvent(ModelI m, ModelI child, int idx, boolean aor) {
		this(m, child, idx, aor, null);

	}

	public ModelChildEvent(ModelI m, ModelI child, int idx, boolean aor,
			String rep) {
		super(TYPE, m, rep);
		this.child = child;
		this.add = aor;
	}

	/**
	 * @return the child
	 */
	public ModelI getChild() {
		return child;
	}

	/**
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @return the addOrRemove
	 */
	public boolean isAdd() {
		return add;
	}

}
