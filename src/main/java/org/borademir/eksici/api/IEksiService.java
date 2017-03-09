package org.borademir.eksici.api;

import java.io.IOException;
import java.util.List;

import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SukelaMode;
import org.borademir.eksici.api.model.TopicModel;
/**
 * @author bora.demir
 */
public interface IEksiService extends IEksiBaseService{
	
	GenericPager<TopicModel> retrievePopularTopics(MainPageModel pMainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveTodaysTopics(MainPageModel pMainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveDesertedTopics(MainPageModel pMainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveChannelTopics(ChannelModel pChannel) throws IOException;
	
	GenericPager<TopicModel> retrieveTodayInHistoryTopics(MainPageModel mainPage, int pYear) throws IOException;
	
	GenericPager<EntryModel> retriveEntries(TopicModel pTopic,SukelaMode pSukelaMod)  throws IOException;
	
	List<ChannelModel> retrieveChannels(MainPageModel pMainPage) throws IOException;

	GenericPager<TopicModel> retrieveVideos(MainPageModel pMainPage)	throws IOException;

}
