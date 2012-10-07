/**
 * 
 */
package com.co.client.factory.util;

import com.co.core.shared.resources.COResources;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * @author Lucas Reeh
 * @param <V>
 *
 */
public abstract class COReceiver<V> extends Receiver<V> {

	@Override
	public void onFailure(ServerFailure error) {
		super.onFailure(error);
		AlertMessageBox alert = new AlertMessageBox(
				COResources.TEXT_ERROR.error(), error.getStackTraceString() + ": " + error.getMessage());
		
	    alert.show();
	}

}
