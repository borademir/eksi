package org.borademir.eksici.api.model.base;
/**
 * @author bora.demir
 */
public class PagingModel extends EksiBaseModel {
	
	private int currentPage = 1;
	
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
