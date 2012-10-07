/**
 * 
 */
package com.co.app.cer.client.curriculum.node;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * abstract cell for rendering attendance chart
 * 
 * @author Lucas Reeh
 * 
 */
public class AttendanceChartCell extends AbstractCell<Double> {

	String color = "213, 70, 121";
	
	/**
	 * onRender override
	 */
	@Override
	public void render(Context context, Double value, SafeHtmlBuilder sb) {
		if (value != null) {
			String htmlString = "<div style=\"min-width:130px\">"
					+ "<div style=\"float:left;border-radius:3px;background-color:rgb("+color+"); width:"
					+ value.intValue() + "px"
					+ ";height:17px;padding:1px;\">";
			
			htmlString += "</div>";
			htmlString += "<div style=\"float:left\"><span style=\"margin-left:4px;color: black";
			htmlString += "\">";
			if (value < 1) {
				NumberFormat f = NumberFormat.getFormat("#0.0"); 
				htmlString += f.format(value);
			} else {
				htmlString += value.intValue();
			}
			htmlString += "</span></div></div>";
			sb.appendHtmlConstant(htmlString);
		}
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
