package com.co.app.cer.client.curriculum.node.ui.tree;

import java.util.List;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.co.app.cer.client.curriculum.node.TreeNodeSelectionChangedEvent;
import com.co.app.cer.client.curriculum.node.ui.grid.RecommendationsLoadedEvent;
import com.co.app.cer.client.curriculum.node.ui.grid.RecommendationsLoadedEvent.RecommendationsLoadedHandler;
import com.co.app.modrec.client.events.ModuleSearchEvent;
import com.co.app.modrec.client.events.ModuleSearchEvent.ModuleSearchHandler;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent.CheckChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * View for Presenter Widget for Cer Curriculum Node TreeGrid
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumNodeTreePresenter extends
		PresenterWidget<CurriculumNodeTreePresenter.MyView> {

	private Long curriculumVersionId = null;

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {
		public TreeStore<CurriculumNodeProxy> getStore();

		public Tree<CurriculumNodeProxy, String> getTree();
	}

	@Inject
	CurriculumRequestFactory factory;

	/**
	 * handles {@link ModuleSearchEvent}
	 */
	public final ModuleSearchHandler ModuleSearchHandler = new ModuleSearchHandler() {
		@Override
		public void onModuleSarch(ModuleSearchEvent event) {
			getView().getTree().setCheckedSelection(null);
		}
	};

	/**
	 * if recommendations had been loaded from elsewhere in application tree
	 * selection has to be cleared
	 * 
	 * handles {@link RecommendationsLoadedEvent}
	 */
	public final RecommendationsLoadedHandler recommendationsLoadedHandler = new RecommendationsLoadedHandler() {
		@Override
		public void onRecommendationsLoaded(RecommendationsLoadedEvent event) {
			getView().getTree().setCheckedSelection(null);
		}
	};

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public CurriculumNodeTreePresenter(final EventBus eventBus,
			final MyView view) {
		super(eventBus, view);
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
		// user clicks tree checkbox fires TreeNodeSelectionChangedEvent
		getView().getTree().addCheckChangedHandler(
				new CheckChangedHandler<CurriculumNodeProxy>() {
					@Override
					public void onCheckChanged(
							CheckChangedEvent<CurriculumNodeProxy> event) {
						TreeNodeSelectionChangedEvent evt = new TreeNodeSelectionChangedEvent(
								event.getItems());
						getEventBus().fireEvent(evt);
					}
				});
		registerHandler(getEventBus().addHandler(ModuleSearchEvent.getType(),
				ModuleSearchHandler));
		registerHandler(getEventBus().addHandler(
				RecommendationsLoadedEvent.getType(),
				recommendationsLoadedHandler));
	}

	/**
	 * onReset
	 */
	@Override
	protected void onReset() {
		super.onReset();
	}

	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		factory.initialize(getEventBus());

	}

	/**
	 * method for loading tree depending on selected curriculum version
	 * 
	 * @param curriculumVersionId
	 */
	public void setCurriculumVersionIdAndLoad(Long curriculumVersionId) {
		getView().getTree().mask();
		if (curriculumVersionId != null) {
			if (!curriculumVersionId.equals(new Long(0))) {
				this.curriculumVersionId = curriculumVersionId;
				factory.curriculumNodeServiceRequest()
						.getTree(getCurriculumVersionId())
						.with("type", "superNodeId", "nodeShortName", "isLeaf")
						.fire(new Receiver<List<CurriculumNodeProxy>>() {
							@Override
							public void onFailure(ServerFailure error) {
								getView().getTree().unmask();
							}

							@Override
							public void onSuccess(
									List<CurriculumNodeProxy> response) {
								if (response != null) {
									getView().getStore().clear();
									processTree(getView().getStore(), response);
								}
								getView().getTree().unmask();

							}
						});
			}
		}

	}

	/**
	 * @return the curriculumVersionId
	 */
	public Long getCurriculumVersionId() {
		return curriculumVersionId;
	}

	/**
	 * @param curriculumVersionId
	 *            the curriculumVersionId to set
	 */
	public void setCurriculumVersionId(Long curriculumVersionId) {
		this.curriculumVersionId = curriculumVersionId;
	}

	/**
	 * from tree to tree --> sorry for that gxt needs it
	 * 
	 * @param store
	 * @param item
	 */
	private void processTree(TreeStore<CurriculumNodeProxy> store,
			List<CurriculumNodeProxy> nodes) {
		addChildren(null, nodes, store);
	}

	/**
	 * hierarchical structure has to be rebuild after receiving from server
	 * depending on depth of tree request factory has problems when sending
	 * hierarchical objects as JSON
	 * 
	 * @param father
	 *            in tree
	 * @param nodes
	 *            received from server
	 * @param store
	 *            to build hierarchical structure in
	 */
	private void addChildren(CurriculumNodeProxy father,
			List<CurriculumNodeProxy> nodes,
			TreeStore<CurriculumNodeProxy> store) {
		/* for root element */
		if (father == null) {
			for (CurriculumNodeProxy root : nodes) {
				if (root.getSuperNodeId() == null) {
					store.add(root);
					addChildren(root, nodes, store);
					getView().getTree().setExpanded(root, true);
				}
			}
		} else {
			for (CurriculumNodeProxy child : nodes) {
				if (child.getSuperNodeId() != null) {
					if (child.getSuperNodeId().equals(father.getId())) {
						store.add(father, child);
						addChildren(child, nodes, store);
					}
				}
			}
		}
	}

}
