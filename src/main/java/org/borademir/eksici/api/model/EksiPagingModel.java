package org.borademir.eksici.api.model;

public class EksiPagingModel extends EksiBaseModel {
	
	private int currentPage ;
	
	protected boolean hasNext = true;
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public boolean hasNext(){
		return hasNext;
	}
	
}
