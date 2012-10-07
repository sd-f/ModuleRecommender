package com.co.app.cer.client.curriculum.node.ui.tree;

import com.co.app.cer.client.curriculum.node.CurriculumNodeProxySimpleProperties;
import com.co.app.cer.client.curriculum.node.CurriculumNodeTypeIcon;
import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckCascade;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckNodes;

/**
 * View for Presenter Widget for curriculum node checkbox tree
 * 
 * rule nodes will be loaded in tree to select super nodes for grid content
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumNodeTreeView extends ViewImpl implements
		CurriculumNodeTreePresenter.MyView {

	/** key provide for tree grid */
	class KeyProvider implements ModelKeyProvider<CurriculumNodeProxy> {

		@Override
		public String getKey(CurriculumNodeProxy item) {
			return item.getId().toString();
		}

	}

	/** creating properties class */
	CurriculumNodeProxySimpleProperties props = GWT
			.create(CurriculumNodeProxySimpleProperties.class);

	TreeStore<CurriculumNodeProxy> store = new TreeStore<CurriculumNodeProxy>(
			props.id());

	/**
	 * @return the store
	 */
	public TreeStore<CurriculumNodeProxy> getStore() {
		return store;
	}

	ColumnModel<CurriculumNodeProxy> cm;

	Tree<CurriculumNodeProxy, String> tree;

	@UiField
	VerticalPanel panel;

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CurriculumNodeTreeView> {
	}

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public CurriculumNodeTreeView(final Binder binder) {

		// tree comes pre sorted from server
		tree = new Tree<CurriculumNodeProxy, String>(store,
				new ValueProvider<CurriculumNodeProxy, String>() {
					@Override
					public String getValue(CurriculumNodeProxy object) {
						return object.getName();
					}

					@Override
					public void setValue(CurriculumNodeProxy object,
							String value) {

					}

					@Override
					public String getPath() {
						return "name";
					}
				});
		tree.setIconProvider(new IconProvider<CurriculumNodeProxy>() {
			@Override
			public ImageResource getIcon(CurriculumNodeProxy model) {
				CurriculumNodeTypeIcon icon = new CurriculumNodeTypeIcon(model
						.getType());
				return icon.getImageResource();
			}

		});

		// checkbox tree configuration
		tree.setCheckable(true);
		tree.setCheckNodes(CheckNodes.BOTH);
		tree.setCheckStyle(CheckCascade.NONE);
		tree.setAutoLoad(true);

		// expanding context menu in tree, events should be handled in presenter
		// (UI-Handler Class)
		Menu contextMenu = new Menu();
		MenuItem expandAll = new MenuItem(COCerResources.TEXT.expandAll());
		expandAll.setIcon(COCerResources.IMAGES.arrow_out());
		expandAll.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if (tree.getSelectionModel().getSelectedItem()
						.equals(store.getRootItems().get(0))) {
					tree.expandAll();
				} else {
					for (CurriculumNodeProxy node : tree.getSelectionModel()
							.getSelectedItems()) {
						tree.setExpanded(node, true, true);
					}

				}
			}
		});
		contextMenu.add(expandAll);
		MenuItem collapseAll = new MenuItem(COCerResources.TEXT.collapseAll());
		collapseAll.setIcon(COCerResources.IMAGES.arrow_in());
		collapseAll.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				if (tree.getSelectionModel().getSelectedItem()
						.equals(store.getRootItems().get(0))) {
					tree.collapseAll();
				} else {
					for (CurriculumNodeProxy node : tree.getSelectionModel()
							.getSelectedItems()) {
						tree.setExpanded(node, false, true);
					}

				}

			}
		});
		contextMenu.add(collapseAll);
		tree.setContextMenu(contextMenu);

		widget = binder.createAndBindUi(this);

		panel.add(tree);
	}

	/**
	 * @return view as widget
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the tree
	 */
	public Tree<CurriculumNodeProxy, String> getTree() {
		return tree;
	}
}
