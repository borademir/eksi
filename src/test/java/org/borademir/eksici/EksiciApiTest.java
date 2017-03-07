package org.borademir.eksici;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.test.EksiciApiAnonymousTests;
import org.borademir.eksici.util.EksiciDateUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author bora.demir
 */
@Category({EksiciApiAnonymousTests.class}) 
public class EksiciApiTest {
	
	static Logger log = Logger.getLogger(EksiciApiTest.class);

	 @Test
	 public void testPopularTopicList() throws Exception {
		 IEksiService eksiciService = EksiApiServiceFactory.createService();
		 MainPageModel mpModel = new MainPageModel();
		 GenericPager<TopicModel> topicList = eksiciService.retrievePopularTopics(mpModel);
		 for(TopicModel tm : topicList.getContentList()){
			 log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
			 GenericPager<EntryModel> currentPage = eksiciService.retriveEntries(tm);
			 printEntryModel(currentPage);
			 for(int i=tm.getCurrentPage()+1;i<=tm.getTotalPage();i++){
				 currentPage = eksiciService.retriveEntries(tm);
				 printEntryModel(currentPage);
			 }
		 }
	 }
	 
	 public void printEntryModel(GenericPager<EntryModel> current) throws ParseException{
		 log.debug("\tPage: " + current.getCurrentPage());
		 for(EntryModel entryModel : current.getContentList()){
			 log.debug("\t\t" + entryModel.getEntryText());
			 log.debug("\t\t\tEntryId:" + entryModel.getEntryId());
			 log.debug("\t\t\tAuthor:" + entryModel.getSuser().getEntryAuthor());
			 log.debug("\t\t\tAuthorId:" + entryModel.getSuser().getEntryAuthorId());
			 log.debug("\t\t\tFavoriteCount:" + entryModel.getFavoriteCount());
			 log.debug("\t\t\tEntryDate:" + EksiciDateUtil.parseEntryDate(entryModel.getEntryDate()));
			 log.debug("\t\t\tEntryLink:" + entryModel.getEntryHref());

		 }
	 }
}
