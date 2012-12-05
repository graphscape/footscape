/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.ContainerI.ObjectEntryI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.support.ObjectFinderSupport;
import com.fs.commons.api.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public class ObjectFinderImpl<T> extends ObjectFinderSupport<T> {

	private ContainerImpl container;

	private Dumper<T> dumper;

	public ObjectFinderImpl(ContainerImpl c, Class<T> cls) {
		this.container = c;
		this.clazz(cls);
		this.dumper = new Dumper<T>(this);
	}

	private static class Dumper<T> {

		private ObjectFinderImpl<T> ofi;

		Dumper(ObjectFinderImpl<T> ofi) {
			this.ofi = ofi;
		}

		public String toString() {
			final StringBuffer sb = new StringBuffer("finder:");
			sb.append(this.ofi.toString());
			sb.append(",in container with id:");
			sb.append("todo");
			sb.append(",all object are:\n");

			this.ofi.container.forEach(new CallbackI<ObjectEntryI, Boolean>() {

				@Override
				public Boolean execute(ObjectEntryI i) {
					sb.append(i.toString() + ",\n");
					return false;
				}
			});
			return sb.toString();
		}

	}

	@Override
	public String toString() {
		return this.container.toString() + "," + this.describe.toString();
	}

	@Override
	public T find(boolean force) {
		List<T> l = this.find();

		return CollectionUtil.single(l, force, dumper);
	}

	@Override
	public List<T> find() {
		List<T> rt = new ArrayList<T>();

		ContainerI c = this.container;
		do {
			List<T> cL = c.find(this.describe);
			rt.addAll(cL);
			if (!this.withParent) {
				break;
			}
			c = c.getParent();

		} while (c != null);
		return rt;
	}

}
