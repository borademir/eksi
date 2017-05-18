package org.borademir.eksici.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JSoupUtil {

	public static String getElementTextById(Document doc, String pElementId) {
		Element element = doc.getElementById(pElementId);
		if(element != null){
			return element.text();
		}
		return null;
	}
	
	
	public static String getElementInnerHtmlBySelector(Document doc , String pSelector){
		Element element = doc.select(pSelector).first();
		if(element != null) {
			return element.html();
		}
		return null;
	}
	
	public static String getElementTextBySelector(Document doc , String pSelector){
		Element element = doc.select(pSelector).first();
		if(element != null) {
			return element.text();
		}
		return null;
	}

}
