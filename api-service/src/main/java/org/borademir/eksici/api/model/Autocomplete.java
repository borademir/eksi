package org.borademir.eksici.api.model;

import java.util.List;

public class Autocomplete {
	
	private String query ;
	
	private List<TopicModel> topicList ;
	
	private List<SuserModel> suserList ;

	public List<TopicModel> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<TopicModel> topicList) {
		this.topicList = topicList;
	}

	public List<SuserModel> getSuserList() {
		return suserList;
	}

	public void setSuserList(List<SuserModel> suserList) {
		this.suserList = suserList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
