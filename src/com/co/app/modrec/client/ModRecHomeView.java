package com.co.app.modrec.client;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * View for Presenter of Module Recommender Home
 * 
 * @author Lucas Reeh
 *
 */
public class ModRecHomeView extends ViewImpl implements
		ModRecHomePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ModRecHomeView> {
	}
	
	/** Panel holding content */
	@UiField SimpleLayoutPanel pnContent;

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public ModRecHomeView(final Binder binder) {
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
	 * set content in slot depending on given slot
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ModRecHomePresenter.SLOT_mainContent) {
			pnContent.clear();
			if (content != null) {
				pnContent.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	/**
	 * @return the pnContent
	 */
	public SimpleLayoutPanel getPnContent() {
		return pnContent;
	}
	
}
