package com.co.core.home.client;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.co.app.modrec.client.ModRecHomePresenter;
import com.co.client.place.PageNameTokens;
import com.co.core.home.shared.resources.COHomeResources;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * Home Page Presenter
 * 
 * presents home page welcome text and logo
 * 
 * @author Lucas Reeh
 *
 */
public class COHomePresenter extends
	Presenter<COHomePresenter.MyView, COHomePresenter.MyProxy> {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {
		
		public Image getImgLogoBig();

		public Label getWelcomeLabel();

		public ContentPanel getCpMain();
	}

	/**
	 * code split interface
	 * 
	 * @author Lucas Reeh
	 *
	 */
	@ProxyCodeSplit
	@NameToken(PageNameTokens.home)
	public interface MyProxy extends ProxyPlace<COHomePresenter> {
	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public COHomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * revealInParent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, ModRecHomePresenter.SLOT_mainContent, this);
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
	}
	
	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();
		getView().getCpMain().setHeadingText(COHomeResources.TEXT.homeTextWelcome());
		getView().getWelcomeLabel().setText(COHomeResources.TEXT.homeTextWelcome());
	}
}
