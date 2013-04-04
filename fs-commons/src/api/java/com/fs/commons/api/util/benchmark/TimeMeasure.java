/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.commons.api.util.benchmark;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class TimeMeasure {

	public String name;
	public long amount = 1;
	public long start;
	public long end;

	public long avg() {
		if (this.amount == 0) {
			return Long.MAX_VALUE;
		}
		return this.total() / this.amount;
	}

	public long total() {
		return this.end - this.start;
	}

	/**
	 * Jan 22, 2013
	 */
	public void end() {
		this.end = System.currentTimeMillis();
	}

	public String toString() {
		return "metric," + ",\tavg:" + this.avg() + ",\tname:" + name + ",\tstart:" + start + ",\tend:" + end
				+ ",\tamount:" + amount + ",\ttotal:" + this.total();
	}
}
