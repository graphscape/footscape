/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class Tmp extends TestCase {
	final int PLUS = 0;
	final int MINUS = 1;
	final int MULTIPLE = 2;
	final int DIVIDE = 3;

	public void test() {

		for (int j1 = 0; j1 < 4; j1++) {

			for (int j2 = 0; j2 < 4; j2++) {
				for (int j = 0; j < 4; j2++) {
					for (int j3 = 0; j3 < 4; j3++) {
						for (int j4 = 0; j4 < 4; j4++) {
							Integer total = 9;
							total = op(total, j1, 9);
							if (total == null) {
								continue;
							}
							total = op(total, j2, 9);
							if (total == null) {
								continue;
							}
							total = op(total, j3, 9);
							if (total == null) {
								continue;
							}
							total = op(total, j4, 9);
							if (total == null) {
								continue;
							}
							if (total == 12) {
								System.out.println(j1 + "," + j2 + "," + j3
										+ "," + j4);
								return;

							}
						}
					}

				}

			}

		}
	}

	public void test(int numbers) {
		if (numbers == 4) {

		}
	}

	public Integer op(int left, int opcode, int right) {
		switch (opcode) {
		case PLUS:
			return left + right;
		case MINUS:
			return left - right;
		case MULTIPLE:
			return left * right;
		case DIVIDE:
			if (right == 0) {
				return null;
			}
			return left / right;
		default:
			throw new RuntimeException("no op:" + opcode);
		}
	}
}
