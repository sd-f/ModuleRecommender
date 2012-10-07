package com.co.app.modrec.client.ui.widgets;

import com.co.core.home.shared.resources.COHomeResources;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * Presenter for Module Recommender Home Screen
 * 
 * logo and welcome page
 * 
 * @author Lucas Reeh
 * 
 */
public class ModRecWelcomePresenter extends
		PresenterWidget<ModRecWelcomePresenter.MyView> {

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
	 * class constructor
	 */
	@Inject
	public ModRecWelcomePresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
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
