package org.borademir.eksici.api.impl;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SukelaMode;
import org.borademir.eksici.api.model.SuserModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.api.model.TopicTypes;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.borademir.eksici.util.EksiciDateUtil;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * @author bora.demir
 */
public class EksiServiceJsoupImpl implements IEksiService {
	
	static Logger log = Logger.getLogger(EksiServiceJsoupImpl.class);

	@Override
	public GenericPager<TopicModel> retrievePopularTopics(MainPageModel mainPage) throws IOException {
		
		boolean hasNext = true;
		String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
		if(mainPage.getPopularTopics() != null && mainPage.getPopularTopics().size() > 0){
			GenericPager<TopicModel> lastPageOfPopularTopics = mainPage.getPopularTopics().get(mainPage.getPopularTopics().size()-1);
			if(lastPageOfPopularTopics.getNextPageHref() == null){
				hasNext = false;
			}else{
				targetUrl = EksiciResourceUtil.getHeaderReferrer() + lastPageOfPopularTopics.getNextPageHref();
			}
		}
		if(!hasNext){
			return null;
		}
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>();
		
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		
		Response response = conn.execute();
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		
		/**
		 * parse next page link if exists
		 */
		Element nextPageElement = doc.select("div.full-index-continue-link-container > a").first();
		if(nextPageElement == null){
			Element pagerDivelement = doc.select("div.pager").first();
			int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
			int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
			if(currentPage < maxPage){
				String nextPageNumber = String.valueOf(currentPage+1);
				String href = MessageFormat.format(URLDecoder.decode(pagerDivelement.attr("data-urltemplate"),"UTF-8"),nextPageNumber);
				currentPopularPage.setNextPageHref(href);
			}
		}else{
			currentPopularPage.setNextPageHref(nextPageElement.attr("href"));
		}
		
		
		Element topicListElement = doc.getElementsByAttributeValue("class", "topic-list partial").get(0);
		Elements liElements = topicListElement.getElementsByTag("li");
		List<TopicModel> topicList = new ArrayList<TopicModel>();
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
			if(href.contains("?")){
				tm.setOriginalUrl(href.split("\\?")[0]);
			}else{
				tm.setOriginalUrl(href);
			}
			tm.setTopicPopularEntryCount(topicPopularEntryCount);
			tm.setTopicText(topicText);
			tm.setType(TopicTypes.POPULAR);
			
			topicList.add(tm);
		}
		
		
		currentPopularPage.setContentList(topicList);
		if(mainPage.getPopularTopics() == null){
			mainPage.setPopularTopics(new ArrayList<GenericPager<TopicModel>>());
		}
		mainPage.getPopularTopics().add(currentPopularPage);
		return currentPopularPage;
	}

	
	@Override
	public GenericPager<TopicModel> retrieveTodaysTopics(MainPageModel mainPage) throws IOException {

		boolean hasNext = true;
		String targetUrl = EksiciResourceUtil.getTodaysTopicsUrl(System.currentTimeMillis());
		if(mainPage.getTodaysTopics() != null && mainPage.getTodaysTopics().size() > 0){
			GenericPager<TopicModel> lastPageOfPopularTopics = mainPage.getTodaysTopics().get(mainPage.getTodaysTopics().size()-1);
			if(lastPageOfPopularTopics.getNextPageHref() == null){
				hasNext = false;
			}else{
				targetUrl = EksiciResourceUtil.getHeaderReferrer() + lastPageOfPopularTopics.getNextPageHref();
			}
		}
		if(!hasNext){
			return null;
		}
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>();
		
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("X-Requested-With","XMLHttpRequest")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		
		
		
		
		Response response = conn.execute();
		
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		
//		System.out.println(doc.html());
		
		/**
		 * parse next page link if exists
		 */
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		if(nextPageElement == null){
			Element pagerDivelement = doc.select("div.pager").first();
			int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
			int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
			if(currentPage < maxPage){
				String nextPageNumber = String.valueOf(currentPage+1);
				String href = MessageFormat.format(URLDecoder.decode(pagerDivelement.attr("data-urltemplate"),"UTF-8"),nextPageNumber);
				currentPopularPage.setNextPageHref(href);
			}
		}else{
			currentPopularPage.setNextPageHref(nextPageElement.attr("href"));
		}
		
		Element topicListElement = doc.getElementsByAttributeValue("class", "topic-list partial").get(0);
		Elements liElements = topicListElement.getElementsByTag("li");
		List<TopicModel> topicList = new ArrayList<TopicModel>();
		for(Element liEl : liElements){
			if(liEl.id().contains("sponsored-index")){
				continue;
			}
			Element topicElement = liEl.getElementsByTag("a").get(0);
			String href = topicElement.attr("href");
			String topicText = ((TextNode)topicElement.childNode(0)).text();
			String topicPopularEntryCount = "0";
			TopicModel tm = new TopicModel();
			try {
				topicPopularEntryCount = ((Element)topicElement.childNode(1)).text();
				tm.setTopicPopularEntryCount(topicPopularEntryCount == null ? "0" : topicPopularEntryCount);
			} catch (Exception e) {
			}
			
			tm.setHref(href);
			if(href.contains("?")){
				tm.setOriginalUrl(href.split("\\?")[0]);
			}else{
				tm.setOriginalUrl(href);
			}
			tm.setTopicText(topicText);
			tm.setType(TopicTypes.TODAYS);
			
			topicList.add(tm);
		}
		
		currentPopularPage.setContentList(topicList);
		if(mainPage.getTodaysTopics() == null){
			mainPage.setTodaysTopics(new ArrayList<GenericPager<TopicModel>>());
		}
		mainPage.getTodaysTopics().add(currentPopularPage);
		return currentPopularPage;
	}
	
	@Override
	public GenericPager<TopicModel> retrieveDesertedTopics(MainPageModel mainPage) throws IOException {

		boolean hasNext = true;
		String targetUrl = EksiciResourceUtil.getDesertedTopicsUrl(System.currentTimeMillis());
		if(mainPage.getTodaysTopics() != null && mainPage.getTodaysTopics().size() > 0){
			GenericPager<TopicModel> lastPageOfPopularTopics = mainPage.getTodaysTopics().get(mainPage.getTodaysTopics().size()-1);
			if(lastPageOfPopularTopics.getNextPageHref() == null){
				hasNext = false;
			}else{
				targetUrl = EksiciResourceUtil.getHeaderReferrer() + lastPageOfPopularTopics.getNextPageHref();
			}
		}
		if(!hasNext){
			return null;
		}
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>();
		
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("X-Requested-With","XMLHttpRequest")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		Response response = conn.execute();
		log.debug("Loaded : " + response.url());
		
		Document doc = response.parse();
		
//		System.out.println(doc.html());
		
		/**
		 * parse next page link if exists
		 */
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		if(nextPageElement == null){
			Element pagerDivelement = doc.select("div.pager").first();
			int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
			int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
			if(currentPage < maxPage){
				String nextPageNumber = String.valueOf(currentPage+1);
				String href = MessageFormat.format(URLDecoder.decode(pagerDivelement.attr("data-urltemplate"),"UTF-8"),nextPageNumber);
				currentPopularPage.setNextPageHref(href);
			}
		}else{
			currentPopularPage.setNextPageHref(nextPageElement.attr("href"));
		}
		
		Element topicListElement = doc.getElementsByAttributeValue("class", "topic-list partial").get(0);
		Elements liElements = topicListElement.getElementsByTag("li");
		List<TopicModel> topicList = new ArrayList<TopicModel>();
		for(Element liEl : liElements){
			if(liEl.id().contains("sponsored-index")){
				continue;
			}
			Element topicElement = liEl.getElementsByTag("a").get(0);
			String href = topicElement.attr("href");
			String topicText = ((TextNode)topicElement.childNode(0)).text();
			String topicPopularEntryCount = "0";
			TopicModel tm = new TopicModel();
			try {
				topicPopularEntryCount = ((Element)topicElement.childNode(1)).text();
				tm.setTopicPopularEntryCount(topicPopularEntryCount == null ? "0" : topicPopularEntryCount);
			} catch (Exception e) {
			}
			
			tm.setHref(href);
			if(href.contains("?")){
				tm.setOriginalUrl(href.split("\\?")[0]);
			}else{
				tm.setOriginalUrl(href);
			}
			tm.setTopicText(topicText);
			tm.setType(TopicTypes.TODAYS);
			
			topicList.add(tm);
		}
		
		currentPopularPage.setContentList(topicList);
		if(mainPage.getTodaysTopics() == null){
			mainPage.setTodaysTopics(new ArrayList<GenericPager<TopicModel>>());
		}
		mainPage.getTodaysTopics().add(currentPopularPage);
		return currentPopularPage;
	}
	
	
	@Override
	public GenericPager<EntryModel> retriveEntries(TopicModel pTopic,SukelaMode pSukelaMod) throws IOException {
		
		int targetPage = 1;
		
		if(pTopic.getEntryList() != null){
			if(pTopic.getCurrentPage() == pTopic.getTotalPage()){
				return null;
			}
			targetPage = pTopic.getCurrentPage()+1;
		}

		String targetUrl = EksiciResourceUtil.getHeaderReferrer() + pTopic.getHref();
		if(pSukelaMod != null && targetUrl.contains("?")){
			targetUrl = targetUrl.split("\\?")[0];
		}
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.data("p",String.valueOf(targetPage))
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		if(pSukelaMod != null){
			conn.data("a", pSukelaMod.name().toLowerCase());
		}
		
		Response response = conn.execute();
		
		Document doc = response.parse();
		
		Element pagerDivelement = doc.select("div.pager").first();
		if(pagerDivelement == null){
			pTopic.setCurrentPage(1);
			pTopic.setTotalPage(1);
		}else{
			int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
			int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
			pTopic.setCurrentPage(currentPage);
			pTopic.setTotalPage(maxPage);
			
			Element nextAnchor = pagerDivelement.select("a.next").first();
			if(nextAnchor != null){
				pTopic.setNextPageHref(nextAnchor.attr("href"));
			}
		}
		
		
		Element entryListUlElement = doc.getElementById("entry-list");
		Elements liElements = entryListUlElement.getElementsByTag("li");
		
		if(pTopic.getEntryList() == null){
			pTopic.setEntryList(new ArrayList<GenericPager<EntryModel>>());
		}
		GenericPager<EntryModel> currentPageModel = new GenericPager<EntryModel>();
		currentPageModel.setCurrentPage(pTopic.getCurrentPage());
		currentPageModel.setContentList(new ArrayList<EntryModel>());
		
		for(Element liEl : liElements){
			String entryId = liEl.attr("data-id");
			String entryAuthor = liEl.attr("data-author");
			String entryAuthorId = liEl.attr("data-author-id");
			String dataIsFavorite = liEl.attr("data-isfavorite");
			int favoriteCount = Integer.valueOf(liEl.attr("data-favorite-count"));
			int commentCount = Integer.valueOf(liEl.attr("data-comment-count"));
			
			if(liEl.attr("data-seyler-slug").trim().length() > 0){
				log.debug(liEl.attr("data-seyler-slug"));
			}

			EntryModel entryModel = new EntryModel();
			entryModel.setCommentCount(commentCount);
			entryModel.setDataIsFavorite(dataIsFavorite);
			SuserModel suser = new SuserModel();
			suser.setEntryAuthor(entryAuthor);
			suser.setEntryAuthorId(entryAuthorId);
			entryModel.setSuser(suser);
			entryModel.setEntryId(entryId);
			entryModel.setFavoriteCount(favoriteCount);
			
			Element infoElement = liEl.getElementsByAttributeValue("class", "info").get(0);
			
			Element entryDateElement = infoElement.getElementsByAttributeValue("class","entry-date permalink").get(0);
			
			String entryHref = entryDateElement.attr("href");
			entryModel.setEntryHref(entryHref);
			
			String entryDate = entryDateElement.text();
			try {
				entryModel.setEntryDate(EksiciDateUtil.formateEntryDate(entryDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Element authorElement = infoElement.getElementsByAttributeValue("class","entry-author").get(0);
			
			suser.setHref(authorElement.attr("href"));
			
			Element contentElement = liEl.getElementsByAttributeValue("class", "content").get(0);
			
			entryModel.setEntryText(contentElement.text());
			entryModel.setEntryHtml(contentElement.html());
			
			currentPageModel.getContentList().add(entryModel);
			
		}
		
		pTopic.getEntryList().add(currentPageModel);
		return currentPageModel;
	
	}

}
