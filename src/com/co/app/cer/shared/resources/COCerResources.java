package com.co.app.cer.shared.resources;

import com.co.app.cer.shared.resources.images.COCerImageResources;
import com.co.app.cer.shared.resources.text.COCerTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COCerResources {

	/**
	 * helper variable for accessing image resources
	 */
	public static final COCerImageResources IMAGES = GWT.create(COCerImageResources.class);

	/**
	 * helper variable for accessing text resources
	 */
	public static final COCerTextConstants TEXT = GWT.create(COCerTextConstants.class);

  
}
