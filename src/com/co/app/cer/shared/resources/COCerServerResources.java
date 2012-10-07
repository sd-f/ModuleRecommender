package com.co.app.cer.shared.resources;

import java.io.IOException;
import org.scb.gwt.web.server.i18n.GWTI18N;
import com.co.app.cer.shared.resources.text.COCerTextConstants;
/**
 * 
 * @author Lucas Reeh
 *
 */
public class COCerServerResources {

	public static COCerTextConstants text() {
		try {
			return GWTI18N.create(COCerTextConstants.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
