package org.borademir.eksici.api;

import org.borademir.eksici.api.impl.MainPageServiceJsoupImpl;

public class EksiciApiServiceFactory {
	
	public IMainPageService createMainPageService(){
		return new MainPageServiceJsoupImpl();
	}

}
