package org.borademir.eksici.api.model;

import java.util.List;
/**
 * @author bora.demir
 */
public class MainPageModel {

	private List<GenericPager<TopicModel>> popularTopics ;
	
	private List<GenericPager<TopicModel>> todaysTopics ;
	
	private List<GenericPager<TopicModel>> desertedTopics ;
	
	private List<GenericPager<TopicModel>> videoTopics ;
	
	private List<GenericPager<TopicModel>> todayInHistoryTopics ;
	
	private List<ChannelModel> channels ;
	
	private List<GenericPager<TopicModel>> searchResults ;

	private SearchCriteriaModel searchCriteria;
	
	public List<GenericPager<TopicModel>> getPopularTopics() {
		return popularTopics;
	}

	public void setPopularTopics(List<GenericPager<TopicModel>> popularTopics) {
		this.popularTopics = popularTopics;
	}
	
	public List<GenericPager<TopicModel>> getTodaysTopics() {
		return todaysTopics;
	}
	
	public void setTodaysTopics(List<GenericPager<TopicModel>> todaysTopics) {
		this.todaysTopics = todaysTopics;
	}
	
	public List<GenericPager<TopicModel>> getDesertedTopics() {
		return desertedTopics;
	}
	
	public void setDesertedTopics(List<GenericPager<TopicModel>> desertedTopics) {
		this.desertedTopics = desertedTopics;
	}

	public List<GenericPager<TopicModel>> getTodayInHistoryTopics() {
		return todayInHistoryTopics;
	}

	public void setTodayInHistoryTopics(
			List<GenericPager<TopicModel>> todayInHistoryTopics) {
		this.todayInHistoryTopics = todayInHistoryTopics;
	}

	public List<ChannelModel> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelModel> channels) {
		this.channels = channels;
	}

	public List<GenericPager<TopicModel>> getVideoTopics() {
		return videoTopics;
	}

	public void setVideoTopics(List<GenericPager<TopicModel>> videoTopics) {
		this.videoTopics = videoTopics;
	}

	public List<GenericPager<TopicModel>> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<GenericPager<TopicModel>> searchResults) {
		this.searchResults = searchResults;
	}

	public SearchCriteriaModel getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteriaModel searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	
}
