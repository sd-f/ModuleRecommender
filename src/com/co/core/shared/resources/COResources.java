/**
 * 
 */
package com.co.core.shared.resources;

import com.co.core.shared.resources.text.COErrorTextConstants;
import com.co.core.shared.resources.text.COTextConstants;
import com.google.gwt.core.client.GWT;

/**
 * Class Resource helper
 * 
 * @author Lucas Reeh
 *
 */
public class COResources {
	
	/**
	 * helper variable for accessing text resources
	 */
	public static final COTextConstants TEXT = GWT.create(COTextConstants.class);
	
	public static final COErrorTextConstants TEXT_ERROR = GWT.create(COErrorTextConstants.class);


}
