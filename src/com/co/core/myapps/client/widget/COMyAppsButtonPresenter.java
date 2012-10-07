package com.co.core.myapps.client.widget;

import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.client.place.PageNameTokens;
import com.co.core.myapps.client.resources.COMyAppsResources;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Button for Link to My Apps Presenter
 * 
 * 
 * @author Lucas Reeh
 *
 */
public class COMyAppsButtonPresenter extends
		PresenterWidget<COMyAppsButtonPresenter.MyView> implements COMyAppsButtonUiHandlers {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View, HasUiHandlers<COMyAppsButtonUiHandlers> {
		
		public TextButton getBtCOMyApps();
		
	}

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;
	
	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public COMyAppsButtonPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();
		getView().getBtCOMyApps().setText(COMyAppsResources.TEXT.myAppsTitle());
		getView().getBtCOMyApps().setIcon(COModuleRecommenderResources.IMAGES.app_module_recommender_16());
		getView().getBtCOMyApps().setIconAlign(IconAlign.LEFT);
	}

	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
	}

	/**
	 * onButtonPressed ui event from view 
	 * 
	 * @param event
	 */
	@Override
	public void onButtonPressed(SelectEvent event) {
		PlaceRequest request = new PlaceRequest(PageNameTokens.getModrechome());
		placeManager.revealPlace(request);
	}
}
