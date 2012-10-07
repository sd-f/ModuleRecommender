package com.co.app.modrec.shared.resources;

import com.co.app.modrec.shared.resources.images.COModuleRecommenderImageResources;
import com.co.app.modrec.shared.resources.text.COModuleRecommenderTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COModuleRecommenderResources {

	/**
	 * helper variable for accessing image resources
	 */
	public static final COModuleRecommenderImageResources IMAGES = GWT.create(COModuleRecommenderImageResources.class);

	/**
	 * helper variable for accessing text resources
	 */
	public static final COModuleRecommenderTextConstants TEXT = GWT.create(COModuleRecommenderTextConstants.class);

  
}
