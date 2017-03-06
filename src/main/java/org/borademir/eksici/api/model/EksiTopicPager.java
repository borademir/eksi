package org.borademir.eksici.api.model;

import java.util.List;

public class EksiTopicPager extends EksiPagingModel {
	
	private List<TopicModel> topicList ;
	
	public List<TopicModel> getTopicList() {
		return topicList;
	}
	
	public void setTopicList(List<TopicModel> topicList) {
		this.topicList = topicList;
	}

//	public void next() throws IOException {
//		setCurrentPage(getCurrentPage()+1);
//		EksiTopicPager nextPager;
//		try {
//			nextPager = EksiciApiServiceFactory.createMainPageService().popularTopics(getCurrentPage());
//			this.topicList = nextPager.topicList;
//		} catch (Exception e) {
//			hasNext = false;
//		}
//	}

}
