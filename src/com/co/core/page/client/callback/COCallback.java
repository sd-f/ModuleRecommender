/**
 * 
 */
package com.co.core.page.client.callback;

import com.co.core.shared.resources.COResources;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * Class CO modified callbacks
 * 
 * @author Lucas Reeh
 *
 */
public abstract class COCallback<T> implements AsyncCallback<T> {

	/**
	 * Class constructor
	 */
	public COCallback() {
	}
	
	/**
	 * onFailure
	 */
	@Override
    public void onFailure(Throwable caught) {
            callbackError(caught);
    }
	
	/**
	 * onSuccess
	 */
	@Override
    public void onSuccess(T result) {
            callback(result);
    }

	/**
     * Must be overriden by clients to handle callbacks
     * @param result
     */
    public abstract void callback(T result);
	
	/**
	 * error handling
	 * 
	 * @param caught
	 */
	public void callbackError(Throwable caught) {
    	caught.printStackTrace();
    	AlertMessageBox alert = new AlertMessageBox(
				COResources.TEXT_ERROR.error(), caught
						.getLocalizedMessage());
		alert.show();
    }
}