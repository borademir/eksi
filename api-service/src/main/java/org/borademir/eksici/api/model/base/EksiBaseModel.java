package org.borademir.eksici.api.model.base;
/**
 * @author bora.demir
 */
public class EksiBaseModel {
	
	public EksiBaseModel(){
		this.creationDate = System.currentTimeMillis();
	}
	
	private long creationDate;
	
	private String nextPageHref ;
	
	public long getCreationDate() {
		return creationDate;
	}
	
	public String getNextPageHref() {
		return nextPageHref;
	}
	
	public void setNextPageHref(String nextPageHref) {
		this.nextPageHref = nextPageHref;
	}
	

}
