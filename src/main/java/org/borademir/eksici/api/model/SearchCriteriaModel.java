package org.borademir.eksici.api.model;

import org.borademir.eksici.api.model.base.EksiBaseModel;

public class SearchCriteriaModel extends EksiBaseModel {

	private String keywords ;
	
	private String author ;
	
	private String dateStart ;
	
	private String dateEnd ;
	
	private boolean niceOnly ;
	
	private SearchSortOrder sortOrder = SearchSortOrder.DATE;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public boolean isNiceOnly() {
		return niceOnly;
	}

	public void setNiceOnly(boolean niceOnly) {
		this.niceOnly = niceOnly;
	}

	public SearchSortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SearchSortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "SearchCriteriaModel [keywords=" + keywords + ", author="
				+ author + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", niceOnly=" + niceOnly + ", sortOrder=" + sortOrder + "]";
	}
	
}
