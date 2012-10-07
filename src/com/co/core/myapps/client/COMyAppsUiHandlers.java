/**
 * 
 */
package com.co.core.myapps.client;

import com.gwtplatform.mvp.client.UiHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Interface My Apps interface Handlers
 * 
 * interface holding all user interface Handlers of the My Apps module
 * 
 * @author Lucas Reeh
 *
 */
public interface COMyAppsUiHandlers extends UiHandlers {

	/**
	 * user pressed app button
	 */
	void onAppModRecPressed(SelectEvent event);

	
}
