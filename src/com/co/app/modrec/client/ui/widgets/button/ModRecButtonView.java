package com.co.app.modrec.client.ui.widgets.button;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Module Recommender Button View
 * 
 * holds widget for Module Recommender button and search box
 * 
 * @author Lucas Reeh
 *
 */
public class ModRecButtonView extends ViewWithUiHandlers<ModRecButtonUiHandlers> implements
		ModRecButtonPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ModRecButtonView> {
	}

	@UiField
	TextButton btCurriculumVersionSelection;
	
	@UiField
	TextButton btSearch;
	
	@UiField
	TextField tfSearchString;


	/**
	 * Class constructor
	 */
	@Inject
	public ModRecButtonView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	/**
	 * asWidget
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}
	
	/**
	 * user pressed sign in button
	 */
	@UiHandler("btCurriculumVersionSelection")
	void onButtonPressed(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onButtonPressed(event);
		}
	}
	
	/**
	 * @return the btCOMyApps
	 */
	public TextButton getBtCurriculumVersionSelection() {
		return btCurriculumVersionSelection;
	}

	/**
	 * @return the btSearch
	 */
	public TextButton getBtSearch() {
		return btSearch;
	}

	/**
	 * @return the tfSearchString
	 */
	public TextField getTfSearchString() {
		return tfSearchString;
	}
	
	/**
	 * user pressed sign in button
	 */
	@UiHandler("btSearch")
	void onSearchButtonPressed(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onSearchButtonPressed(event);
		}
	}
	
	/**
	 * user pressed sign in button
	 */
	@UiHandler("tfSearchString")
	void onSearchBoxEnterKeyPressed(KeyDownEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onSearchBoxEnterKeyPressed(event);
		}
	}
}
