package org.borademir.eksici.api.model;

import java.util.List;

import org.borademir.eksici.api.model.base.EksiBaseModel;
/**
 * @author bora.demir
 */
public class MainPageModel extends EksiBaseModel{

	private List<GenericPager<TopicModel>> popularTopics ;
	
	private List<GenericPager<TopicModel>> todaysTopics ;

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
	
	
}
