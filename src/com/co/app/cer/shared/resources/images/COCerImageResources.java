package com.co.app.cer.shared.resources.images;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Class holding image resources
 * 
 * @author Lucas Reeh
 * 
 */
public interface COCerImageResources extends ClientBundle {

	@Source("map_16.png")
	ImageResource map_16();

	@Source("stp_0.gif")
	ImageResource form_blank_yellow();

	@Source("stp_1.gif")
	ImageResource flag_blue();

	@Source("stp_2.gif")
	ImageResource flag_green();

	@Source("stp_3.gif")
	ImageResource flag_orange();

	@Source("stp_4.gif")
	ImageResource flag_red();

	@Source("stp_5.gif")
	ImageResource flag_grey();

	@Source("stp_stp.gif")
	ImageResource form_blank_blue();

	@Source("page_white.png")
	ImageResource page_white();
	
	@Source("arrow_in.png")
	ImageResource arrow_in();
	
	@Source("arrow_out.png")
	ImageResource arrow_out();

}