package com.co.app.modrec.client.ui.widgets;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * View for Presenter of Module Recommender Home Screen
 * 
 * @author Lucas Reeh
 *
 */
public class ModRecWelcomeView extends ViewImpl implements
		ModRecWelcomePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ModRecWelcomeView> {
	}

	@UiField Image imgLogoBig;
	@UiField Label welcomeLabel;
	@UiField ContentPanel cpMain;
	
	@Inject
	public ModRecWelcomeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the imgLogoBig
	 */
	public Image getImgLogoBig() {
		return imgLogoBig;
	}

	/**
	 * @return the welcomeLabel
	 */
	public Label getWelcomeLabel() {
		return welcomeLabel;
	}

	/**
	 * @return the cpMain
	 */
	public ContentPanel getCpMain() {
		return cpMain;
	}
}
