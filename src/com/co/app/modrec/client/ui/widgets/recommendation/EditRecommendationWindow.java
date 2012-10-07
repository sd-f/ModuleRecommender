/**
 * 
 */
package com.co.app.modrec.client.ui.widgets.recommendation;

import java.util.ArrayList;
import java.util.List;

import com.co.app.cer.client.curriculum.node.CurriculumNodeProxySimpleProperties;
import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.modrec.client.events.RecommendationChangedEvent;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.core.shared.resources.COResources;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.Status.BoxStatusAppearance;
import com.sencha.gxt.widget.core.client.Status.StatusAppearance;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Window for editing Recommendations
 * 
 * @author Lucas Reeh
 * 
 */
public class EditRecommendationWindow extends Window {

	// node provider
	CurriculumNodeProxy node;

	// student count changed when dragging modules or removing
	private Status studentCount;

	/** creating properties class */
	CurriculumNodeProxySimpleProperties props = GWT
			.create(CurriculumNodeProxySimpleProperties.class);

	final SafeHtml htmlOrangeFlagIcon = SafeHtmlUtils
			.fromTrustedString(AbstractImagePrototype.create(
					COCerResources.IMAGES.flag_orange()).getHTML());

	// GXT components
	ListStore<CurriculumNodeProxy> store = new ListStore<CurriculumNodeProxy>(
			props.id());

	ColumnModel<CurriculumNodeProxy> cm;

	Grid<CurriculumNodeProxy> grid;

	List<ColumnConfig<CurriculumNodeProxy, ?>> columnList = new ArrayList<ColumnConfig<CurriculumNodeProxy, ?>>();

	CurriculumRequestFactory factory;

	final EventBus eventBus;

	final Window thisWindow = this;

	/**
	 * class constructor
	 */
	@Inject
	public EditRecommendationWindow(CurriculumRequestFactory factory,
			final EventBus eventBus) {
		super();
		// local vars
		this.eventBus = eventBus;
		this.factory = factory;

		this.setHeadingText(COModuleRecommenderResources.TEXT
				.editRecommendation());
		this.setPixelSize(500, 300);

		// col for node icon
		ColumnConfig<CurriculumNodeProxy, Long> colIcon = new ColumnConfig<CurriculumNodeProxy, Long>(
				props.nodeId());
		colIcon.setCell(new AbstractCell<Long>() {
			@Override
			public void render(Context context, Long value, SafeHtmlBuilder sb) {
				sb.append(htmlOrangeFlagIcon);
			}

		});
		colIcon.setWidth(30);
		colIcon.setGroupable(false);
		colIcon.setSortable(false);
		colIcon.setFixed(true);

		// column for module id
		ColumnConfig<CurriculumNodeProxy, String> colModuleId = new ColumnConfig<CurriculumNodeProxy, String>(
				props.nodeShortName());
		colModuleId.setGroupable(false);
		colModuleId.setWidth(80);
		colModuleId.setFixed(true);
		colModuleId.setHeader(COCerResources.TEXT.moduleId());
		colModuleId.setCell(new AbstractCell<String>() {
			@Override
			public void render(Context context, String value, SafeHtmlBuilder sb) {
				if (value != null)
					sb.appendHtmlConstant("<span style=\"font-weight:bold;\">"
							+ value + "</span>");
			}
		});

		// column for node name
		ColumnConfig<CurriculumNodeProxy, String> colName = new ColumnConfig<CurriculumNodeProxy, String>(
				props.name());
		colName.setHeader(COCerResources.TEXT.moduleName());
		colName.setWidth(400);
		colName.setGroupable(false);

		// adding default columns
		columnList.add(colIcon);
		columnList.add(colModuleId);
		columnList.add(colName);

		// configuring GXT components
		cm = new ColumnModel<CurriculumNodeProxy>(columnList);
		grid = new Grid<CurriculumNodeProxy>(store, cm);

		// context menu for removing selection
		Menu contextMenu = new Menu();
		MenuItem delItems = new MenuItem(
				COModuleRecommenderResources.TEXT.delete());
		delItems.setIcon(COModuleRecommenderResources.IMAGES.bin_empty_16());
		delItems.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				for (CurriculumNodeProxy node : grid.getSelectionModel()
						.getSelectedItems()) {
					store.remove(node);
					loadStudentCount();
				}
			}
		});
		contextMenu.add(delItems);
		grid.setContextMenu(contextMenu);

		// grid droppable configuration
		GridDropTarget<CurriculumNodeProxy> target = new GridDropTarget<CurriculumNodeProxy>(
				grid);
		target.setAllowSelfAsSource(false);
		target.setOperation(Operation.COPY);
		target.addDropHandler(new DndDropHandler() {
			@Override
			public void onDrop(DndDropEvent event) {
				loadStudentCount();
			}
		});

		grid.getView().setForceFit(true);
		grid.getView().setAutoFill(true);

		grid.getView().setEmptyText(
				COModuleRecommenderResources.TEXT.dragHereInfo());

		// buttons / buttonbar
		TextButton btSave = new TextButton(
				COModuleRecommenderResources.TEXT.save());
		TextButton btDelete = new TextButton(
				COModuleRecommenderResources.TEXT.delete());
		TextButton btClose = new TextButton(
				COModuleRecommenderResources.TEXT.close());
		btDelete.setIcon(COModuleRecommenderResources.IMAGES.bin_empty_16());
		btSave.setIcon(COModuleRecommenderResources.IMAGES.check_16());
		btClose.setIcon(COModuleRecommenderResources.IMAGES.close_16());
		thisWindow.addButton(btSave);
		thisWindow.addButton(btDelete);
		thisWindow.addButton(btClose);

		// button handler
		btSave.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				onSaveRecommendation();
			}
		});
		btDelete.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				onDeleteRecommendation();
			}
		});
		btClose.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				thisWindow.hide();
			}
		});
		ToolBar toolBar = new ToolBar();
		toolBar.addStyleName(ThemeStyles.getStyle().borderTop());
		Label students = new Label(
				COModuleRecommenderResources.TEXT.numberOfStudents());
		toolBar.add(students);

		toolBar.add(new FillToolItem());
		studentCount = new Status(
				GWT.<StatusAppearance> create(BoxStatusAppearance.class));
		studentCount.setWidth(100);
		studentCount.setText(COModuleRecommenderResources.TEXT
				.numberOfStudentsUnknown());
		toolBar.add(studentCount);

		// window layouting
		VerticalLayoutContainer form = new VerticalLayoutContainer();
		VerticalLayoutData data = new VerticalLayoutData(1, 1);
		grid.setLayoutData(data);
		form.add(grid, new VerticalLayoutData(1, 1, new Margins(5)));
		toolBar.setLayoutData(new VerticalLayoutData(1, -1));
		form.add(toolBar);
		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.add(form);
		thisWindow.add(panel);

	}

	/**
	 * load number of matching students from server and set to status component
	 */
	protected void loadStudentCount() {
		if (store.getAll().size() > 0) {
			studentCount.setBusy(COModuleRecommenderResources.TEXT
					.loadingStudentsCountBusyMsg());
			factory.moduleRecommenderServiceRequest()
					.getNumberOfStundentsMatching(store.getAll(), node)
					.fire(new Receiver<Integer>() {
						@Override
						public void onSuccess(Integer result) {
							studentCount.clearStatus(result.toString());
						}

						@Override
						public void onFailure(ServerFailure error) {
							Info.display(COResources.TEXT_ERROR.error(),
									error.getMessage());
							studentCount.clearStatus(COResources.TEXT_ERROR
									.error());
							super.onFailure(error);
						}
					});
		} else {
			studentCount.clearStatus("0");
		}
	}

	/**
	 * onSaveRecommendation
	 * 
	 * saving recommendation to server and fire changed event for update of
	 * depending views
	 * 
	 */
	protected void onSaveRecommendation() {
		if (store.getAll().size() == 0) {
			onDeleteRecommendation();
		} else {
			factory.moduleRecommenderServiceRequest()
					.persistRecommendation(store.getAll(), node)
					.fire(new Receiver<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							AutoBean<CurriculumNodeProxy> autoBean = AutoBeanUtils
									.getAutoBean(node);
							autoBean.setFrozen(false);
							node.setHasRecommendations(" ");
							autoBean.setFrozen(true);
							RecommendationChangedEvent evt = new RecommendationChangedEvent(
									node);
							getEventBus().fireEvent(evt);
							thisWindow.hide();
							if (result) {
								Info.display(COResources.TEXT.info(),
										COModuleRecommenderResources.TEXT
												.infoSuccessfull());
							} else {
								Info.display(COResources.TEXT_ERROR.error(),
										COModuleRecommenderResources.TEXT
												.errorUnknown());
							}
						}
						@Override
						public void onFailure(ServerFailure error) {
							Info.display(COResources.TEXT_ERROR.error(),
									error.getMessage());
							super.onFailure(error);
						}
					});
		}
	}

	/**
	 * onDeleteRecommendation
	 * 
	 * delete recommendation from server and fire changed event for update of
	 * depending views
	 * 
	 */
	protected void onDeleteRecommendation() {
		factory.moduleRecommenderServiceRequest().deleteRecommendation(node)
				.fire(new Receiver<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							Info.display(COResources.TEXT.info(),
									COModuleRecommenderResources.TEXT
											.infoSuccessfull());
							store.clear();
							AutoBean<CurriculumNodeProxy> autoBean = AutoBeanUtils
									.getAutoBean(node);
							autoBean.setFrozen(false);
							node.setHasRecommendations("");
							autoBean.setFrozen(true);
							RecommendationChangedEvent evt = new RecommendationChangedEvent(
									node);
							getEventBus().fireEvent(evt);
							thisWindow.hide();
						} else {
							Info.display(COResources.TEXT_ERROR.error(),
									COModuleRecommenderResources.TEXT
											.errorUnknown());
						}
					}

					@Override
					public void onFailure(ServerFailure error) {
						Info.display(COResources.TEXT_ERROR.error(),
								error.getMessage());
						super.onFailure(error);
					}
				});

	}

	/**
	 * method for caller of window for setting current node and 
	 * loading recommendation data from server
	 * 
	 * @param node
	 */
	public void setNodeAndShow(CurriculumNodeProxy node) {

		setNode(node);
		studentCount.setText(COModuleRecommenderResources.TEXT.errorUnknown());

		// check parameter
		if (node == null) {
			Info.display(COResources.TEXT_ERROR.error(),
					COModuleRecommenderResources.TEXT.errorModuleHasNoId());
			return;
		}
		if (node.getNodeShortName().equals("")) {
			Info.display(COResources.TEXT_ERROR.error(),
					COModuleRecommenderResources.TEXT.errorModuleHasNoId());
			return;
		}
		if (node.getNodeShortName() == null) {
			Info.display(COResources.TEXT_ERROR.error(),
					COModuleRecommenderResources.TEXT.errorModuleHasNoId());
			return;
		}

		final CurriculumNodeProxy localnode = getNode();
		
		// load recommendation from server
		factory.moduleRecommenderServiceRequest().getRecommendation(node)
				.fire(new Receiver<List<CurriculumNodeProxy>>() {
					@Override
					public void onSuccess(List<CurriculumNodeProxy> result) {
						thisWindow
								.setHeadingText(COModuleRecommenderResources.TEXT
										.editRecommendation()
										+ ": "
										+ localnode.getNodeShortName());
						store.clear();
						store.addAll(result);
						grid.getView().refresh(true);
						thisWindow.show();
						loadStudentCount();
					}
					@Override
					public void onFailure(ServerFailure error) {
						Info.display(COResources.TEXT_ERROR.error(),
								error.getMessage());
						super.onFailure(error);
						thisWindow.hide();
					}
				});
	}

	/**
	 * @return the node
	 */
	public CurriculumNodeProxy getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(CurriculumNodeProxy node) {
		this.node = node;
	}

	/**
	 * @return the eventBus
	 */
	public EventBus getEventBus() {
		return eventBus;
	}

}
