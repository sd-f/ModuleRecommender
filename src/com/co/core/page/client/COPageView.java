package com.co.core.page.client;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * CO Page view
 * 
 * view representing main page contents, header and footer
 * 
 * @author Lucas Reeh
 *
 */
public class COPageView extends ViewImpl implements COPagePresenter.MyView {
	
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
	public interface Binder extends UiBinder<Widget, COPageView> {
	}
	
	/**
	 * is footer set to hidden
	 */
	private Boolean footerHidden = false;
	
	/**
	 * is header set to hidden
	 */
	private Boolean headerHidden = false;
	
	/**
	 * panel holding header content
	 */
	@UiField SimpleLayoutPanel headerContentPanel;
	
	/**
	 * panel holding main content
	 */
	@UiField SimpleLayoutPanel mainContentPanel;
	
	/**
	 * panel holding footer content
	 */
	@UiField SimpleLayoutPanel footerContentPanel;
	
	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public COPageView(final Binder binder) {
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
	 * @return {@link COPageView#headerContentPanel}
	 */
	public SimpleLayoutPanel getHeaderContentPanel() {
		return headerContentPanel;
	}

	/**
	 * @return {@link COPageView#mainContentPanel}
	 */
	public SimpleLayoutPanel getMainContentPanel() {
		return mainContentPanel;
	}

	/**
	 * @return {@link COPageView#footerContentPanel}
	 */
	public SimpleLayoutPanel getFooterContentPanel() {
		return footerContentPanel;
	}
	
	/**
	 * setting content widget in the right slot
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		/* setting main slot content */
		if (slot == COPagePresenter.SLOT_mainContent) {
			setMainContent(content);
		} else if (slot == COPagePresenter.SLOT_headerContent) {
			/* same as above but with according content panel*/
			setHeaderContent(content);
		} else if (slot == COPagePresenter.SLOT_footerContent) {
			/* same as above but with according content panel*/
			setFooterContent(content);
		} else {
			super.setInSlot(slot, content);
		}
	}
	
	/**
	 * sets given content widget in Main Container
	 * 
	 * @param content
	 */
	private void setMainContent(Widget content) {
		/* clear content on slot */
		mainContentPanel.clear();
		/* to clear slot content with null parameter we check if it is null */
		if (content != null) {
			mainContentPanel.add(content);
		}
	}
	
	/**
	 * sets given content widget in Header Container
	 * 
	 * @param content
	 */
	private void setHeaderContent(Widget content) {
		/* clear content on slot */
		headerContentPanel.clear();
		/* if headerHidden is not set to true add to header Panel */
		if ((content != null) && (!headerHidden)) {
			headerContentPanel.add(content);
		}
	}
	
	/**
	 * sets given content widget in Footer Container
	 * 
	 * @param content
	 */
	private void setFooterContent(Widget content) {
		/* clear content on slot */
		footerContentPanel.clear();
		/* if footerHidden is not set to true add to header Panel */
		if ((content != null) && (!footerHidden)) {
			footerContentPanel.add(content);
		}
	}
	
	/**
	 * set footer hidden
	 */
	public void setFooterHidden(Boolean footerHidden) {
		this.footerHidden = footerHidden;
	}
	
	/**
	 * set header hidden
	 */
	public void setHeaderHidden(Boolean headerHidden) {
		this.headerHidden = headerHidden;
	}
	
}
