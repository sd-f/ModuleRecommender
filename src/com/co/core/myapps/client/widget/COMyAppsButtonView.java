package com.co.core.myapps.client.widget;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * CO MyApps Button View
 * 
 * holds widget for my apps button
 * 
 * @author Lucas Reeh
 *
 */
public class COMyAppsButtonView extends ViewWithUiHandlers<COMyAppsButtonUiHandlers> implements
		COMyAppsButtonPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, COMyAppsButtonView> {
	}

	@UiField
	TextButton btCOMyApps;


	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COMyAppsButtonView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	/**
	 * user pressed sign in button
	 */
	@UiHandler("btCOMyApps")
	void onButtonPressed(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onButtonPressed(event);
		}
	}
	
	/**
	 * @return the btCOMyApps
	 */
	public TextButton getBtCOMyApps() {
		return btCOMyApps;
	}
}
