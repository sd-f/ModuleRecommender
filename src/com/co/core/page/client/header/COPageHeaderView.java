package com.co.core.page.client.header;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * CO Page Header view
 * 
 * view representing page header
 * 
 * @author Lucas Reeh
 *
 */
public class COPageHeaderView extends ViewImpl implements
		COPageHeaderPresenter.MyView {

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
	public interface Binder extends UiBinder<Widget, COPageHeaderView> {
	}

	/**
	 * container holding header's left content
	 */
	@UiField SimplePanel headerLeftContainer;
	
	/**
	 * container holding header's center content
	 */
	@UiField SimplePanel headerCenterContainer;
	
	/**
	 * container holding header's right content
	 */
	@UiField SimplePanel headerRightContainer;
	
	/**
	 * container holding header's center right content
	 */
	@UiField SimplePanel headerCenterRightContainer;

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COPageHeaderView(final Binder binder) {
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
	 * @return the {@link COPageHeaderView#headerLeftContainer}
	 */
	public SimplePanel getHeaderLeftContainer() {
		return headerLeftContainer;
	}

	/**
	 * @return the {@link COPageHeaderView#headerCenterContainer}
	 */
	public SimplePanel getHeaderCenterContainer() {
		return headerCenterContainer;
	}

	/**
	 * @return the {@link COPageHeaderView#headerRightContainer}
	 */
	public SimplePanel getHeaderRightContainer() {
		return headerRightContainer;
	}
	
	/**
	 * @return the {@link COPageHeaderView#headerCenterRightContainer}
	 */
	public SimplePanel getHeaderCenterRightContainer() {
		return headerCenterRightContainer;
	}
	
	/**
	 * set content in slot depending on given slot
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == COPageHeaderPresenter.SLOT_leftContent) {
			setLeftContent(content);
		} else if (slot == COPageHeaderPresenter.SLOT_centerContent) {
			setCenterContent(content);
		} else if (slot == COPageHeaderPresenter.SLOT_rightContent) {
			setRightContent(content);
		} else if (slot == COPageHeaderPresenter.SLOT_centerRightContent) {
			setCenterRightContent(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	/**
	 * set content in right content panel
	 * 
	 * @param content
	 */
	private void setRightContent(Widget content) {
		headerRightContainer.clear();
		if (content != null) {
			headerRightContainer.add(content);
		}
	}

	/**
	 * set content in center content panel
	 * 
	 * @param content
	 */
	private void setCenterContent(Widget content) {
		headerCenterContainer.clear();
		if (content != null) {
			headerCenterContainer.add(content);
		}
	}

	/**
	 * set content in left content panel
	 * 
	 * @param content
	 */
	private void setLeftContent(Widget content) {
		headerLeftContainer.clear();
		if (content != null) {
			headerLeftContainer.add(content);
		}
		
	}
	
	/**
	 * set content in center right content panel
	 * 
	 * @param content
	 */
	private void setCenterRightContent(Widget content) {
		headerCenterRightContainer.clear();
		if (content != null) {
			headerCenterRightContainer.add(content);
		}
		
	}


}
