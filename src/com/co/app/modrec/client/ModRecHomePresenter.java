package com.co.app.modrec.client;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.co.app.modrec.client.ui.widgets.ModRecWelcomePresenter;
import com.co.client.place.PageNameTokens;
import com.co.core.page.client.COPagePresenter;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * Presenter of Module Recommender Home
 * 
 * @author Lucas Reeh
 * 
 */
public class ModRecHomePresenter extends
		Presenter<ModRecHomePresenter.MyView, ModRecHomePresenter.MyProxy> {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {

	}

	/**
	 * Slot for navigation Presenter widget
	 */
	public static final Object SLOT_navigation = new Object();

	/**
	 * Slot for main content of page
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_mainContent = new Type<RevealContentHandler<?>>();

	@Inject
	ModRecWelcomePresenter modRecWelcomePresenter;
	
	@ProxyCodeSplit
	@NameToken(PageNameTokens.modrechome)
	public interface MyProxy extends ProxyPlace<ModRecHomePresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public ModRecHomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
	}

	/**
	 * revealInParent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, COPagePresenter.SLOT_mainContent, this);
	}

}
