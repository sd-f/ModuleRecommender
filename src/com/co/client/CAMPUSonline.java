package com.co.client;

import com.google.gwt.core.client.EntryPoint;
import com.co.client.gin.ClientGinjector;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.shared.COClientUserModel;
import com.co.core.auth.shared.service.COAuthDataRequestFactory;
import com.co.core.auth.shared.service.COClientUserRequest;
import com.co.core.auth.shared.service.proxy.COClientUserProxy;
import com.co.core.shared.resources.COResources;
import com.google.gwt.core.client.GWT;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * GWT start and entry point class
 * 
 * binds gin module
 * 
 * @author Lucas Reeh
 *
 */
public class CAMPUSonline implements EntryPoint {

	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

    public final COClientUserHolder holder = ginjector.getCOClientUserHolder();
    
    public final PlaceManager placeManager = ginjector.getPlaceManager();

	@Override
	public void onModuleLoad() {
		// set custom uncaught exception
		//GWT.setUncaughtExceptionHandler( new CustomUncaughtExceptionHandler() );

		
		// This is required for GWT-Platform proxy's generator
		DelayedBindRegistry.bind(ginjector);

		// getting request factory from ginjector
		COAuthDataRequestFactory requestFactory = ginjector.getCOAuthDataRequestFactory();
        
		// initialize request factory
		requestFactory.initialize(ginjector.getEventBus());
		
		// request from request factory for initial call to get COClientUser
		COClientUserRequest request = requestFactory.getCOClientUserRequest();

		// user has to be loaded before application can be revealed
		// get COClient user and reveal
        request.getCurrentCOClientUser().fire(new Receiver<COClientUserProxy>() {

            @Override
            public void onSuccess(COClientUserProxy response) {
                
            	if (response != null) {
            		COClientUserModel coClientUserModel = new COClientUserModel();
            		coClientUserModel.setDisplayName(response.getDisplayName());
            		coClientUserModel.setLoggedIn(response.isLoggedIn());
            		coClientUserModel.setStaff(response.isStaff());
            		coClientUserModel.setStudent(response.isStudent());
            		holder.setUser(coClientUserModel);
            	} else {
            		holder.setUser(null);
            	}
                placeManager.revealCurrentPlace();  
                
            }

            @Override
            public void onFailure(ServerFailure error) {
            	holder.setUser(null);
            	AlertMessageBox alert = new AlertMessageBox(
        				COResources.TEXT_ERROR.error(), COResources.TEXT_ERROR.errorDBNotReachable());
        		
                placeManager.revealDefaultPlace();
                alert.show();
            }
            
        });
		
	}
}
