package org.borademir.eksici.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.IMainPageService;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.api.model.TopicTypes;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class MainPageServiceJsoupImpl implements IMainPageService {
	
	static Logger log = Logger.getLogger(MainPageServiceJsoupImpl.class);

//	@Override
//	public EksiTopicPager popularTopics(int page) throws IOException {
//		List<TopicModel> popularTopics = retrievePopularTopics(page);
//		EksiTopicPager pager = new EksiTopicPager();
//		pager.setCurrentPage(page);
//		pager.setTopicList(popularTopics);
//		return pager;
//	}

	@Override
	public List<TopicModel> retrievePopularTopics() throws IOException {
		Connection conn = Jsoup.connect(EksiciResourceUtil.getPopularTopicsUrl()).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.method(Method.GET);
		
		Response response = conn.execute();
		
		Document doc = response.parse();
		
		Element topicList = doc.getElementsByAttributeValue("class", "topic-list partial").get(0);
		
		List<TopicModel> returnList = new ArrayList<TopicModel>();
		
		Elements liElements = topicList.getElementsByTag("li");
		for(Element liEl : liElements){
			if(liEl.id().contains("sponsored-index")){
				continue;
			}
			Element topicElement = liEl.getElementsByTag("a").get(0);
			String href = topicElement.attr("href");
			String topicText = ((TextNode)topicElement.childNode(0)).text();
			String topicPopularEntryCount = ((Element)topicElement.childNode(1)).text();
			
			TopicModel tm = new TopicModel();
			tm.setHref(href);
			tm.setTopicPopularEntryCount(topicPopularEntryCount);
			tm.setTopicText(topicText);
			tm.setType(TopicTypes.POPULAR);
			
			returnList.add(tm);
		}
		
		return returnList;
	}
	
	

}
