package org.borademir.eksici.api;

import java.io.IOException;
import java.util.List;

import org.borademir.eksici.api.model.Autocomplete;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SuserModel;
import org.borademir.eksici.api.model.TopicModel;
/**
 * @author bora.demir
 */
public interface IEksiService extends IEksiBaseService{
	
	GenericPager<TopicModel> retrievePopularTopics(String pUrl) throws IOException;
	
	GenericPager<TopicModel> retrieveTodaysTopics(String pUrl) throws IOException;
	
	GenericPager<TopicModel> retrieveDesertedTopics(String pUrl) throws IOException;
	
	GenericPager<TopicModel> retrieveChannelTopics(String pUrl) throws IOException;
	
	GenericPager<TopicModel> retrieveTodayInHistoryTopics(String pUrl) throws IOException;
	
	TopicModel retrieveEntries(String pUrl, boolean pUsePageHrefTemplate)  throws IOException;
	
	List<ChannelModel> retrieveChannels() throws IOException;

	GenericPager<TopicModel> retrieveVideos(String pUrl)	throws IOException;

	GenericPager<TopicModel> search(MainPageModel pMainPage) throws EksiApiException, IOException;

	Autocomplete autocomplete(String pUrl) throws IOException;

	List<SuserModel> favorites(String targetUrl, String pToken) throws EksiApiException, IOException;

	SuserModel suser(String targetUrl) throws EksiApiException, IOException;

	GenericPager<TopicModel> suserEntryStats(String targetUrl)  throws EksiApiException, IOException;

	String login(String pUrl , String pEmail, String pPass) throws EksiApiException, IOException;;


}
