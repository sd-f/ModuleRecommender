/**
 * 
 */
package com.co.client.gin;

import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.gatekeeper.LoggedInGatekeeper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Class Client Ginjector Base
 * 
 * @author Lucas Reeh
 *
 */
public interface ClientGinjectorBase extends Ginjector {
	EventBus getEventBus();
	LoggedInGatekeeper getLoggedInGatekeeper();
	PlaceManager getPlaceManager();
	COClientUserHolder getCOClientUserHolder();

}
