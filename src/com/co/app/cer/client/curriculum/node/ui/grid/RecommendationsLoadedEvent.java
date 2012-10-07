package com.co.app.cer.client.curriculum.node.ui.grid;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Recommendations Loaded Event class (generated)
 * 
 * fired user clicks recommendation button in grid toolbar
 * tree selection has to be cleared because context changed
 * 
 * @author Lucas Reeh
 *
 */
public class RecommendationsLoadedEvent extends
		GwtEvent<RecommendationsLoadedEvent.RecommendationsLoadedHandler> {

	public static Type<RecommendationsLoadedHandler> TYPE = new Type<RecommendationsLoadedHandler>();

	public interface RecommendationsLoadedHandler extends EventHandler {
		void onRecommendationsLoaded(RecommendationsLoadedEvent event);
	}

	public interface RecommendationsLoadedHasHandlers extends HasHandlers {
		HandlerRegistration addRecommendationsLoadedHandler(
				RecommendationsLoadedHandler handler);
	}

	public RecommendationsLoadedEvent() {
	}

	@Override
	protected void dispatch(RecommendationsLoadedHandler handler) {
		handler.onRecommendationsLoaded(this);
	}

	@Override
	public Type<RecommendationsLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<RecommendationsLoadedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new RecommendationsLoadedEvent());
	}
}
