package org.borademir.eksici.api.model;


public class TopicModel extends EksiBaseModel {
	
	private String href;
	private String topicText;
	private String topicPopularEntryCount;
	private TopicTypes type ;
	
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
	
	

}
