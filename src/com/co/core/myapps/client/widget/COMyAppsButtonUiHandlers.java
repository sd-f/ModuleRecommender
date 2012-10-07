/**
 * 
 */
package com.co.core.myapps.client.widget;

import com.gwtplatform.mvp.client.UiHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Interface CO MyApps user interface Handlers
 * 
 * interface holding all user interface Handlers of the CO MyApps
 * 
 * @author Lucas Reeh
 *
 */
public interface COMyAppsButtonUiHandlers extends UiHandlers {

	/**
	 * user pressed button
	 */
	void onButtonPressed(SelectEvent event);
	
}
