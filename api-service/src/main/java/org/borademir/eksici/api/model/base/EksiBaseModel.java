package org.borademir.eksici.api.model.base;
/**
 * @author bora.demir
 */
public class EksiBaseModel {
	
	private String href;
	private String originalUrl ;
	private long creationDate;
	private String nextPageHref ;
	
	public EksiBaseModel(String href){
		this.href= href;
		this.creationDate = System.currentTimeMillis();
	}
	
	public long getCreationDate() {
		return creationDate;
	}
	
	public String getNextPageHref() {
		return nextPageHref;
	}
	
	public void setNextPageHref(String nextPageHref) {
		this.nextPageHref = nextPageHref;
	}
	
	public String getHref() {
		return href;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	

}
