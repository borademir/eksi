package org.borademir.eksici.api;

import org.borademir.eksici.api.impl.EksiServiceJsoupImpl;
/**
 * @author bora.demir
 */
public class EksiApiServiceFactory {
	
	public static IEksiService createService(){
		return new EksiServiceJsoupImpl();
	}

}
