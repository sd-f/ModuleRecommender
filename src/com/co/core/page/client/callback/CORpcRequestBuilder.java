/**
 * 
 */
package com.co.core.page.client.callback;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Override Class (inject) for loader animation on Callbacks
 * 
 * @author Lucas Reeh
 * 
 * TODO implement loading animation on RPC-Request
 *
 */
public class CORpcRequestBuilder extends RpcRequestBuilder {

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		Info.display("Loading", "start");
		return super.doCreate(serviceEntryPoint);
		
	}

	@Override
	protected void doFinish(RequestBuilder rb) {
		super.doFinish(rb);
		Info.display("Loading", "end");
	}

	
	


}
