package com.co.core.page.client.footer;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * CO Page Footer view
 * 
 * view representing page footer
 * 
 * @author Lucas Reeh
 *
 */
public class COPageFooterView extends ViewImpl implements
		COPageFooterPresenter.MyView {

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
	public interface Binder extends UiBinder<Widget, COPageFooterView> {
	}

	/**
	 * Label holding copyright text
	 */
	@UiField Label lbCopyright;
	
	/**
	 * Label holding support text
	 */
	@UiField Anchor aSupport;
	
	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COPageFooterView(final Binder binder) {
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
	 * @return the {@link COPageFooterView#lbCopyright}
	 */
	public Label getLbCopyright() {
		return lbCopyright;
	}

	/**
	 * @return the {@link COPageFooterView#lbSupport}
	 */
	public Anchor getAnchorSupport() {
		return aSupport;
	}
	
	
}
