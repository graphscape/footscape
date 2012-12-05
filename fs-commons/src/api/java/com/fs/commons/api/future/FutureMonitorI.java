/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 4, 2012
 */
package com.fs.commons.api.future;

import java.util.concurrent.Future;

/**
 * @author wuzhen
 * 
 */
public interface FutureMonitorI {

	public void addFuture(Object source, Future<?> f);

}
