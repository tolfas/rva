// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.client.common.exception;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class RiseAsyncCallback<T> implements AsyncCallback<T>{
	public Throwable caught;
	public abstract void onFailure();
	
	@Override
	public final void onFailure(Throwable caught) {
//		if (caught instanceof StatusCodeException) {
//			switch (((StatusCodeException)caught).getStatusCode()){
//			case 401:
//				redirectToLoginPage();
//				break;
//			}
//		}
//		else if (caught instanceof ServiceFailedException) {
//			switch (((ServiceFailedException)caught).getReason()){
//			case ServiceFailedException.AUTHENTICATION_FAILED: 
//				reAuthenticateUser();
//				break;
//			default:
//				this.caught = caught;
//				onFailure();
//				break;
//			}
//		}
//		else {
//			onFailure();
//		}
		
		onFailure();
	}

}
