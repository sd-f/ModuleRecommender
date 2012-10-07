/**
 * 
 */
package com.co.core.shared.custom.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Hyperlink;

/**
 * @author Lucas Reeh
 *
 */
public class HyperlinkCell extends AbstractCell<Hyperlink> {

    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context,
                    Hyperlink h, SafeHtmlBuilder sb)
    {
            sb.append(SafeHtmlUtils.fromTrustedString(h.toString()));
    }

}
