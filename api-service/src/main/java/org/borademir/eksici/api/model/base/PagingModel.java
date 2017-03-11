package org.borademir.eksici.api.model.base;
/**
 * @author bora.demir
 */
public class PagingModel extends EksiBaseModel {
	
	private int currentPage = 1;
	
	private int maxPage ;
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
	
}
