/**
 *  
 */
package com.fs.commons.api.components;

import com.fs.commons.api.SPI;
import com.fs.commons.api.config.Configuration;

/**
 * @author wu
 * 
 */
public interface ComponentFactoryI {

	public <T> Class<T> getComponentClass(String key, boolean force);

	public <T> void addComponentClass(String key, Class<T> cls);

	public <T> void addComponentClass(String key, Class<T> cls, boolean replace);

	public <I, T extends I> void addComponentClass(Class<I> key, Class<T> cls);

	public <T> T newComponent(SPI pl, Configuration cfg);

	public <T> T newComponent(SPI pl, Configuration cfg, Class<T> cls);

	public <T> T newComponent(SPI pl, Class<T> cls);

}
