package org.borademir.eksici.api;

import org.borademir.eksici.api.impl.EksiServiceJsoupImpl;
/**
 * @author bora.demir
 */
public class EksiApiServiceFactory {
	
	private static IEksiService eksiServiceImpl = null;
	
	
	public static IEksiService createService(){
		if(eksiServiceImpl == null){
			eksiServiceImpl = new EksiServiceJsoupImpl();
		}
		return eksiServiceImpl;
	}

}
