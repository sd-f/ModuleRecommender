package com.co.app.modrec.client.ui.widgets;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.FramedPanel;

/**
 * View for Presenter of Curriculum Version Selection
 * 
 * @author Lucas Reeh
 *
 */
public class CurriculumVersionSelectionView extends ViewImpl implements
		CurriculumVersionSelectionPresenter.MyView {

	private final Widget widget;

	public interface Binder extends
			UiBinder<Widget, CurriculumVersionSelectionView> {
	}

	/** Panel holding list of my cer versions */
	@UiField
	SimplePanel pnMyCurriculumVersionList;

	/** Panel holding list of cer versions */
	@UiField
	SimplePanel pnCurriculumVersionList;

	@UiField
	FramedPanel panel;

	@Inject
	public CurriculumVersionSelectionView(final EventBus eventBus,
			final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the pnMyCurriculumVersionList
	 */
	public SimplePanel getPnMyCurriculumVersionList() {
		return pnMyCurriculumVersionList;
	}

	/**
	 * @return the pnCurriculumVersionList
	 */
	public SimplePanel getPnCurriculumVersionList() {
		return pnCurriculumVersionList;
	}

	/**
	 * set content in slot depending on given slot
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == CurriculumVersionSelectionPresenter.SLOT_curriculumVersionList) {
			pnCurriculumVersionList.clear();
			if (content != null) {
				pnCurriculumVersionList.add(content);
			}
		} else if (slot == CurriculumVersionSelectionPresenter.SLOT_myCurriculumVersionList) {
			pnMyCurriculumVersionList.clear();
			if (content != null) {
				pnMyCurriculumVersionList.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

}
