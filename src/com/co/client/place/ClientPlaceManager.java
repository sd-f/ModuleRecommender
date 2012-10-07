package com.co.client.place;

import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.co.client.place.DefaultPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * the client place manager
 * 
 * handles invalid tokens, no authorization fall back and 
 * default place reveal
 * 
 * @author Lucas Reeh
 *
 */
public class ClientPlaceManager extends PlaceManagerImpl {

	private final PlaceRequest defaultPlaceRequest;

	@Inject
	public ClientPlaceManager(final EventBus eventBus,
			final TokenFormatter tokenFormatter,
			@DefaultPlace final String defaultPlaceNameToken) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest(defaultPlaceNameToken);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest, false);
	}

	/**
	 * if invalid token is given page not found place will be revealed
	 */
	@Override
	public void revealErrorPlace(String invalidHistoryToken) {
		PlaceRequest request = new PlaceRequest(PageNameTokens.getPagenotfound());
		revealPlace(request);
	}
	
	/**
	 * if gate keeper not reveals site user will be redirected to home
	 * place, but token kill be kept in URL (see 2nd parameter is false in 
	 * revealPlace)
	 */
	@Override
	public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
		PlaceRequest request = new PlaceRequest(PageNameTokens.getNotauthorized());
		revealPlace(request, false);
	}
	
}
