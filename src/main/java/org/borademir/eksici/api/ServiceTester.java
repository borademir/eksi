package org.borademir.eksici.api;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.model.TopicModel;

public class ServiceTester {
	
	static Logger log = Logger.getLogger(ServiceTester.class);
	
	public static void main(String[] args) throws IOException {
		IMainPageService mainPageService = EksiciApiServiceFactory.createMainPageService();
		
//		EksiTopicPager eksiPager = mainPageService.popularTopics(1);
//		
//		while(eksiPager.hasNext()){
//			List<TopicModel> topicList = eksiPager.getTopicList();
//			log.debug("Popular Topics , page :" + eksiPager.getCurrentPage()) ;
//			for(TopicModel tm : topicList){
//				log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
//			}
//			
//			eksiPager.next();
//			
//		}
		

		List<TopicModel> topicList = mainPageService.retrievePopularTopics();
		for(TopicModel tm : topicList){
			log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
		}
		
		
	
	}

}
