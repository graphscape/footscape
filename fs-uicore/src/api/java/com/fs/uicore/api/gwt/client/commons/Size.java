/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.commons;

/**
 * @author wu
 * 
 */
public class Size {

	private int width;

	private int height;

	private Size(int w, int h) {
		this.width = w;
		this.height = h;
	}

	/**
	 * @param w
	 * @param h
	 * @return
	 */
	public static Size valueOf(int w, int h) {
		// TODO Auto-generated method stub
		return new Size(w, h);
	}

	public Size multiple(double zoom) {
		return Size.valueOf((int) (this.width * zoom),
				(int) (this.height * zoom));
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the widget
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Size)) {
			return false;
		}
		Size s2 = (Size) obj;
		return s2.height == this.height && s2.width == this.width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Size,width:" + this.width + ",height:" + this.height + "";
	}

}
