package org.borademir.eksici.api;

import java.io.IOException;

import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SukelaMode;
import org.borademir.eksici.api.model.TopicModel;
/**
 * @author bora.demir
 */
public interface IEksiService extends IEksiBaseService{
	
//	public EksiTopicPager popularTopics(int pPage) throws IOException;

	GenericPager<TopicModel> retrievePopularTopics(MainPageModel mainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveTodaysTopics(MainPageModel mainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveDesertedTopics(MainPageModel mainPage) throws IOException;
	
	GenericPager<TopicModel> retrieveTodayInHistoryTopics(MainPageModel mainPage, int pYear) throws IOException;
	
	GenericPager<EntryModel> retriveEntries(TopicModel pTopic,SukelaMode pSukelaMod)  throws IOException;

}
