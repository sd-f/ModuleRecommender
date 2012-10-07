package com.co.core.page.client.header;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.co.app.modrec.client.ui.widgets.button.ModRecButtonPresenter;
import com.co.core.auth.client.COLoginBarPresenter;
import com.co.core.auth.client.COSessionControllerPresenter;
import com.co.core.auth.client.event.COClientUserChangedEvent;
import com.co.core.auth.client.event.COClientUserLoadedEvent;
import com.co.core.auth.client.event.COClientUserChangedEvent.COClientUserChangedHandler;
import com.co.core.auth.client.event.COClientUserLoadedEvent.COClientUserLoadedHandler;
import com.co.core.auth.shared.COClientUserModel;
import com.co.core.page.client.COPagePresenter;

/**
 * CO Page Header presenter
 * 
 * presenter for page header
 * 
 * @author Lucas Reeh
 *
 */
public class COPageHeaderPresenter extends
		Presenter<COPageHeaderPresenter.MyView, COPageHeaderPresenter.MyProxy> {

	/**
	 * Slot for left header content
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_leftContent = new Type<RevealContentHandler<?>>();
	
	/**
	 * Slot for center header content
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_centerContent = new Type<RevealContentHandler<?>>();
	
	
	/**
	 * Slot for right centered content
	 */
	public static final Object SLOT_centerRightContent = new Object();
	
	/**
	 * Slot for right header content - presenter widget
	 */
	public static final Object SLOT_rightContent = new Object();
	
	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {
		
		public SimplePanel getHeaderLeftContainer();
		
		public SimplePanel getHeaderCenterContainer();
		
		public SimplePanel getHeaderRightContainer();
		
		public SimplePanel getHeaderCenterRightContainer();
		
	}
	
	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;
	
	/**
	 * handles {@link COClientUserChangedEvent}
	 */
	public final COClientUserChangedHandler coClientUserChangedHandler = new COClientUserChangedHandler() {

		/**
		 * sets header content in right content slot
		 */
		@Override
		public void onCOClientUserChanged(COClientUserChangedEvent event) {
			setRightContentInSlot(event.getCoClientUser());
		}
			
	};
	
	/**
	 * handles {@link COClientUserLoadedEvent}
	 */
	public final COClientUserLoadedHandler coClientUserLoadedHandler = new COClientUserLoadedHandler() {
		
		/**
		 * sets header content in right content slot
		 */
		@Override
		public void onCOClientUserLoaded(COClientUserLoadedEvent event) {
			setRightContentInSlot(event.getCoClientUser());
		}
		
	};
	
	/**
	 * locale my apps button presenter widget
	 */
	@Inject
	ModRecButtonPresenter coModRecButtonPresenter;
	
	/**
	 * locale login bar presenter widget
	 */
	@Inject
	COLoginBarPresenter coLoginBarPresenter;
	
	/**
	 * local controller presenter widget
	 */
	@Inject
	COSessionControllerPresenter coSessionControllerPresenter;

	
	/**
	 * code split proxy
	 * 
	 * @author Lucas Reeh
	 *
	 */
	@ProxyCodeSplit
	public interface MyProxy extends Proxy<COPageHeaderPresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public COPageHeaderPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * reveal in parent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, COPagePresenter.SLOT_headerContent, this);
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
		registerHandler(getEventBus().addHandler(
				COClientUserLoadedEvent.getType(),
				coClientUserLoadedHandler));
	}
	
	/**
	 * setting initial contents of view elements
	 */
	@Override
	protected void onReset() {
		super.onReset();
	}
	
	/**
	 * set Content in right content slot according to users logged in state
	 * 
	 * @param coClientUser
	 */
	private void setRightContentInSlot(COClientUserModel coClientUser) {
		if (coClientUser.isLoggedIn()) {
			
			setInSlot(COPageHeaderPresenter.SLOT_rightContent, coSessionControllerPresenter);
		} else {
			setInSlot(COPageHeaderPresenter.SLOT_rightContent, coLoginBarPresenter);
			setInSlot(COPageHeaderPresenter.SLOT_leftContent, null);
		}
		setInSlot(COPageHeaderPresenter.SLOT_leftContent, coModRecButtonPresenter);
	}
	
}
