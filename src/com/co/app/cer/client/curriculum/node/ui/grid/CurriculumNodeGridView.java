package com.co.app.cer.client.curriculum.node.ui.grid;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import com.co.app.cer.client.curriculum.node.AttendanceChartCell;
import com.co.app.cer.client.curriculum.node.CurriculumNodeProxySimpleProperties;
import com.co.app.cer.client.curriculum.node.RecommendedByLecturerChartCell;
import com.co.app.cer.client.curriculum.node.RecommendedChartCell;
import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.event.COClientUserChangedEvent;
import com.co.core.auth.client.event.COClientUserChangedEvent.COClientUserChangedHandler;
import com.co.core.auth.shared.COClientUserModel;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

/**
 * Curriculum Node Grid View
 * 
 * holds grid with curriculum nodes and attendance values
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumNodeGridView extends
		ViewWithUiHandlers<CurriculumNodeGridUiHandlers> implements
		CurriculumNodeGridPresenter.MyView {

	/** key provider for tree grid */
	class KeyProvider implements ModelKeyProvider<CurriculumNodeProxy> {
		@Override
		public String getKey(CurriculumNodeProxy item) {
			return item.getId().toString();
		}
	}

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CurriculumNodeGridView> {
	}

	@UiField(provided = true)
	BorderLayoutData northData = new BorderLayoutData(35);

	@UiField(provided = true)
	MarginData centerData = new MarginData();

	@UiField
	ToggleButton btAttendance;

	@UiField
	ToggleButton btAttendanceInactive;

	@UiField
	TextButton btRecommended;

	@UiField
	TextButton btRecommendedByLecturer;

	@UiField
	ToggleButton btSemesterType;

	@UiField
	ToggleButton btSemester;

	/** creating properties class */
	CurriculumNodeProxySimpleProperties props = GWT
			.create(CurriculumNodeProxySimpleProperties.class);

	ListStore<CurriculumNodeProxy> store;

	/**
	 * @return the store
	 */
	public ListStore<CurriculumNodeProxy> getStore() {
		return store;
	}

	ColumnModel<CurriculumNodeProxy> cm;

	Grid<CurriculumNodeProxy> grid;

	GroupingView<CurriculumNodeProxy> groupingView;

	CurriculumRequestFactory factory;

	final AbstractImagePrototype imgBook16Prototype = AbstractImagePrototype
			.create(COModuleRecommenderResources.IMAGES.book_16());

	final Image imageBook16 = imgBook16Prototype.createImage();

	private final static Image recImage = AbstractImagePrototype.create(
			COModuleRecommenderResources.IMAGES.brick()).createImage();

	private final static ImageResource recEditIcon = COModuleRecommenderResources.IMAGES
			.brick_edit();// .createImage();
	private final static ImageResource recAddIcon = COModuleRecommenderResources.IMAGES
			.brick_add();// .createImage();

	final SafeHtml htmlOrangeFlagIcon = SafeHtmlUtils
			.fromTrustedString(AbstractImagePrototype.create(
					COCerResources.IMAGES.flag_orange()).getHTML());

	@UiField
	ContentPanel pnList;

	ColumnConfig<CurriculumNodeProxy, Double> colAttendanceActive = new ColumnConfig<CurriculumNodeProxy, Double>(
			props.attendance());

	ColumnConfig<CurriculumNodeProxy, Double> colAttendanceInactive = new ColumnConfig<CurriculumNodeProxy, Double>(
			props.attendanceInactive());

	ColumnConfig<CurriculumNodeProxy, String> colSemesterTypeRefId = new ColumnConfig<CurriculumNodeProxy, String>(
			props.semesterTypeId());

	ColumnConfig<CurriculumNodeProxy, Integer> colSemester = new ColumnConfig<CurriculumNodeProxy, Integer>(
			props.semester());

	ColumnConfig<CurriculumNodeProxy, Boolean> colRecommended = new ColumnConfig<CurriculumNodeProxy, Boolean>(
			props.recommended());

	ColumnConfig<CurriculumNodeProxy, Boolean> colRecommendedByLecturer = new ColumnConfig<CurriculumNodeProxy, Boolean>(
			props.recommendedByLecturer());

	ColumnConfig<CurriculumNodeProxy, String> colEditRecommendedByLecturer = new ColumnConfig<CurriculumNodeProxy, String>(
			props.hasRecommendations());

	List<ColumnConfig<CurriculumNodeProxy, ?>> columnList = new ArrayList<ColumnConfig<CurriculumNodeProxy, ?>>();

	COClientUserModel user;

	/**
	 * handles {@link COClientUserChangedEvent}
	 */
	public final COClientUserChangedHandler coClientUserChangedHandler = new COClientUserChangedHandler() {
		@Override
		public void onCOClientUserChanged(COClientUserChangedEvent event) {
			user = event.getCoClientUser();
		}

	};

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public CurriculumNodeGridView(final Binder binder,
			Provider<CurriculumRequestFactory> provider,
			COClientUserHolder userHolder) {
		// set locale vars
		this.factory = provider.get();
		this.user = userHolder.getUser();

		

		// init store and add sort
		store = new ListStore<CurriculumNodeProxy>(props.id());
		store.addSortInfo(new StoreSortInfo<CurriculumNodeProxy>(
				props.nodeId(), SortDir.DESC));
		
		// column for node icon
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
		colIcon.setMenuDisabled(true);

		// column for link to CO
		ColumnConfig<CurriculumNodeProxy, Long> colLinkCO = new ColumnConfig<CurriculumNodeProxy, Long>(
				props.nodeId());
		// tooltip
		imageBook16.setTitle(COModuleRecommenderResources.TEXT.linkToCO());
		colLinkCO.setCell(new AbstractCell<Long>() {
			@Override
			public void render(Context context, Long value, SafeHtmlBuilder sb) {
				String anchor = "<a href=\"https://campus.tum.de/tumonline/wbStpModHB.detailPage?pKnotenNr="
						+ value.toString() + "\"";
				anchor += " target=\"_blank\">" + imageBook16.toString()
						+ "</a>";
				sb.append(SafeHtmlUtils.fromTrustedString(anchor));
			}

		});
		colLinkCO.setWidth(30);
		colLinkCO.setGroupable(false);
		colLinkCO.setSortable(false);
		colLinkCO.setFixed(true);
		colLinkCO.setMenuDisabled(true);

		// column node name
		ColumnConfig<CurriculumNodeProxy, String> colName = new ColumnConfig<CurriculumNodeProxy, String>(
				props.name());
		colName.setHeader(COCerResources.TEXT.moduleName());
		colName.setWidth(400);
		colName.setGroupable(false);

		// column node short name (module id)
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
		
		// column super node name (for grouping)
		ColumnConfig<CurriculumNodeProxy, String> colSuperNodeName = new ColumnConfig<CurriculumNodeProxy, String>(
				props.superNodeName());
		colSuperNodeName.setHeader(COCerResources.TEXT.context());
		colSuperNodeName.setGroupable(true);

		// column attendance value
		colAttendanceActive.setHeader(COModuleRecommenderResources.TEXT
				.attendance());
		colAttendanceActive.setWidth(130);
		colAttendanceActive.setResizable(false);
		colAttendanceActive.setFixed(true);
		AttendanceChartCell attendanceActiveCell = new AttendanceChartCell();
		attendanceActiveCell.setColor("213, 70, 121");
		colAttendanceActive.setCell(attendanceActiveCell);
		colAttendanceActive.setGroupable(false);

		// column attendance inactive students
		colAttendanceInactive.setHeader(COModuleRecommenderResources.TEXT
				.attendanceInactive());
		colAttendanceInactive.setWidth(130);
		colAttendanceInactive.setResizable(false);
		colAttendanceInactive.setFixed(true);
		AttendanceChartCell attendanceInactiveCell = new AttendanceChartCell();
		attendanceInactiveCell.setColor("36, 173, 154");
		colAttendanceInactive.setCell(attendanceInactiveCell);
		colAttendanceInactive.setGroupable(false);

		// column recommended semester
		colSemesterTypeRefId.setFixed(true);
		colSemesterTypeRefId.setGroupable(false);
		colSemesterTypeRefId.setWidth(30);
		colSemesterTypeRefId.setCell(new AbstractCell<String>() {
			@Override
			public void render(Context context, String value, SafeHtmlBuilder sb) {
				if (value != null)
					sb.appendHtmlConstant("<span style=\"color:rgb(0, 132, 200);font-weight:bold;\">"
							+ value + "</span>");
			}
		});
		colSemesterTypeRefId.setHeader(COModuleRecommenderResources.TEXT
				.recommendedSemsterShort());
		colSemesterTypeRefId.setToolTip(SafeHtmlUtils
				.fromString(COModuleRecommenderResources.TEXT
						.recommendedSemsterLong()));

		// column semester attended by students
		colSemester.setFixed(true);
		colSemester.setGroupable(false);
		colSemester.setWidth(30);
		colSemester.setCell(new AbstractCell<Integer>() {
			@Override
			public void render(Context context, Integer value,
					SafeHtmlBuilder sb) {
				if (value != null)
					sb.appendHtmlConstant("<span style=\"color:rgb(97, 186, 61);font-weight:bold;\">"
							+ value.toString() + "." + "</span>");
			}
		});
		colSemester
				.setHeader(COModuleRecommenderResources.TEXT.semesterShort());
		colSemester.setToolTip(SafeHtmlUtils
				.fromString(COModuleRecommenderResources.TEXT.semester()));

		// column for recommended icon (generated by others students)
		colRecommended.setFixed(true);
		colRecommended.setGroupable(false);
		colRecommended.setWidth(30);
		colRecommended.setCell(new RecommendedChartCell());

		// column for recommend icon (by lecturer recommendations)
		colRecommendedByLecturer.setFixed(true);
		colRecommendedByLecturer.setGroupable(false);
		colRecommendedByLecturer.setWidth(30);
		colRecommendedByLecturer.setCell(new RecommendedByLecturerChartCell());

		// column for edit recommendation icon
		colEditRecommendedByLecturer.setFixed(true);
		colEditRecommendedByLecturer.setGroupable(false);
		colEditRecommendedByLecturer.setWidth(50);
		recImage.setTitle(COModuleRecommenderResources.TEXT
				.lecturerRecommendationExists());
		TextButtonCell btEditRecommendation = new TextButtonCell() {
			@Override
			public void render(Context context, String value, SafeHtmlBuilder sb) {
				// server sends string if recommendations exist
				this.setText(" ");
				if (!value.equals(""))
					this.setIcon(recEditIcon);
				else
					this.setIcon(recAddIcon);
				int row = context.getIndex();
				if (getStore().get(row).isLecturersModule()) {
					super.render(context, value, sb);
				} else {
					if (!value.equals("")) {
						sb.appendHtmlConstant(recImage.toString());
					} else {
						sb.appendHtmlConstant("");
					}

				}
				this.setText(" ");
			}
		};
		// click on edit recommendation handler
		btEditRecommendation.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				int row = event.getContext().getIndex();
				if (getStore().get(row).isLecturersModule()) {
					if (getUiHandlers() != null) {
						getUiHandlers().onEditRecommendation(event);
					}
				}
			}
		});
		colEditRecommendedByLecturer.setCell(btEditRecommendation);

		// column list (default columns added)
		columnList.add(colIcon);
		columnList.add(colModuleId);
		columnList.add(colLinkCO);
		columnList.add(colName);
		columnList.add(colSuperNodeName);

		// grid config (column model, grouping view etc)
		cm = new ColumnModel<CurriculumNodeProxy>(columnList);
		groupingView = new GroupingView<CurriculumNodeProxy>();
		groupingView.groupBy(colSuperNodeName);
		groupingView.setShowGroupedColumn(false);
		grid = new Grid<CurriculumNodeProxy>(store, cm, groupingView);
		
		// draggable added to modules if current user is staff
		if (userHolder.getUser().isStaff()) {
			new GridDragSource<CurriculumNodeProxy>(grid);
		}
		grid.setLazyRowRender(3);
		
		// filters on columns
		GridFilters<CurriculumNodeProxy> filters = new GridFilters<CurriculumNodeProxy>();
		filters.initPlugin(grid);
		filters.setLocal(true);
		filters.addFilter(new StringFilter<CurriculumNodeProxy>(props.name()));
		filters.addFilter(new StringFilter<CurriculumNodeProxy>(props
				.nodeShortName()));

		// uibinder
		widget = binder.createAndBindUi(this);

		// icons for buttons in toolbar
		btAttendance.setIcon(COModuleRecommenderResources.IMAGES
				.align_left_16());
		btAttendanceInactive.setIcon(COModuleRecommenderResources.IMAGES
				.align_left_16());
		btSemesterType.setIcon(COModuleRecommenderResources.IMAGES
				.numbered_list_16());
		btSemesterType.setToolTip(COModuleRecommenderResources.TEXT
				.recommendedSemsterLong());
		btSemester.setIcon(COModuleRecommenderResources.IMAGES.folder_16());
		btRecommended.setIcon(COModuleRecommenderResources.IMAGES.group());
		btRecommendedByLecturer.setIcon(COModuleRecommenderResources.IMAGES
				.star_16());
		btRecommended.setToolTip(COModuleRecommenderResources.TEXT
				.recommendationFromStudentsLong());

		pnList.add(grid);
		grid.getView().setForceFit(true);
		grid.getView().setAutoFill(true);
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
	public Grid<CurriculumNodeProxy> getGrid() {
		return grid;
	}

	/**
	 * 
	 * @return
	 */
	public ColumnConfig<CurriculumNodeProxy, Double> getColAttendanceActive() {
		return colAttendanceActive;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btAttendance")
	void btAttendance(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onAttendanceSelected(event);
		}
	}

	/**
	 * @return btAttendance
	 */
	public ToggleButton getBtAttendance() {
		return btAttendance;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btAttendanceInactive")
	void btAttendanceInactive(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onAttendanceInactiveSelected(event);
		}
	}

	/**
	 * @return btAttendanceInactive
	 */
	public ToggleButton getBtAttendanceInactive() {
		return btAttendanceInactive;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btSemesterType")
	void onShowSemesterType(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onShowSemesterType(event);
		}
	}

	/**
	 * @return btSemesterType
	 */
	public ToggleButton getBtSemesterType() {
		return btSemesterType;
	}

	/**
	 * @return the props
	 */
	public CurriculumNodeProxySimpleProperties getProps() {
		return props;
	}

	/**
	 * @return the coAttendanceInactive
	 */
	public ColumnConfig<CurriculumNodeProxy, Double> getColAttendanceInactive() {
		return colAttendanceInactive;
	}

	/**
	 * @return the columnList
	 */
	public List<ColumnConfig<CurriculumNodeProxy, ?>> getColumnList() {
		return columnList;
	}

	/**
	 * @return the colSemesterTypeRefId
	 */
	public ColumnConfig<CurriculumNodeProxy, String> getColSemesterTypeRefId() {
		return colSemesterTypeRefId;
	}

	/**
	 * @return the groupingView
	 */
	public GroupingView<CurriculumNodeProxy> getGroupingView() {
		return groupingView;
	}

	/**
	 * @return the btRecommended
	 */
	public TextButton getBtRecommended() {
		return btRecommended;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btRecommended")
	void onShowRecommended(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onShowRecommended(event);
		}
	}

	/**
	 * @return the btRecommendedByLecturer
	 */
	public TextButton getBtRecommendedByLecturer() {
		return btRecommendedByLecturer;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btRecommendedByLecturer")
	void onShowRecommendedByLecturer(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onShowRecommendedByLecturer(event);
		}
	}

	/**
	 * @return the colRecommendedByLecturer
	 */
	public ColumnConfig<CurriculumNodeProxy, Boolean> getColRecommendedByLecturer() {
		return colRecommendedByLecturer;
	}

	/**
	 * @return the colRecommended
	 */
	public ColumnConfig<CurriculumNodeProxy, Boolean> getColRecommended() {
		return colRecommended;
	}

	/**
	 * @return the btSemester
	 */
	public ToggleButton getBtSemester() {
		return btSemester;
	}

	/**
	 * ui button click handler
	 */
	@UiHandler("btSemester")
	void onShowSemester(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onShowSemester(event);
		}
	}

	/**
	 * @return the colSemester
	 */
	public ColumnConfig<CurriculumNodeProxy, Integer> getColSemester() {
		return colSemester;
	}

	/**
	 * @return the colEditRecommendedByLecturer
	 */
	public ColumnConfig<CurriculumNodeProxy, String> getColEditRecommendedByLecturer() {
		return colEditRecommendedByLecturer;
	}

}
