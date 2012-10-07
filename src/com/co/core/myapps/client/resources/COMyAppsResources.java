package com.co.core.myapps.client.resources;

import com.co.core.myapps.client.resources.images.COMyAppsImageResources;
import com.co.core.myapps.client.resources.text.COMyAppsTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COMyAppsResources {

	/**
	 * helper variable for accessing image resources
	 */
	public static final COMyAppsImageResources IMAGES = GWT.create(COMyAppsImageResources.class);

	/**
	 * helper variable for accessing text resources
	 */
	public static final COMyAppsTextConstants TEXT = GWT.create(COMyAppsTextConstants.class);

  
}
