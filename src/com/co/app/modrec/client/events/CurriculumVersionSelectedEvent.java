package com.co.app.modrec.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Curriculum Version Selected Event class (generated)
 * 
 * fired when user selects curriculum version in Curriculum version grid
 * 
 * @author Lucas Reeh
 *
 */
public class CurriculumVersionSelectedEvent
		extends
		GwtEvent<CurriculumVersionSelectedEvent.CurriculumVersionSelectedHandler> {

	public static Type<CurriculumVersionSelectedHandler> TYPE = new Type<CurriculumVersionSelectedHandler>();
	private Long curriculumVersionId;

	public interface CurriculumVersionSelectedHandler extends EventHandler {
		void onCurriculumVersionSelected(CurriculumVersionSelectedEvent event);
	}

	public interface CurriculumVersionSelectedHasHandlers extends HasHandlers {
		HandlerRegistration addCurriculumVersionSelectedHandler(
				CurriculumVersionSelectedHandler handler);
	}

	public CurriculumVersionSelectedEvent(Long curriculumVersionId) {
		this.curriculumVersionId = curriculumVersionId;
	}

	public Long getCurriculumVersionId() {
		return curriculumVersionId;
	}

	@Override
	protected void dispatch(CurriculumVersionSelectedHandler handler) {
		handler.onCurriculumVersionSelected(this);
	}

	@Override
	public Type<CurriculumVersionSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<CurriculumVersionSelectedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source,
			Long curriculumVersionId) {
		source.fireEvent(new CurriculumVersionSelectedEvent(curriculumVersionId));
	}
}
