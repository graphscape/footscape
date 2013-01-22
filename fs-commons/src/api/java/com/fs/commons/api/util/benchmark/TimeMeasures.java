/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 22, 2013
 */
package com.fs.commons.api.util.benchmark;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 * 
 */
public class TimeMeasures {
	Map<String, TimeMeasure> metricMap = new HashMap<String, TimeMeasure>();

	public void start(String name) {
		get(name).start = System.currentTimeMillis();
	}

	public synchronized TimeMeasure get(String name) {

		TimeMeasure m = this.metricMap.get(name);
		if (m == null) {
			m = new TimeMeasure();
			m.name = name;
			this.metricMap.put(name, m);
		}

		return m;
	}

	public void end(String name, int ammount) {

		TimeMeasure m = get(name);
		m.amount = ammount;
		m.end();
	}

	public void end(String name) {
		this.get(name).end();
	}

	public void print() {
		for (TimeMeasure m : this.metricMap.values()) {
			System.out.println(m);
		}
	}
}
