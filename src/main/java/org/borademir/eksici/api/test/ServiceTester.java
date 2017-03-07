package org.borademir.eksici.api.test;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.util.EksiciDateUtil;
/**
 * @author bora.demir
 */
public class ServiceTester {
	
	static Logger log = Logger.getLogger(ServiceTester.class);
	
//	static {
//		System.setProperty("http.proxyHost"  , "172.16.64.74");
//		System.setProperty("http.proxyPort"  , "8080");
//		System.setProperty("https.proxyHost" , "172.16.64.74");
//		System.setProperty("https.proxyPort" , "8080");
//	}
	
	static EntryModel maxFav = null;
	
	public static void main(String[] args) throws IOException, ParseException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		
		MainPageModel mainPage = new MainPageModel();
		GenericPager<TopicModel> popularTopicCurrentPage = null;
		
//		log.debug("Popular Topics:");
//		while((popularTopicCurrentPage = eksiciService.retrievePopularTopics(mainPage)) != null){
//			for(TopicModel tm : popularTopicCurrentPage.getContentList()){
//				log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
//				GenericPager<EntryModel> currentPage = null;
//				while((currentPage = eksiciService.retriveEntries(tm)) != null){
//					printEntryModel(currentPage);
//				}
//			}
//		}

		log.debug("Todays Topics:");
		GenericPager<TopicModel> todaysTopicCurrentPage = null;
		while((todaysTopicCurrentPage = eksiciService.retrieveTodaysTopics(mainPage)) != null){
			for(TopicModel tm : todaysTopicCurrentPage.getContentList()){
				log.debug(tm.getTopicText() + "(" + tm.getTopicPopularEntryCount() + ") - " + tm.getHref());
//				GenericPager<EntryModel> currentPage = null;
//				while((currentPage = eksiciService.retriveEntries(tm)) != null){
//					printEntryModel(currentPage);
//				}
			}
		}
		
		
//		log.debug(maxFav.getEntryHref());
//		log.debug(maxFav.getEntryText());
//		log.debug(maxFav.getFavoriteCount());
	}
	
	public static void printEntryModel(GenericPager<EntryModel> current) throws ParseException{
		log.debug("\tPage: " + current.getCurrentPage());
		for(EntryModel entryModel : current.getContentList()){
			 log.debug("\t\t" + entryModel.getEntryText());
			 log.debug("\t\t\tEntryId:" + entryModel.getEntryId());
			 log.debug("\t\t\tFavoriteCount:" + entryModel.getFavoriteCount());
			 log.debug("\t\t\tEntryDate:" + EksiciDateUtil.parseEntryDate(entryModel.getEntryDate()));
			 log.debug("\t\t\tEntryLink:" + entryModel.getEntryHref());
			 
			 log.debug("\t\t\tAuthor:" + entryModel.getSuser().getEntryAuthor());
			 log.debug("\t\t\tAuthorId:" + entryModel.getSuser().getEntryAuthorId());
			 log.debug("\t\t\tAuthorLink:" + entryModel.getSuser().getHref());
			 
			 if(maxFav == null || entryModel.getFavoriteCount() > maxFav.getFavoriteCount()){
				 maxFav = entryModel;
			 }
		 
		}
	}


}
