package com.co.core.home.client;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * Home view
 * 
 * presents home page an logo
 * 
 * @author Lucas Reeh
 * 
 */
public class COHomeView extends ViewImpl implements COHomePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, COHomeView> {
	}

	@UiField Image imgLogoBig;
	@UiField Label welcomeLabel;
	@UiField ContentPanel cpMain;
	
	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COHomeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	/**
	 * @return view as widget
	 */
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
