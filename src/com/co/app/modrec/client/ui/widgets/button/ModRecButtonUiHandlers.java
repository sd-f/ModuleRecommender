/**
 * 
 */
package com.co.app.modrec.client.ui.widgets.button;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.gwtplatform.mvp.client.UiHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Interface CO MyApps user interface Handlers
 * 
 * interface holding all user interface Handlers of ModRecButtonPresenter
 * 
 * @author Lucas Reeh
 *
 */
public interface ModRecButtonUiHandlers extends UiHandlers {

	/**
	 * user pressed button
	 */
	void onButtonPressed(SelectEvent event);
	
	/**
	 * user pressed search button
	 */
	void onSearchButtonPressed(SelectEvent event);
	
	/**
	 * user hit enter on textfield
	 */
	void onSearchBoxEnterKeyPressed(KeyDownEvent event);
	
}
