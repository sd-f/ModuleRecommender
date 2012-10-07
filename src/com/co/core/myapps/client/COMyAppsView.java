package com.co.core.myapps.client;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Home view
 * 
 * shows available apps
 * 
 * @author Lucas Reeh
 * 
 */
public class COMyAppsView extends ViewWithUiHandlers<COMyAppsUiHandlers>
		implements COMyAppsPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, COMyAppsView> {
	}

	@UiField
	ContentPanel cpMain;

	@UiField
	TextButton btAppModuleRecommender;

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COMyAppsView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		btAppModuleRecommender.setIconAlign(IconAlign.TOP);
	}

	/**
	 * @return view as widget
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the cpMain
	 */
	public ContentPanel getCpMain() {
		return cpMain;
	}

	/**
	 * @return the btAppModuleRecommender
	 */
	public TextButton getBtAppModuleRecommender() {
		return btAppModuleRecommender;
	}

	/**
	 * user pressed app button
	 */
	@UiHandler("btAppModuleRecommender")
	void onAppModRecPressed(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onAppModRecPressed(event);
		}
	}

}
