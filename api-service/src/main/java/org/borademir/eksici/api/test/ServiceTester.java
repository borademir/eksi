package org.borademir.eksici.api.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.borademir.eksici.util.EksiciDateUtil;
/**
 * @author bora.demir
 */
public class ServiceTester {
	
	static Logger log = Logger.getLogger(ServiceTester.class);
	
	static EntryModel maxFav = null;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ParseException, EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		
		MainPageModel mainPage = new MainPageModel();
		
		boolean processPopulars = false;
		boolean processTodays = false;
		boolean processDeserted = false;
		boolean processTodayInHistory = true;
		boolean processChannels = false;
		boolean processVideos = false;
		
		boolean processSearch = false;
		
		
		
		if(processPopulars){
			log.debug("Popular Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrievePopularTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}


		if(processTodays){
			log.debug("Todays Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodaysTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
		
		if(processDeserted){
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveDesertedTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
		if(processTodayInHistory){
			log.debug("Today In History Topics:");
			String targetUrl = EksiciResourceUtil.getTodayInHistoryTopicsUrl(System.currentTimeMillis(), 2002);
			for(;;){
				GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodayInHistoryTopics(targetUrl);
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(popularTopicCurrentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  popularTopicCurrentPage.getNextPageHref();
				}else{
					break;
				}
			}
		}
		
//		if(processChannels){
//			log.debug("Channels:");
//			List<ChannelModel> channels = eksiciService.retrieveChannels();
//			for(ChannelModel channel : channels){
//				log.debug(channel.getName() + " (" + channel.getTitle() + ") -- " + channel.getHref() );
//				GenericPager<TopicModel> channelTopics = null;
//				while((channelTopics = eksiciService.retrieveChannelTopics(channel)) != null){
//				}
//			}
//		}
		
		
		if(processVideos){
			log.debug("Videos:");
			String targetUrl = EksiciResourceUtil.getVideosUrl(System.currentTimeMillis());
			for(;;){
				GenericPager<TopicModel> currentPage = eksiciService.retrieveVideos(targetUrl);
				for(TopicModel tm : currentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
				if(currentPage.getNextPageHref() != null){
					targetUrl = EksiciResourceUtil.getHeaderReferrer() +  currentPage.getNextPageHref();
				}else{
					break;
				}
			}
		
		}
		
		if(processSearch){
			SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
			searchCriteriaModel.setKeywords("aykut"); 
			searchCriteriaModel.setAuthor("qlluq");
			mainPage.setSearchCriteria(searchCriteriaModel);
			log.debug(searchCriteriaModel);
			log.debug("Search results:");
			GenericPager<TopicModel> searchResults = null;
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
					GenericPager<EntryModel> currentPage = null;
					while((currentPage = eksiciService.retrieveEntries(tm,null)) != null){
						printEntryModel(currentPage);
					}
				}
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
