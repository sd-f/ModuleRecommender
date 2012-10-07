/**
 * 
 */
package com.co.core.server.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * @author Lucas Reeh
 *
 */
public class LoquaciousExceptionHandler implements ExceptionHandler {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(LoquaciousExceptionHandler.class);

	@Override
	public ServerFailure createServerFailure(Throwable throwable) {
		LOG.error("Server error", throwable);
		return new ServerFailure(throwable.getMessage(), throwable
				.getClass().getName(), null, true);
	}
}
