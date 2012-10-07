package com.co.core.myapps.client;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.co.client.place.PageNameTokens;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.co.core.auth.client.gatekeeper.LoggedInGatekeeper;
import com.co.core.myapps.client.resources.COMyAppsResources;
import com.co.core.page.client.COPagePresenter;

/**
 * My Apps Presenter
 * 
 * presents My Apps view
 * 
 * @author Lucas Reeh
 * 
 */
public class COMyAppsPresenter extends
		Presenter<COMyAppsPresenter.MyView, COMyAppsPresenter.MyProxy>
		implements COMyAppsUiHandlers {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View, HasUiHandlers<COMyAppsUiHandlers> {

		public ContentPanel getCpMain();

		public TextButton getBtAppModuleRecommender();

	}

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;
	
	/**
	 * code split interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	@ProxyCodeSplit
	@NameToken(PageNameTokens.myapps)
	@UseGatekeeper(LoggedInGatekeeper.class)
	public interface MyProxy extends ProxyPlace<COMyAppsPresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public COMyAppsPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}
	
	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();
		getView().getCpMain().setTitle(COMyAppsResources.TEXT.myAppsTitle());
	}

	/**
	 * onAppModRecPressed event fired from view
	 */
	@Override
	public void onAppModRecPressed(SelectEvent event) {
		PlaceRequest request = new PlaceRequest(PageNameTokens.getModrechome());
		placeManager.revealPlace(request);
	}

	/**
	 * revealInParent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, COPagePresenter.SLOT_mainContent, this);
	}

}
