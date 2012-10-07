package com.co.app.modrec.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.Long;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Module Search Event class (generated)
 * 
 * fired when user searches for modules
 * 
 * @author Lucas Reeh
 *
 */
public class ModuleSearchEvent extends
		GwtEvent<ModuleSearchEvent.ModuleSearchHandler> {

	public static Type<ModuleSearchHandler> TYPE = new Type<ModuleSearchHandler>();
	private Long curriculumVersionId;
	private String searchString;

	public interface ModuleSearchHandler extends EventHandler {
		void onModuleSarch(ModuleSearchEvent event);
	}

	public interface ModuleSarchHasHandlers extends HasHandlers {
		HandlerRegistration addModuleSarchHandler(ModuleSearchHandler handler);
	}

	public ModuleSearchEvent(Long curriculumVersionId, String searchString) {
		this.curriculumVersionId = curriculumVersionId;
		this.searchString = searchString;
	}

	public Long getCurriculumVersionId() {
		return curriculumVersionId;
	}

	public String getSearchString() {
		return searchString;
	}

	@Override
	protected void dispatch(ModuleSearchHandler handler) {
		handler.onModuleSarch(this);
	}

	@Override
	public Type<ModuleSearchHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ModuleSearchHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, Long curriculumVersionId,
			String searchString) {
		source.fireEvent(new ModuleSearchEvent(curriculumVersionId, searchString));
	}
}
