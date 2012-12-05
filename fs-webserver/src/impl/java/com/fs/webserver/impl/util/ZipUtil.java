/**
 * Jul 13, 2012
 */
package com.fs.webserver.impl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ZipUtil {

	public static void main(String[] args) throws Exception {
		String zip = "/home/wu/tmp/zip1.jar";
		String dest = "/home/wu/tmp/zipxx/";
		File destF = new File(dest);
		if (!destF.exists()) {
			destF.mkdirs();
		}

		InputStream is = new FileInputStream(zip);
		extract(is, destF);
	}

	public static void extract(InputStream is, File dest) throws IOException {

		byte[] buf = new byte[1024];
		ZipInputStream zipinputstream = null;
		ZipEntry zipentry;
		zipinputstream = new ZipInputStream(is);

		zipentry = zipinputstream.getNextEntry();
		while (zipentry != null) {
			// for each entry to be extracted
			String entryName = zipentry.getName();
			entryName = entryName.replace('/', File.separatorChar);
			entryName = entryName.replace('\\', File.separatorChar);
			String target = dest.getAbsolutePath() + File.separator + entryName;

			System.out.println("entryname " + entryName);
			int n;
			FileOutputStream fileoutputstream;
			File newFile = new File(target);
			if (zipentry.isDirectory()) {
				if (!newFile.exists()) {
					newFile.mkdirs();
				}
				zipentry = zipinputstream.getNextEntry();
				continue;
			}
			File pfile = newFile.getParentFile();
			if (!pfile.exists()) {
				if (!pfile.mkdirs()) {
					throw new FsException("mkdirs failed:" + pfile);
				}
			}

			newFile.createNewFile();//

			fileoutputstream = new FileOutputStream(newFile);

			while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
				fileoutputstream.write(buf, 0, n);
			}

			fileoutputstream.close();
			zipinputstream.closeEntry();
			zipentry = zipinputstream.getNextEntry();

		}// while

		zipinputstream.close();

	}

}
