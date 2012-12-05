/**
 * Jul 7, 2012
 */
package com.fs.filesystem.impl.test.cases;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.junit.Test;

import com.fs.filesystem.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class FileSystemTest extends TestBase {

	@Test
	public void test() throws Exception {
		this.fs.delete("/", true);// NOTE
		String msg = "hello";
		String path = "/test/tmp-2.txt";
		DataOutputStream out = this.fs.create(path);
		out.writeUTF(msg);
		out.close();
		DataInputStream di = this.fs.open(path);
		String txt = di.readUTF();
		out.close();
		assertEquals(msg, txt);

		FSDataOutputStream fsd;

	}

}
