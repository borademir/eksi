package org.borademir.eksici.api;

import java.io.IOException;
import java.util.List;

import org.borademir.eksici.api.model.TopicModel;

public interface IMainPageService extends IBaseService{
	
//	public EksiTopicPager popularTopics(int pPage) throws IOException;

	List<TopicModel> retrievePopularTopics() throws IOException;

}
