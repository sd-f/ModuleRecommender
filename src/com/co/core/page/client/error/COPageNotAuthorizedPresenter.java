package com.co.core.page.client.error;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.co.client.place.PageNameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.co.core.auth.shared.resources.COAuthResources;
import com.co.core.page.client.COPagePresenter;
import com.co.core.shared.resources.COResources;

/**
 * CO Page Not Authorized Presenter
 * 
 * presenter for place if user is not authorized for call page
 * 
 * @author Lucas Reeh
 *
 */
public class COPageNotAuthorizedPresenter
		extends
		Presenter<COPageNotAuthorizedPresenter.MyView, COPageNotAuthorizedPresenter.MyProxy> {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {
		
		/**
		 * @return label for holding not authorized text
		 */
		public Label getLbNotAuthorizedText();
		
		/**
		 * @return content panel of view
		 */
		public ContentPanel getCpMain();
	}

	/**
	 * code split proxy
	 * 
	 * @author Lucas Reeh
	 *
	 */
	@ProxyCodeSplit
	@NameToken(PageNameTokens.notauthorized)
	public interface MyProxy extends ProxyPlace<COPageNotAuthorizedPresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public COPageNotAuthorizedPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * reveal in parent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, COPagePresenter.SLOT_mainContent, this);
	}

	/**
	 * bindings, registering of handlers and selection handlers of user interface elements
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
		getView().getCpMain().setHeadingText(COResources.TEXT_ERROR.error());
		getView().getLbNotAuthorizedText().setText(COAuthResources.TEXT.msgPleaseLogIn());
	}
}
