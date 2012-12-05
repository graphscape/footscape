/**
 * Jul 8, 2012
 */
package com.fs.commons.api.mask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu
 * 
 */
public class Mask {

	private int value;

	private Mask(int vale) {
		this.value = vale;
	}

	public static Mask valueOf(int v) {
		return new Mask(v);
	}

	public Mask and(Mask m) {
		return Mask.valueOf(this.value & m.value);
	}

	public Mask or(Mask m) {
		return Mask.valueOf(this.value | m.value);
	}

	public int value() {
		return this.value;
	}

	public boolean hasOne() {
		return this.value != 0;
	}

	public boolean isZero() {
		return this.value == 0;
	}

	@Override
	public String toString() {
		return Integer.toBinaryString(this.value);

	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null | !(obj instanceof Mask)) {
			return false;
		}

		return this.value == ((Mask) obj).value;
	}

	public boolean isAtom() {
		return this.split().size() == 1;
	}

	public List<Mask> split() {
		int idx = 0x1;
		List<Mask> rt = new ArrayList<Mask>();
		int remain = Integer.MAX_VALUE;
		while (true) {
			int v = this.value & idx;
			if (v != 0) {
				rt.add(Mask.valueOf(idx));
			}
			idx = idx << 1;
			if ((remain | v) == 0) {
				break;
			}
			remain = remain << 1;
		}
		return rt;
	}
}
