package com.co.core.page.client;

import com.co.client.place.PageNameTokens;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.event.COClientUserChangedEvent;
import com.co.core.auth.client.event.COClientUserChangedEvent.COClientUserChangedHandler;
import com.co.core.auth.client.event.COClientUserLoadedEvent;
import com.co.core.auth.shared.resources.COAuthResources;
import com.co.core.page.client.footer.COPageFooterPresenter;
import com.co.core.page.client.header.COPageHeaderPresenter;
import com.co.core.shared.resources.COResources;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * CO Page Presenter
 * 
 * presents slots for header, footer and main content
 * 
 * @author Lucas Reeh
 *
 */
public class COPagePresenter extends
		Presenter<COPagePresenter.MyView, COPagePresenter.MyProxy> {

	/**
	 * Slot for main content of page
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_mainContent = new Type<RevealContentHandler<?>>();

	/**
	 * Slot for header content of page
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_headerContent = new Type<RevealContentHandler<?>>();

	/**
	 * Slot for footer content of page
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_footerContent = new Type<RevealContentHandler<?>>();

	/**
	 * locale variable for header presenter
	 */
	@Inject
	COPageHeaderPresenter coPageHeaderPresenter;

	/**
	 * local variable for footer presenter
	 */
	@Inject
	COPageFooterPresenter coPageFooterPresenter;

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;

	/**
	 * dispatchAsync
	 */
	@Inject
	DispatchAsync dispatchAsync;
	
	
	/**
	 * current user
	 */
	@Inject
	COClientUserHolder coClientUserHolder;


	/**
	 * handles {@link COClientUserLoadedEvent}
	 */

	/**
	 * handles {@link COClientUserChangedEvent}
	 */
	public final COClientUserChangedHandler coClientUserChangedHandler = new COClientUserChangedHandler() {

		@Override
		public void onCOClientUserChanged(COClientUserChangedEvent event) {
			if (event.getCoClientUser().isLoggedIn()) {
				if (placeManager.getCurrentPlaceRequest().getNameToken() == PageNameTokens
						.getHome()) {
					PlaceRequest request = new PlaceRequest(PageNameTokens.getModrechome());
					placeManager.revealPlace(request);
				} else {
					placeManager.revealCurrentPlace();
				}
				Info.display(COResources.TEXT.info(),
						COAuthResources.TEXT.msgSucessfullyLoggedIn());
			} else {
				PlaceRequest request = new PlaceRequest(PageNameTokens.getHome());
				placeManager.revealPlace(request);
				Info.display(COResources.TEXT.info(),
						COAuthResources.TEXT.msgSucessfullyLoggedOut());
			}
			
		}	
	};

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {

		public void setFooterHidden(Boolean footerHidden);

		public void setHeaderHidden(Boolean headerHidden);

		public SimpleLayoutPanel getHeaderContentPanel();

		public SimpleLayoutPanel getMainContentPanel();

		public SimpleLayoutPanel getFooterContentPanel();

	}

	/**
	 * code split proxy
	 * 
	 * @author Lucas Reeh
	 *
	 */
	@ProxyCodeSplit
	public interface MyProxy extends Proxy<COPagePresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public COPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * reveal in parent
	 */
	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}
	
	/**
	 * bindings, registering of handlers and selection handlers of user interface elements
	 */
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(
				COClientUserChangedEvent.getType(),
				coClientUserChangedHandler));
	}

	/**
	 * setting initial contents of view elements
	 */
	@Override
	protected void onReset() {
		super.onReset();
	}
	
	/**
	 * onReveal
	 * 
	 * setting header, footer in slots, load user from server
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		setInSlot(COPagePresenter.SLOT_headerContent, coPageHeaderPresenter);
		setInSlot(COPagePresenter.SLOT_footerContent, coPageFooterPresenter);
		
		COClientUserLoadedEvent coClientUserLoadedEvent = new COClientUserLoadedEvent(coClientUserHolder.getUser());
		getEventBus().fireEvent(coClientUserLoadedEvent);
	}
	
	

}
