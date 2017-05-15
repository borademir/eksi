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
	private List<TopicModel> suggestedTopicList;
	private String errorText ;
	private String niceAllHref ;
	private String niceTodayHref ;
	private String pagingHrefTemplate ;
	private TopicModel beforeEntries ;
	private TopicModel afterEntries ;
	private String focusTo ;
	
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

	public List<TopicModel> getSuggestedTopicList() {
		return suggestedTopicList;
	}

	public void setSuggestedTopicList(List<TopicModel> suggestedTopicList) {
		this.suggestedTopicList = suggestedTopicList;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getNiceAllHref() {
		return niceAllHref;
	}

	public void setNiceAllHref(String niceAllHref) {
		this.niceAllHref = niceAllHref;
	}

	public String getNiceTodayHref() {
		return niceTodayHref;
	}

	public void setNiceTodayHref(String niceTodayHref) {
		this.niceTodayHref = niceTodayHref;
	}

	public String getPagingHrefTemplate() {
		return pagingHrefTemplate;
	}

	public void setPagingHrefTemplate(String pagingHrefTemplate) {
		this.pagingHrefTemplate = pagingHrefTemplate;
	}

	public TopicModel getBeforeEntries() {
		return beforeEntries;
	}

	public void setBeforeEntries(TopicModel beforeEntries) {
		this.beforeEntries = beforeEntries;
	}

	public TopicModel getAfterEntries() {
		return afterEntries;
	}

	public void setAfterEntries(TopicModel afterEntries) {
		this.afterEntries = afterEntries;
	}

	public String getFocusTo() {
		return focusTo;
	}

	public void setFocusTo(String focusTo) {
		this.focusTo = focusTo;
	}


}
