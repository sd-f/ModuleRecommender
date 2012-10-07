/**
 * 
 */
package com.co.app.cer.client.curriculum.node.ui.grid;

import java.util.List;

import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.co.core.shared.resources.COResources;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Request Factory Reciever class for curriculum nodes in grid
 * 
 * @author Lucas Reeh
 *
 */
public abstract class CurriculumProxyGridReceiver extends Receiver<List<CurriculumNodeProxy>> {

	private Grid<CurriculumNodeProxy> gridToUnmask;
	
	/**
	 * class constructor
	 */
	public CurriculumProxyGridReceiver(Grid<CurriculumNodeProxy> gridToUnmask) {
		super();
		this.gridToUnmask = gridToUnmask;
	}

	@Override
	public void onFailure(ServerFailure error) {
		Info.display(COResources.TEXT_ERROR.error(), error.getMessage());
		
		if (gridToUnmask != null) {
			gridToUnmask.unmask();
		}
		
		super.onFailure(error);
	}


}
