package com.co.core.page.client.error;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * CO Page Not Authorized view
 * 
 * view representing not authorized page
 * 
 * @author Lucas Reeh
 *
 */
public class COPageNotAuthorizedView extends ViewImpl implements
		COPageNotAuthorizedPresenter.MyView {

	/**
	 * locale widget
	 */
	private final Widget widget;

	/**
	 * user interface binder interface
	 * 
	 * @author Lucas Reeh
	 *
	 */
	public interface Binder extends UiBinder<Widget, COPageNotAuthorizedView> {
	}

	/**
	 * Label holding not authorized text message
	 */
	@UiField Label lbNotAuthorizedText;
	
	/**
	 * content panel of view
	 */
	@UiField ContentPanel cpMain;
	
	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COPageNotAuthorizedView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	/**
	 * returns view as widget
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the {@link COPageNotAuthorizedView#lbNotAuthorizedText}
	 */
	public Label getLbNotAuthorizedText() {
		return lbNotAuthorizedText;
	}
	
	/**
	 * @return the {@link COPageNotAuthorizedView#cpMain}
	 */
	public ContentPanel getCpMain() {
		return cpMain;
	}
	
	
}
