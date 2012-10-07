package com.co.core.home.shared.resources;

import com.co.core.home.shared.resources.images.COHomeImageResources;
import com.co.core.home.shared.resources.text.COHomeTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COHomeResources {

	/**
	 * helper variable for accessing image resources
	 */
	public static final COHomeImageResources IMAGES = GWT.create(COHomeImageResources.class);

	/**
	 * helper variable for accessing text resources
	 */
	public static final COHomeTextConstants TEXT = GWT.create(COHomeTextConstants.class);

  
}
