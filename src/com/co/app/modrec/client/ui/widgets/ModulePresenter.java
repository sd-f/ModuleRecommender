package com.co.app.modrec.client.ui.widgets;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.co.client.place.PageNameTokens;
import com.co.core.shared.resources.COResources;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.co.app.cer.client.curriculum.node.ui.grid.CurriculumNodeGridPresenter;
import com.co.app.cer.client.curriculum.node.ui.tree.CurriculumNodeTreePresenter;
import com.co.app.modrec.client.ModRecHomePresenter;
import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;

/**
 * Presenter of Modules tree and grid with toolbar
 * 
 * @author Lucas Reeh
 * 
 */
public class ModulePresenter extends
		Presenter<ModulePresenter.MyView, ModulePresenter.MyProxy> {

	String pCurriculumVersionId = null;
	
	public interface MyView extends View {
		public ContentPanel getCpMain();
	}
	
	/**
	 * Slot for tree
	 */
	public static final Object SLOT_tree = new Object();
	
	/**
	 * Slot for grid
	 */
	public static final Object SLOT_main = new Object();
	
	@Inject
	CurriculumNodeTreePresenter curriculumNodeTreePresenter;
	
	@Inject
	CurriculumNodeGridPresenter curriculumNodeGridPresenter;
	
	@Inject
	CurriculumRequestFactory factory;
	
	@ProxyCodeSplit
	@NameToken(PageNameTokens.modules)
	public interface MyProxy extends ProxyPlace<ModulePresenter> {
	}

	/**
	 * class constructor
	 */
	@Inject
	public ModulePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	/**
	 * revealInParent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, ModRecHomePresenter.SLOT_mainContent, this);
	}

	/**
	 * prepareFromRequest
	 */
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		pCurriculumVersionId = request.getParameter("pCurriculumVersionId", "0");
	}

	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		factory.initialize(getEventBus());
		if (pCurriculumVersionId == null) {
			AlertMessageBox alert = new AlertMessageBox(
    				COResources.TEXT_ERROR.error(), "Parameter: pCurriculumVersionId must not be null");
			alert.show();
		} else if (pCurriculumVersionId.equals("0")) {
			AlertMessageBox alert = new AlertMessageBox(
    				COResources.TEXT_ERROR.error(), "Parameter: pCurriculumVersionId must not be null");
			alert.show();
		} else {
			CurriculumVersionSelectedEvent evt = new CurriculumVersionSelectedEvent(new Long(pCurriculumVersionId));
			getEventBus().fireEvent(evt);
			setInSlot(SLOT_tree, curriculumNodeTreePresenter);
			setInSlot(SLOT_main, curriculumNodeGridPresenter);
			curriculumNodeGridPresenter.setCurriculumVersionId(new Long(pCurriculumVersionId));
			curriculumNodeTreePresenter.setCurriculumVersionIdAndLoad(new Long(pCurriculumVersionId));
		}
	}
}
