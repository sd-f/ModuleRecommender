package com.co.app.modrec.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Recommendation Changed Event class (generated)
 * 
 * fired when lecturer (staff user) changes recommendation - should be handled
 * in view where recommendations are visible
 * 
 * @author Lucas Reeh
 * 
 */
public class RecommendationChangedEvent extends
		GwtEvent<RecommendationChangedEvent.RecommendationChangedHandler> {

	public static Type<RecommendationChangedHandler> TYPE = new Type<RecommendationChangedHandler>();
	private CurriculumNodeProxy curriculumNode;

	public interface RecommendationChangedHandler extends EventHandler {
		void onRecommendationChanged(RecommendationChangedEvent event);
	}

	public interface RecommendationChangedHasHandlers extends HasHandlers {
		HandlerRegistration addRecommendationChangedHandler(
				RecommendationChangedHandler handler);
	}

	public RecommendationChangedEvent(CurriculumNodeProxy curriculumNode) {
		this.curriculumNode = curriculumNode;
	}

	public CurriculumNodeProxy getCurriculumNode() {
		return curriculumNode;
	}

	@Override
	protected void dispatch(RecommendationChangedHandler handler) {
		handler.onRecommendationChanged(this);
	}

	@Override
	public Type<RecommendationChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<RecommendationChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source,
			CurriculumNodeProxy curriculumNode) {
		source.fireEvent(new RecommendationChangedEvent(curriculumNode));
	}
}
