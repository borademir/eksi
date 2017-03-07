package org.borademir.eksici.api.model;

import java.util.List;

import org.borademir.eksici.api.model.base.EksiBaseModel;

public class ChannelModel extends EksiBaseModel{
	
	private String name ; 
	
	private String href ;
	
	private String title;
	
	private List<GenericPager<TopicModel>> topics ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
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
	
	

}
