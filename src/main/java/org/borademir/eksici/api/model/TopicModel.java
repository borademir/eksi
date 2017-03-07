package org.borademir.eksici.api.model;

import java.util.List;

import org.borademir.eksici.api.model.base.EksiBaseModel;

/**
 * @author bora.demir
 */
public class TopicModel extends EksiBaseModel {
	
	private String href;
	private String topicText;
	private String topicPopularEntryCount;
	private TopicTypes type ;
	private TopicViewType viewType ;
	private List<GenericPager<EntryModel>> entryList ;
	private int currentPage ;
	private int totalPage ; 
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTopicText() {
		return topicText;
	}
	public void setTopicText(String topicText) {
		this.topicText = topicText;
	}
	public String getTopicPopularEntryCount() {
		return topicPopularEntryCount;
	}
	public void setTopicPopularEntryCount(String topicPopularEntryCount) {
		this.topicPopularEntryCount = topicPopularEntryCount;
	}
	public TopicTypes getType() {
		return type;
	}
	public void setType(TopicTypes type) {
		this.type = type;
	}

	public List<GenericPager<EntryModel>> getEntryList() {
		return entryList;
	}
	
	public void setEntryList(List<GenericPager<EntryModel>> entryList) {
		this.entryList = entryList;
	}
	
	public TopicViewType getViewType() {
		return viewType;
	}
	public void setViewType(TopicViewType viewType) {
		this.viewType = viewType;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
