package com.co.app.cer.client.curriculum.node.ui.grid;

import java.util.ArrayList;
import java.util.List;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.co.app.cer.client.curriculum.node.CurriculumNodeProxySimpleProperties;
import com.co.app.cer.client.curriculum.node.TreeNodeSelectionChangedEvent;
import com.co.app.cer.client.curriculum.node.TreeNodeSelectionChangedEvent.TreeNodeSelectionChangedHandler;
import com.co.app.cer.shared.ModRecColumns;
import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.modrec.client.events.ModuleSearchEvent;
import com.co.app.modrec.client.events.RecommendationChangedEvent;
import com.co.app.modrec.client.events.ModuleSearchEvent.ModuleSearchHandler;
import com.co.app.modrec.client.events.RecommendationChangedEvent.RecommendationChangedHandler;
import com.co.app.modrec.client.ui.widgets.recommendation.EditRecommendationWindow;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.event.COClientUserChangedEvent;
import com.co.core.auth.client.event.COClientUserChangedEvent.COClientUserChangedHandler;
import com.co.core.auth.shared.COClientUserModel;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.messages.client.DefaultMessages;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Presenter Widget for Curriculum Node TreeGrid
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumNodeGridPresenter extends
		PresenterWidget<CurriculumNodeGridPresenter.MyView> implements
		CurriculumNodeGridUiHandlers {

	private Boolean dirtyFromSearch = false;

	private Long curriculumVersionId = null;

	private List<String> visibleColumns = new ArrayList<String>();

	private List<CurriculumNodeProxy> currentSelection = new ArrayList<CurriculumNodeProxy>();

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View,
			HasUiHandlers<CurriculumNodeGridUiHandlers> {
		public ListStore<CurriculumNodeProxy> getStore();

		public Grid<CurriculumNodeProxy> getGrid();

		public CurriculumNodeProxySimpleProperties getProps();

		public List<ColumnConfig<CurriculumNodeProxy, ?>> getColumnList();

		public GroupingView<CurriculumNodeProxy> getGroupingView();

		public ColumnConfig<CurriculumNodeProxy, Double> getColAttendanceActive();

		public ColumnConfig<CurriculumNodeProxy, Double> getColAttendanceInactive();

		public ColumnConfig<CurriculumNodeProxy, String> getColSemesterTypeRefId();

		public ColumnConfig<CurriculumNodeProxy, Integer> getColSemester();

		public ColumnConfig<CurriculumNodeProxy, Boolean> getColRecommended();

		public ColumnConfig<CurriculumNodeProxy, Boolean> getColRecommendedByLecturer();

		public ColumnConfig<CurriculumNodeProxy, String> getColEditRecommendedByLecturer();

		public ToggleButton getBtAttendance();

		public ToggleButton getBtAttendanceInactive();

		public TextButton getBtRecommended();

		public TextButton getBtRecommendedByLecturer();

		public ToggleButton getBtSemester();

		public ToggleButton getBtSemesterType();
	}

	@Inject
	CurriculumRequestFactory factory;

	COClientUserModel user;

	@Inject
	EditRecommendationWindow editWindow;

	/**
	 * handles {@link TreeNodeSelectionChangedEvent}
	 */
	public final TreeNodeSelectionChangedHandler nodeSelectionChangedHandler = new TreeNodeSelectionChangedHandler() {
		@Override
		public void onTreeNodeSelectionChanged(
				TreeNodeSelectionChangedEvent event) {
			onNodeSelectionChanged(event.getSelectedNodes());
		}
	};

	/**
	 * handles {@link COClientUserChangedEvent}
	 */
	public final COClientUserChangedHandler coClientUserChangedHandler = new COClientUserChangedHandler() {

		/**
		 * sets header content in right content slot
		 */
		@Override
		public void onCOClientUserChanged(COClientUserChangedEvent event) {
			user = event.getCoClientUser();
			getView().getBtRecommended().setEnabled(user.isStudent());
			getView().getBtRecommendedByLecturer().setEnabled(
					user.isStudent() || user.isStaff());
			if (user.isStaff()) {
				new GridDragSource<CurriculumNodeProxy>(getView().getGrid());
			}
		}

	};

	/**
	 * handles {@link ModuleSearchEvent}
	 */
	public final ModuleSearchHandler ModuleSearchHandler = new ModuleSearchHandler() {
		@Override
		public void onModuleSarch(ModuleSearchEvent event) {
			doModuleSearch(event.getCurriculumVersionId(),
					event.getSearchString());
		}
	};

	/**
	 * handles {@link RecommendationChangedEvent}
	 */
	public final RecommendationChangedHandler recommendationChangedHandler = new RecommendationChangedHandler() {

		@Override
		public void onRecommendationChanged(RecommendationChangedEvent event) {
			CurriculumNodeProxy node = event.getCurriculumNode();
			getView().getStore().update(node);
			refreshGrid();
		}
	};

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public CurriculumNodeGridPresenter(final EventBus eventBus,
			final MyView view, COClientUserHolder userHolder) {
		super(eventBus, view);
		getView().setUiHandlers(this);

		// set local user
		this.user = userHolder.getUser();
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
		getView().getGrid().setLoadMask(true);
		registerHandler(getEventBus().addHandler(
				TreeNodeSelectionChangedEvent.getType(),
				nodeSelectionChangedHandler));
		registerHandler(getEventBus().addHandler(
				COClientUserChangedEvent.getType(), coClientUserChangedHandler));
		registerHandler(getEventBus().addHandler(ModuleSearchEvent.getType(),
				ModuleSearchHandler));
		registerHandler(getEventBus().addHandler(
				RecommendationChangedEvent.getType(),
				recommendationChangedHandler));
	}

	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// set enabled on button depending on client-user
		getView().getBtRecommended().setEnabled(user.isStudent());
		getView().getBtRecommendedByLecturer().setEnabled(
				user.isStudent() || user.isStaff());
	}

	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		factory.initialize(getEventBus());
		clearStore();
		refreshGrid();

	}

	/**
	 * search for modules and replace current selection
	 * 
	 * @param curriculumVersionId
	 * @param searchString
	 */
	protected void doModuleSearch(Long curriculumVersionId, String searchString) {
		clearStore();
		maskGrid();
		factory.curriculumNodeServiceRequest()
				.findCurriculumNodesForGrid(curriculumVersionId, searchString)
				.with(getVisibleColumnsAsStringArrayForFactoryCall())
				.fire(new CurriculumProxyGridReceiver(getView().getGrid()) {
					@Override
					public void onSuccess(List<CurriculumNodeProxy> result) {
						showNothingFoundMessage(result);
						clearStore();
						// unFreezeNodes(result)
						getView().getStore().addAll(result);
						addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(
								getView().getProps().name(), SortDir.ASC));
						setDirtyFromSearch(true);
						refreshGrid();
					}
				});

	}

	/**
	 * display Info for no result from server
	 * 
	 * @param result
	 */
	private void showNothingFoundMessage(List<CurriculumNodeProxy> result) {
		showNothingFoundMessage(result, COCerResources.TEXT.nothingFound());
	}

	/**
	 * display Info for no result from server
	 * 
	 * @param result
	 */
	private void showNothingFoundMessage(List<CurriculumNodeProxy> result,
			String message) {
		if (result.size() <= 0) {
			Info.display(COCerResources.TEXT.info(), message);

		}
	}

	/**
	 * @return the currentSelection
	 */
	public List<CurriculumNodeProxy> getCurrentSelection() {
		return currentSelection;
	}

	/**
	 * @param currentSelection
	 *            the currentSelection to set
	 */
	public void setCurrentSelection(List<CurriculumNodeProxy> currentSelection) {
		this.currentSelection = currentSelection;
	}

	/**
	 * generates array with columns that should be loaded from server
	 * 
	 * @return
	 */
	private String[] getVisibleColumnsAsStringArrayForFactoryCall() {
		List<String> params = new ArrayList<String>();
		if (visibleColumns != null) {
			for (String param : visibleColumns) {
				params.add(param);
			}
		}
		params.add("superNodeId");
		params.add("nodeShortName");
		params.add("superNodeName");
		params.add("hasRecommendations");
		params.add("lecturersModule");
		params.add(ModRecColumns.ATTENDANCE_ACTIVE.toString());
		params.add(ModRecColumns.ATTENDANCE_INACTIVE.toString());
		params.add(ModRecColumns.SEMESTER_TYPE.toString());
		params.add(ModRecColumns.MEDIAN_SEMESTER.toString());
		return (String[]) params.toArray(new String[0]);
	}

	/**
	 * method called from event (tree selection changed)
	 */
	private void onNodeSelectionChanged(List<CurriculumNodeProxy> newSelection) {
		Integer newSelectionCount = newSelection.size();
		Integer currentSelectionCount = currentSelection.size();

		if (newSelectionCount > currentSelectionCount) {
			addToStoreByFatherId(getNodeToAdd(newSelection));
		} else if (currentSelectionCount > newSelectionCount) {
			setGridEmptyText();
			maskGrid();
			removeFromStore(getNodeToRemove(newSelection));
			refreshGrid();
		}
	}

	/**
	 * loads nodes from server by father node id (selected in tree)
	 * 
	 * @param nodeToAdd
	 */
	private void addToStoreByFatherId(CurriculumNodeProxy nodeToAdd) {
		setGridEmptyText();
		if (isDirtyFromSearch()) {
			clearStore();
		}
		setDirtyFromSearch(false);
		maskGrid();
		currentSelection.add(nodeToAdd);
		factory.curriculumNodeServiceRequest()
				.getCurriculumNodesByFatherIdForGrid(nodeToAdd.getId())
				.with(getVisibleColumnsAsStringArrayForFactoryCall())
				.fire(new Receiver<List<CurriculumNodeProxy>>() {
					@Override
					public void onSuccess(List<CurriculumNodeProxy> result) {
						refreshVisibleColumns();
						addToStore(result);
						unMaskGrid();
						refreshGrid();
					}
				});
	}

	/**
	 * sets new empty Text for Grid
	 */
	private void setGridEmptyText() {
		setGridEmptyText(COCerResources.TEXT.noEntries());
	}

	/**
	 * sets new empty Text for Grid
	 */
	private void setGridEmptyText(String text) {
		getView().getGrid().getView().setEmptyText(text);
	}

	/**
	 * unmasks grid
	 */
	private void unMaskGrid() {
		getView().getGrid().unmask();
	}

	/**
	 * set mask on grid
	 */
	private void maskGrid() {
		maskGrid(DefaultMessages.getMessages().loadMask_msg());
	}

	/**
	 * set mask on grid
	 */
	private void maskGrid(String message) {
		getView().getGrid().mask(message);
	}

	private void clearStore() {
		currentSelection.clear();
		getView().getStore().clear();
	}

	/**
	 * adds nodes to store
	 * 
	 * @param nodesToAdd
	 */
	private void addToStore(List<CurriculumNodeProxy> nodesToAdd) {
		getView().getStore().addAll(nodesToAdd);
	}

	/**
	 * removes nodes from store
	 * 
	 * @param nodeToAdd
	 */
	private void removeFromStore(CurriculumNodeProxy nodeToRemove) {
		currentSelection.remove(nodeToRemove);
		List<CurriculumNodeProxy> list = new ArrayList<CurriculumNodeProxy>();
		for (CurriculumNodeProxy node : getView().getStore().getAll()) {
			if (node.getSuperNodeId().equals(nodeToRemove.getId())) {
				list.add(node);
			}

		}
		for (CurriculumNodeProxy node : list) {
			if (node.getSuperNodeId().equals(nodeToRemove.getId())) {
				getView().getStore().remove(node);
			}

		}
	}

	/**
	 * finds element to remove depending on new selection compared to current
	 * selection
	 * 
	 * @param newSelection
	 */
	private CurriculumNodeProxy getNodeToRemove(
			List<CurriculumNodeProxy> newSelection) {
		for (CurriculumNodeProxy node : currentSelection) {
			if (!newSelection.contains(node)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * finds element to add depending on new selection compared to current
	 * selection
	 * 
	 * @param newSelection
	 * @return node to Add
	 */
	private CurriculumNodeProxy getNodeToAdd(
			List<CurriculumNodeProxy> newSelection) {
		for (CurriculumNodeProxy node : newSelection) {
			if (!currentSelection.contains(node)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * enables columns depending on toggle state of button
	 * 
	 * @param gridColumn
	 *            for columns to be added
	 * @param button
	 *            toggle button clicked
	 * @param column
	 *            to be added
	 */
	private void updateColumns(
			final ColumnConfig<CurriculumNodeProxy, ?> gridColumn,
			ToggleButton button, ModRecColumns column) {
		if (button.getValue()) {
			visibleColumns.add(column.toString());
			maskGrid();
			refreshVisibleColumns();
			refreshGrid();
		} else {
			visibleColumns.remove(column.toString());
			maskGrid();
			refreshVisibleColumns();
			refreshGrid();
		}
	}

	/**
	 * loads column attendance inactive from server
	 */
	@Override
	public void onAttendanceSelected(SelectEvent event) {
		addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(getView().getProps()
				.attendance(), SortDir.DESC));
		updateColumns(getView().getColAttendanceActive(), getView()
				.getBtAttendance(), ModRecColumns.ATTENDANCE_ACTIVE);
	}

	/**
	 * loads column attendance inactive from server
	 */
	@Override
	public void onAttendanceInactiveSelected(SelectEvent event) {
		addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(getView().getProps()
				.attendanceInactive(), SortDir.DESC));
		updateColumns(getView().getColAttendanceInactive(), getView()
				.getBtAttendanceInactive(), ModRecColumns.ATTENDANCE_INACTIVE);

	}

	/**
	 * loads column recommended semester from server
	 */
	@Override
	public void onShowSemesterType(SelectEvent event) {
		addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(getView().getProps()
				.semesterTypeId(), SortDir.ASC));
		updateColumns(getView().getColSemesterTypeRefId(), getView()
				.getBtSemesterType(), ModRecColumns.SEMESTER_TYPE);
	}

	/**
	 * loads column semester from server
	 */
	@Override
	public void onShowSemester(SelectEvent event) {
		addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(getView().getProps()
				.semester(), SortDir.ASC));
		updateColumns(getView().getColSemester(), getView().getBtSemester(),
				ModRecColumns.MEDIAN_SEMESTER);
	}

	/**
	 * loads recommendations from server
	 */
	@Override
	public void onShowRecommended(SelectEvent event) {
		RecommendationsLoadedEvent recommendationsLoadedEvent = new RecommendationsLoadedEvent();
		getEventBus().fireEvent(recommendationsLoadedEvent);
		visibleColumns.add(ModRecColumns.RECOMMENDED.toString());
		if (!visibleColumns
				.contains(ModRecColumns.ATTENDANCE_ACTIVE.toString()))
			visibleColumns.add(ModRecColumns.ATTENDANCE_ACTIVE.toString());
		if (!visibleColumns.contains(ModRecColumns.ATTENDANCE_INACTIVE
				.toString()))
			visibleColumns.add(ModRecColumns.ATTENDANCE_INACTIVE.toString());
		maskGrid(COModuleRecommenderResources.TEXT.calculatingRecommendations());
		factory.curriculumNodeServiceRequest()
				.getRecommendedNodes(curriculumVersionId)
				.with(getVisibleColumnsAsStringArrayForFactoryCall())
				.fire(new CurriculumProxyGridReceiver(getView().getGrid()) {
					@Override
					public void onSuccess(List<CurriculumNodeProxy> result) {
						showNothingFoundMessage(result,
								COModuleRecommenderResources.TEXT
										.noRecommendationsFound());
						clearStore();
						getView().getStore().clearSortInfo();
						getView().getStore().addAll(result);
						refreshVisibleColumns();
						addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(
								getView().getProps().attendanceInactive(),
								SortDir.DESC));
						setDirtyFromSearch(true);
						refreshGrid();
						visibleColumns.remove(ModRecColumns.RECOMMENDED);
					}
				});

	}

	/**
	 * loads lecturer recommendations from server
	 */
	@Override
	public void onShowRecommendedByLecturer(SelectEvent event) {
		RecommendationsLoadedEvent recommendationsLoadedEvent = new RecommendationsLoadedEvent();
		getEventBus().fireEvent(recommendationsLoadedEvent);
		visibleColumns.add(ModRecColumns.RECOMMENDED_BY_LECTURER.toString());
		if (!visibleColumns
				.contains(ModRecColumns.ATTENDANCE_ACTIVE.toString()))
			visibleColumns.add(ModRecColumns.ATTENDANCE_ACTIVE.toString());
		if (!visibleColumns.contains(ModRecColumns.ATTENDANCE_INACTIVE
				.toString()))
			visibleColumns.add(ModRecColumns.ATTENDANCE_INACTIVE.toString());
		maskGrid(COModuleRecommenderResources.TEXT.calculatingRecommendations());
		factory.curriculumNodeServiceRequest()
				.getRecommendedNodesByLecturer(curriculumVersionId)
				.with(getVisibleColumnsAsStringArrayForFactoryCall())
				.fire(new CurriculumProxyGridReceiver(getView().getGrid()) {
					@Override
					public void onSuccess(List<CurriculumNodeProxy> result) {
						showNothingFoundMessage(result,
								COModuleRecommenderResources.TEXT
										.noRecommendationsFound());
						clearStore();
						getView().getStore().clearSortInfo();
						getView().getStore().addAll(result);
						refreshVisibleColumns();
						addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(
								getView().getProps().attendanceInactive(),
								SortDir.DESC));
						setDirtyFromSearch(true);
						refreshGrid();
						visibleColumns
								.remove(ModRecColumns.RECOMMENDED_BY_LECTURER);
					}
				});
	}

	/**
	 * adds columns and sets button value according to visibleColumns
	 */
	private void refreshVisibleColumns() {

		getView().getColumnList().remove(
				getView().getColEditRecommendedByLecturer());
		if (user.isStaff()) {
			getView().getColumnList().add(
					getView().getColEditRecommendedByLecturer());
		}

		// Recommended Icon
		getView().getColumnList().remove(getView().getColRecommended());
		if (visibleColumns.contains(ModRecColumns.RECOMMENDED.toString())) {
			getView().getColumnList().add(getView().getColRecommended());
		}

		// Recommended Icon
		getView().getColumnList().remove(
				getView().getColRecommendedByLecturer());
		if (visibleColumns.contains(ModRecColumns.RECOMMENDED_BY_LECTURER
				.toString())) {
			getView().getColumnList().add(
					getView().getColRecommendedByLecturer());
		}

		// Semester Type (ID)
		getView().getColumnList().remove(getView().getColSemesterTypeRefId());
		if (visibleColumns.contains(ModRecColumns.SEMESTER_TYPE.toString())) {
			getView().getColumnList().add(getView().getColSemesterTypeRefId());
			getView().getBtSemesterType().setValue(true);
		} else {
			getView().getBtSemesterType().setValue(false);
		}

		// MEDIAN_SEMESTER
		getView().getColumnList().remove(getView().getColSemester());
		if (visibleColumns.contains(ModRecColumns.MEDIAN_SEMESTER.toString())) {
			getView().getColumnList().add(getView().getColSemester());
			getView().getBtSemester().setValue(true);
		} else {

			getView().getBtSemester().setValue(false);
		}

		// Attendance Active column
		getView().getColumnList().remove(getView().getColAttendanceActive());
		if (visibleColumns.contains(ModRecColumns.ATTENDANCE_ACTIVE.toString())) {
			getView().getColumnList().add(getView().getColAttendanceActive());
			getView().getBtAttendance().setValue(true);
		} else {
			getView().getBtAttendance().setValue(false);
		}

		// Attendance Inactive
		getView().getColumnList().remove(getView().getColAttendanceInactive());
		if (visibleColumns.contains(ModRecColumns.ATTENDANCE_INACTIVE
				.toString())) {
			getView().getColumnList().add(getView().getColAttendanceInactive());
			getView().getBtAttendanceInactive().setValue(true);
		} else {

			getView().getBtAttendanceInactive().setValue(false);
		}

	}

	/**
	 * refresh grid
	 */
	private void refreshGrid() {
		unMaskGrid();
		getView().getGrid().getView().refresh(true);
	}

	/**
	 * sorting for store dep on loaded column
	 * 
	 * @param sortInfo
	 */
	private void addSortInfo(StoreSortInfo<CurriculumNodeProxy> sortInfo) {
		getView().getStore().clearSortInfo();
		if (!getView().getGroupingView().isShowGroupedColumn()) {
			getView().getStore().addSortInfo(
					new StoreSortInfo<CurriculumNodeProxy>(getView().getProps()
							.superNodeName(), SortDir.ASC));
		}
		getView().getStore().addSortInfo(sortInfo);
	}

	/**
	 * @return the dirtyFromSearch
	 */
	public Boolean isDirtyFromSearch() {
		return dirtyFromSearch;
	}

	/**
	 * @param dirtyFromSearch
	 *            the dirtyFromSearch to set
	 */
	public void setDirtyFromSearch(Boolean dirtyFromSearch) {
		this.dirtyFromSearch = dirtyFromSearch;
	}

	/**
	 * sets the curriculumVersionId
	 * 
	 * @param curriculumVersionId
	 */
	public void setCurriculumVersionId(Long curriculumVersionId) {
		this.curriculumVersionId = curriculumVersionId;
	}

	/**
	 * event onEditRecommendation
	 */
	@Override
	public void onEditRecommendation(SelectEvent event) {
		Context c = event.getContext();
		int row = c.getIndex();
		editWindow.setNodeAndShow(getView().getStore().get(row));
	}

}
