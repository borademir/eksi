package org.borademir.eksici;

import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiciApiServiceFactory;
import org.borademir.eksici.api.IMainPageService;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.test.EksiciApiAnonymousTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category({EksiciApiAnonymousTests.class}) 
public class EksiciApiMainPageTest {
	
	static Logger log = Logger.getLogger(EksiciApiMainPageTest.class);

	 @Test
	 public void testPopularTopicList() throws Exception {

		 IMainPageService mainPageService = EksiciApiServiceFactory.createMainPageService();
		 List<TopicModel> topicList = mainPageService.retrievePopularTopics();
		 for(TopicModel tm : topicList){
			 log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
		 }
		 Assert.assertTrue(topicList != null && topicList.size() > 0);
	 }
	 
	 
}
