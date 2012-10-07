package com.co.app.modrec.client.ui.widgets.button;

import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent;
import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent.CurriculumVersionSelectedHandler;
import com.co.app.modrec.client.events.ModuleSearchEvent;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.client.place.PageNameTokens;
import com.co.core.shared.resources.COResources;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Button for Link to My Apps Presenter
 * 
 * 
 * @author Lucas Reeh
 *
 */
public class ModRecButtonPresenter extends
		PresenterWidget<ModRecButtonPresenter.MyView> implements ModRecButtonUiHandlers {

	
	private Long curriculumVersionId = new Long(0);
	
	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View, HasUiHandlers<ModRecButtonUiHandlers> {
		
		public TextButton getBtCurriculumVersionSelection();
		
		public TextField getTfSearchString();
		
		public TextButton getBtSearch();
		
	}

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;
	
	/**
	 * handles {@link CurriculumVersionSelectedEvent}
	 */
	public final CurriculumVersionSelectedHandler curriculumVersionSelectedHandler = new CurriculumVersionSelectedHandler() {	
		@Override
		public void onCurriculumVersionSelected(CurriculumVersionSelectedEvent event) {
			// show search tools only if curriculum version has been selected before
			curriculumVersionId = event.getCurriculumVersionId();
			if (curriculumVersionId != null) {
				if (!curriculumVersionId.equals(new Long(0))) {
					getView().getBtSearch().setVisible(true);
					getView().getTfSearchString().setVisible(true);
				} else {
					getView().getBtSearch().setVisible(false);
					getView().getTfSearchString().setVisible(false);
				}
			}
			
		}
	};
	
	/**
	 * Class constructor
	 */
	@Inject
	public ModRecButtonPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(
				CurriculumVersionSelectedEvent.getType(),
				curriculumVersionSelectedHandler));
	}

	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();
		getView().getBtCurriculumVersionSelection().setText(COModuleRecommenderResources.TEXT.navElementCurrVersionSelection()+"...");
		getView().getBtCurriculumVersionSelection().setIcon(COModuleRecommenderResources.IMAGES.document_open_24());
		getView().getBtCurriculumVersionSelection().setIconAlign(IconAlign.LEFT);
		getView().getBtSearch().setIcon(COModuleRecommenderResources.IMAGES.loupe_16());
		getView().getBtSearch().setIconAlign(IconAlign.LEFT);
		
	}
	
	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		getView().getBtSearch().setVisible(false);
		getView().getTfSearchString().setVisible(false);
	}

	/**
	 * onButtonPressed ui event from view 
	 * 
	 * @param event
	 */
	@Override
	public void onButtonPressed(SelectEvent event) {
		PlaceRequest request = new PlaceRequest(PageNameTokens.modreccurriculumversionselection);
		placeManager.revealPlace(request);
	}

	
	/**
	 * fires {@link ModuleSearchEvent}
	 */
	private void fireModuleSearchEvent(){
		// check input before firing search event
		if (getView().getTfSearchString().getValue() == null) {
			Info.display(COResources.TEXT_ERROR.error(), COResources.TEXT_ERROR.searchMinChars());
			return;
		}
		if (getView().getTfSearchString().getValue().length() < 3) {
			Info.display(COResources.TEXT_ERROR.error(), COResources.TEXT_ERROR.searchMinChars());
			return;
		}
		if (curriculumVersionId != null) {
			if (!curriculumVersionId.equals(new Long(0))) {
				ModuleSearchEvent moduleSearchEvent = new ModuleSearchEvent(curriculumVersionId, getView().getTfSearchString().getValue() );
				getEventBus().fireEvent(moduleSearchEvent);
			} else {
				Info.display(COResources.TEXT_ERROR.error(), COModuleRecommenderResources.TEXT.noCurriculumId());
			}
		} else {
			Info.display(COResources.TEXT_ERROR.error(), COModuleRecommenderResources.TEXT.noCurriculumId());
		}
	}
	
	/**
	 * onSearchButtonPressed
	 */
	@Override
	public void onSearchButtonPressed(SelectEvent event) {
		fireModuleSearchEvent();
		
	}

	/**
	 * onSearchBoxEnterKeyPressed
	 */
	@Override
	public void onSearchBoxEnterKeyPressed(KeyDownEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			getView().getBtSearch().focus();
			getView().getTfSearchString().finishEditing();
			fireModuleSearchEvent();
		}
	}
}
