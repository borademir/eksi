package org.borademir.eksici.api.model;

import java.util.List;

import org.borademir.eksici.api.model.base.EksiBaseModel;

/**
 * @author bora.demir
 */
public class TopicModel extends EksiBaseModel {

	private String topicText;
	private String relatedEntryCount;
	private TopicTypes type ;
	private TopicViewType viewType ;
	private List<EntryModel> entryList ;
	private Integer currentEntryPage ;
	private Integer totalEntryPage ; 
	private List<PageInfoModel> pageList ; 
	
	public TopicModel(String href) {
		super(href);
	}
	
	public String getTopicText() {
		return topicText;
	}
	public void setTopicText(String topicText) {
		this.topicText = topicText;
	}
	public String getRelatedEntryCount() {
		return relatedEntryCount;
	}
	public void setRelatedEntryCount(String topicPopularEntryCount) {
		this.relatedEntryCount = topicPopularEntryCount;
	}
	public TopicTypes getType() {
		return type;
	}
	public void setType(TopicTypes type) {
		this.type = type;
	}

	public List<EntryModel> getEntryList() {
		return entryList;
	}
	
	public void setEntryList(List<EntryModel> entryList) {
		this.entryList = entryList;
	}
	
	public TopicViewType getViewType() {
		return viewType;
	}
	public void setViewType(TopicViewType viewType) {
		this.viewType = viewType;
	}

	public Integer getTotalEntryPage() {
		return totalEntryPage;
	}
	public void setTotalEntryPage(Integer totalEntryPage) {
		this.totalEntryPage = totalEntryPage;
	}
	public Integer getCurrentEntryPage() {
		return currentEntryPage;
	}
	public void setCurrentEntryPage(Integer currentEntryPage) {
		this.currentEntryPage = currentEntryPage;
	}

	public List<PageInfoModel> getPageList() {
		return pageList;
	}

	public void setPageList(List<PageInfoModel> pageList) {
		this.pageList = pageList;
	}

}
