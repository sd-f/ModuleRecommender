/**
 * 
 */
package com.co.client.exception;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.web.bindery.event.shared.UmbrellaException;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * @author Lucas Reeh
 *
 */
public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {
	
  @Override
  public void onUncaughtException( Throwable e ) {
    // Get rid of UmbrellaException
    Throwable exceptionToDisplay = getExceptionToDisplay( e );
    Info.display("Debug", exceptionToDisplay.getMessage());
    GWT.log(e.getLocalizedMessage() + " : " + exceptionToDisplay.getMessage());
  }

  /**
   * remove useless ubrella output
   * @param throwable
   * @return
   */
  private static Throwable getExceptionToDisplay( Throwable throwable ) {
    Throwable result = throwable;
    if (throwable instanceof UmbrellaException && ((UmbrellaException) throwable).getCauses().size() == 1) {
      result = ((UmbrellaException) throwable).getCauses().iterator().next();
    }
    return result;
  }
}