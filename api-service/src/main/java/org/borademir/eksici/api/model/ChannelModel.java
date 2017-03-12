package org.borademir.eksici.api.model;

import java.util.List;

import org.borademir.eksici.api.model.base.EksiBaseModel;

public class ChannelModel extends EksiBaseModel{
	
	private String name ; 
	
	private String title;
	
	private String topicsUrl ;
	
	private List<GenericPager<TopicModel>> topics ;
	
	public ChannelModel(String href) {
		super(href);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GenericPager<TopicModel>> getTopics() {
		return topics;
	}

	public void setTopics(List<GenericPager<TopicModel>> topics) {
		this.topics = topics;
	}

	public String getTopicsUrl() {
		return topicsUrl;
	}

	public void setTopicsUrl(String topicsUrl) {
		this.topicsUrl = topicsUrl;
	}
	
	

}
