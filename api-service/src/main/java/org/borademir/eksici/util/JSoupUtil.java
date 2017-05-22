package org.borademir.eksici.util;

import org.jsoup.nodes.Element;

public class JSoupUtil {

	public static String getElementTextById(Element pRootEl, String pElementId) {
		Element element = pRootEl.getElementById(pElementId);
		if(element != null){
			return element.text();
		}
		return null;
	}
	
	
	public static String getElementInnerHtmlBySelector(Element pRootEl , String pSelector){
		Element element = getElementBySelector(pRootEl, pSelector);
		if(element != null) {
			return element.html();
		}
		return null;
	}
	
	public static String getElementTextBySelector(Element pRootEl , String pSelector){
		Element element = getElementBySelector(pRootEl, pSelector);
		if(element != null) {
			return element.text();
		}
		return null;
	}
	
	public static Element getElementBySelector(Element pRootEl , String pSelector){
		Element element = pRootEl.select(pSelector).first();
		if(element != null) {
			return element;
		}
		return null;
	}

}
