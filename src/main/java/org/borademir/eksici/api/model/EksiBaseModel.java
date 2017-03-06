package org.borademir.eksici.api.model;

public class EksiBaseModel {
	
	public EksiBaseModel(){
		this.creationDate = System.currentTimeMillis();
	}
	
	private long creationDate;
	
	public long getCreationDate() {
		return creationDate;
	}
	

}
