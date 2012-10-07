package com.co.app.cer.client.curriculum.node;

import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.util.List;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Tree Node Selection Changed Event class (generated)
 * 
 * if user selects node in tree (checkbox)
 * 
 * @author Lucas Reeh
 *
 */
public class TreeNodeSelectionChangedEvent extends
		GwtEvent<TreeNodeSelectionChangedEvent.TreeNodeSelectionChangedHandler> {

	public static Type<TreeNodeSelectionChangedHandler> TYPE = new Type<TreeNodeSelectionChangedHandler>();
	private List<CurriculumNodeProxy> selectedNodes;

	public interface TreeNodeSelectionChangedHandler extends EventHandler {
		void onTreeNodeSelectionChanged(TreeNodeSelectionChangedEvent event);
	}

	public interface TreeNodeSelectionChangedHasHandlers extends HasHandlers {
		HandlerRegistration addTreeNodeSelectionChangedHandler(
				TreeNodeSelectionChangedHandler handler);
	}

	public TreeNodeSelectionChangedEvent(List<CurriculumNodeProxy> selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public List<CurriculumNodeProxy> getSelectedNodes() {
		return selectedNodes;
	}

	@Override
	protected void dispatch(TreeNodeSelectionChangedHandler handler) {
		handler.onTreeNodeSelectionChanged(this);
	}

	@Override
	public Type<TreeNodeSelectionChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<TreeNodeSelectionChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, List<CurriculumNodeProxy> selectedNodes) {
		source.fireEvent(new TreeNodeSelectionChangedEvent(selectedNodes));
	}
}
