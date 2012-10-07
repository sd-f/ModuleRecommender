package com.co.core.page.shared.resources;

import com.co.core.page.shared.resources.images.COPageImageResources;
import com.co.core.page.shared.resources.text.COPageTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COPageResources {

	/**
	 * helper variable for accessing image resources
	 */
	public static final COPageImageResources IMAGES = GWT.create(COPageImageResources.class);

	/**
	 * helper variable for accessing text resources
	 */
	public static final COPageTextConstants TEXT = GWT.create(COPageTextConstants.class);

  
}
