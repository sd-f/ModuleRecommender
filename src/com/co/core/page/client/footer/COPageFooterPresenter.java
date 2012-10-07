package com.co.core.page.client.footer;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.co.core.page.client.COPagePresenter;
import com.co.core.page.shared.resources.COPageResources;
import com.co.core.shared.resources.COResources;

/**
 * CO Page Footer presenter
 * 
 * presenter for page footer
 * 
 * @author Lucas Reeh
 * 
 */
public class COPageFooterPresenter extends
		Presenter<COPageFooterPresenter.MyView, COPageFooterPresenter.MyProxy> {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {

		/**
		 * @return label holding copyright text
		 */
		public Label getLbCopyright();

		/**
		 * @return label holding support text
		 */
		public Anchor getAnchorSupport();
	}

	/**
	 * code split proxy
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	@ProxyCodeSplit
	public interface MyProxy extends Proxy<COPageFooterPresenter> {
	}

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;
	
	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public COPageFooterPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * reveal in parent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, COPagePresenter.SLOT_footerContent, this);
	}

	/**
	 * bindings, registering of handlers and selection handlers of user
	 * interface elements
	 */
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * setting initial contents of view elements
	 */
	@Override
	protected void onReset() {
		super.onReset();
		getView().getAnchorSupport().setText(COResources.TEXT.support());
		getView().getAnchorSupport().setHref(
				"mailto:" + COResources.TEXT.support_email_adress()
				+ "?Subject=" + COResources.TEXT.support_email_subject()
				+ "[App:"+placeManager.getCurrentPlaceRequest().getNameToken()+"]");
		getView().getLbCopyright()
				.setText(COPageResources.TEXT.copyrightText());
	}
}
