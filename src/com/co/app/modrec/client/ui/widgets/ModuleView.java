package com.co.app.modrec.client.ui.widgets;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;

/**
 * View for Presenter of Modules
 * 
 * @author Lucas Reeh
 *
 */
public class ModuleView extends ViewImpl implements ModulePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ModuleView> {
	}

	@UiField(provided = true)
	BorderLayoutData westData = new BorderLayoutData(300);

	@UiField(provided = true)
	MarginData centerData = new MarginData();

	@UiField
	BorderLayoutContainer con;

	@UiField
	ContentPanel cpMain;

	@UiField
	SimplePanel cpTree;

	@Inject
	public ModuleView(final Binder binder) {
		// ui design
	    westData.setMargins(new Margins(0, 5, 0, 0));
	    westData.setCollapsible(true);
	    westData.setSplit(true);

		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * set content in slot depending on given slot
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ModulePresenter.SLOT_main) {
			cpMain.clear();
			if (content != null) {
				cpMain.add(content);
			}
		} else if (slot == ModulePresenter.SLOT_tree) {
			cpTree.clear();
			if (content != null) {
				cpTree.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	/**
	 * @return the cpMain
	 */
	public ContentPanel getCpMain() {
		return cpMain;
	}

}
