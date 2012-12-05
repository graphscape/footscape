/**
 * Jun 21, 2012
 */
package com.fs.commons.impl.filter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.support.ProxyContainerSupport;

/**
 * @author wuzhen
 * 
 */
public class FilterContainer<REQ, RES> extends ProxyContainerSupport {
	public static class FilterEntry<REQ, RES> {
		FilterI<REQ, RES> filter;

		public FilterEntry(FilterI<REQ, RES> fl) {
			this.filter = fl;
		}

		public int getPriority() {

			return this.filter.getPriority();
		}
	}

	private List<FilterI<REQ, RES>> filterList;

	private Boolean dirty = true;

	public FilterContainer(ContainerI target) {
		super(target);// TODO
	}

	/**
	 * @return the filterList
	 */
	public List<FilterI<REQ, RES>> getFilterList() {// ordered with
													// priority
		synchronized (this.dirty) {
			if (!this.dirty) {
				return this.filterList;
			}

			List<FilterI> fl = this.finder(FilterI.class).find();

			FilterI<REQ, RES>[] feA = fl.toArray(new FilterI[fl.size()]);
			Arrays.sort(feA, new Comparator<FilterI>() {

				@Override
				public int compare(FilterI o1, FilterI o2) {
					FilterEntry<REQ, RES> fe1 = new FilterEntry<REQ, RES>(o1);
					FilterEntry<REQ, RES> fe2 = new FilterEntry<REQ, RES>(o2);

					return fe1.getPriority() - fe2.getPriority();

				}
			});

			this.filterList = Arrays.asList(feA);
			this.dirty = false;
			return filterList;
		}
	}

	/*
	
	 */
	@Override
	public void addObject(SPI spi, String name, Object o) {
		synchronized (this.dirty) {
			super.addObject(spi, name, o);
			this.dirty = true;
		}
	}

}
