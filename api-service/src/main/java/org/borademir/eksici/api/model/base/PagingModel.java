package org.borademir.eksici.api.model.base;
/**
 * @author bora.demir
 */
public class PagingModel extends EksiBaseModel {
	
	public PagingModel(String href) {
		super(href);
	}

	private Integer currentPage = 1;
	
	private Integer maxPage ;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}
	
	
}
