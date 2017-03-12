package org.borademir.eksici.api.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SukelaMode;
import org.borademir.eksici.api.model.SuserModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.api.model.TopicTypes;
import org.borademir.eksici.api.test.ServiceTester;
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
	public GenericPager<TopicModel> search(MainPageModel mainPage) throws EksiApiException, IOException {
		
		if(mainPage.getSearchCriteria() == null){
			throw new EksiApiException("Arama kriterleri boş olamaz..");
		}
		
		boolean hasNext = true;
		int page=1;
		System.out.println(ServiceTester.class.getResource("/").getFile());
		String targetUrl = EksiciResourceUtil.getSearchUrl();
		if(mainPage.getSearchResults() != null && mainPage.getSearchResults().size() > 0){
			GenericPager<TopicModel> lastPageOfPopularTopics = mainPage.getSearchResults().get(mainPage.getSearchResults().size()-1);
			if(lastPageOfPopularTopics.getNextPageHref() == null){
				hasNext = false;
			}else{
//				targetUrl = EksiciResourceUtil.getHeaderReferrer() + lastPageOfPopularTopics.getNextPageHref();
				page = mainPage.getSearchResults().size() + 1;
			}
		}
		if(!hasNext){
			return null;
		}
		
		
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);

		conn.data("p",String.valueOf(page));
		
		boolean processSearch  = false;
		if(mainPage.getSearchCriteria().getKeywords() != null){
			conn.data("SearchForm.Keywords",mainPage.getSearchCriteria().getKeywords());
			processSearch  = true;
		}
		
		if(mainPage.getSearchCriteria().getAuthor() != null){
			conn.data("SearchForm.Author",mainPage.getSearchCriteria().getAuthor());
			processSearch  = true;
		}
		
		if(mainPage.getSearchCriteria().getDateStart() != null){
			conn.data("SearchForm.When.From",mainPage.getSearchCriteria().getDateStart());
		}
		
		if(mainPage.getSearchCriteria().getDateEnd() != null){
			conn.data("SearchForm.When.To",mainPage.getSearchCriteria().getDateEnd());
		}
		
		if(mainPage.getSearchCriteria().isNiceOnly()){
			conn.data("SearchForm.NiceOnly" , "true");
		}
		
		if(mainPage.getSearchCriteria().getSortOrder() != null){
			conn.data("SearchForm.SortOrder",mainPage.getSearchCriteria().getSortOrder().getName());
		}
		
		if(!processSearch){
			throw new EksiApiException("Yazar ya da kelime seçmelisiniz.");
		}
		Response response = conn.execute();
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		

		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>(response.url().toString());
		
		Element nextPageElement = doc.select("div.full-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPopularPage, doc, nextPageElement);
		
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.SEARCH_RESULT,"topic-list");
		currentPopularPage.setContentList(topicList);
		
		if(mainPage.getSearchResults() == null){
			mainPage.setSearchResults(new ArrayList<GenericPager<TopicModel>>());
		}
		mainPage.getSearchResults().add(currentPopularPage);
		return currentPopularPage;
	}
	
	@Override
	public GenericPager<TopicModel> retrievePopularTopics(String pUrl) throws IOException {
		
		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		
		Response response = conn.execute();
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>(response.url().toString());
		Element nextPageElement = doc.select("div.full-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPopularPage, doc, nextPageElement);
		
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.POPULAR,"topic-list partial");
		currentPopularPage.setContentList(topicList);
		
		return currentPopularPage;
	}
	
	@Override
	public GenericPager<TopicModel> retrieveTodaysTopics(String pUrl) throws IOException {

		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("X-Requested-With","XMLHttpRequest")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		Response response = conn.execute();
		
		GenericPager<TopicModel> currentPage = new GenericPager<TopicModel>(response.url().toString());
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPage, doc, nextPageElement);
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.TODAYS,"topic-list partial");
		currentPage.setContentList(topicList);
		
		return currentPage;
	}

	@Override
	public GenericPager<TopicModel> retrieveDesertedTopics(String pUrl) throws IOException {

		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
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
		GenericPager<TopicModel> currentPage = new GenericPager<TopicModel>(response.url().toString());
		
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPage, doc, nextPageElement);
		
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.DESERTED,"topic-list partial");
		
		currentPage.setContentList(topicList);
		
		return currentPage;
	}
	
	
	@Override
	public GenericPager<TopicModel> retrieveVideos(String pUrl) throws IOException {

		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
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
		
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>(response.url().toString());
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPopularPage, doc, nextPageElement);
		
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.VIDEOS,"topic-list partial");
		
		currentPopularPage.setContentList(topicList);
		return currentPopularPage;
	}
	
	@Override
	public GenericPager<TopicModel> retrieveTodayInHistoryTopics(String pUrl) throws IOException {

		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
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
		
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>(response.url().toString());
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPopularPage, doc, nextPageElement);
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.TODAY_IN_HISTORY,"topic-list partial");
		currentPopularPage.setContentList(topicList);
		return currentPopularPage;
	}
	
	@Override
	public GenericPager<EntryModel> retrieveEntries(TopicModel pTopic,SukelaMode pSukelaMod) throws IOException {
		
		int targetPage = 1;
		
		// TODO null check
		if(pTopic.getEntryList() != null){
			if(pTopic.getCurrentEntryPage() == pTopic.getTotalEntryPage()){
				return null;
			}
			targetPage = pTopic.getCurrentEntryPage()+1;
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
			pTopic.setCurrentEntryPage(1);
			pTopic.setTotalEntryPage(1);
		}else{
			int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
			int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
			pTopic.setCurrentEntryPage(currentPage);
			pTopic.setTotalEntryPage(maxPage);
			
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
		GenericPager<EntryModel> currentPageModel = new GenericPager<EntryModel>(response.url().toString());
		currentPageModel.setCurrentPage(pTopic.getCurrentEntryPage());
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

			Element infoElement = liEl.getElementsByAttributeValue("class", "info").get(0);
			Element entryDateElement = infoElement.getElementsByAttributeValue("class","entry-date permalink").get(0);
			String entryDate = entryDateElement.text();
			String entryHref = entryDateElement.attr("href");
			
			EntryModel entryModel = new EntryModel(entryHref);
			entryModel.setCommentCount(commentCount);
			entryModel.setDataIsFavorite(dataIsFavorite);
			SuserModel suser = new SuserModel();
			suser.setEntryAuthor(entryAuthor);
			suser.setEntryAuthorId(entryAuthorId);
			entryModel.setSuser(suser);
			entryModel.setEntryId(entryId);
			entryModel.setFavoriteCount(favoriteCount);
			
			
			
			
			
			
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
	
	@Override
	public GenericPager<TopicModel> retrieveChannelTopics(String pUrl)throws IOException {
		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("X-Requested-With","XMLHttpRequest")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		log.debug("Loading : " + pUrl);
		Response response = conn.execute();
		log.debug("Loaded  : " + response.url());
		
		Document doc = response.parse();
		
		GenericPager<TopicModel> currentPopularPage = new GenericPager<TopicModel>(response.url().toString());
		
		Element nextPageElement = doc.select("div.quick-index-continue-link-container > a").first();
		JsoupHtmlParser.parseNextPageForTopicPage(currentPopularPage, doc, nextPageElement);
		
		List<TopicModel> topicList = JsoupHtmlParser.parseTopicList(doc,TopicTypes.CHANNEL,"topic-list partial");
		
		currentPopularPage.setContentList(topicList);
		return currentPopularPage;
	
	}

	
	@Override
	public List<ChannelModel> retrieveChannels()throws IOException {
		
		Connection conn = Jsoup.connect(EksiciResourceUtil.getHeaderReferrer()).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.method(Method.GET);
		
		
		Response response = conn.execute();
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		
		List<ChannelModel> retList = new ArrayList<ChannelModel>();
		Elements anchors = doc.select("a.index-link");
		for(Element anchor : anchors){
			if(anchor.text().startsWith("#")){
				ChannelModel channel = new ChannelModel(anchor.attr("href"));
				channel.setTopicsUrl(anchor.attr("href"));
				channel.setTitle(anchor.attr("title"));
				channel.setName(anchor.text());
				retList.add(channel );
			}
		}
		return retList;
	
	}
	static class JsoupHtmlParser {
		
		private static void parseNextPageForTopicPage(GenericPager<TopicModel> currentPopularPage, Document doc,Element nextPageElement) throws UnsupportedEncodingException {
			if(nextPageElement == null){
				Element pagerDivelement = doc.select("div.pager").first();
				if(pagerDivelement != null){
					int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
					int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
					currentPopularPage.setMaxPage(maxPage);
					currentPopularPage.setCurrentPage(currentPage);
					if(currentPage < maxPage){
						String nextPageNumber = String.valueOf(currentPage+1);
						String href = MessageFormat.format(URLDecoder.decode(pagerDivelement.attr("data-urltemplate"),"UTF-8"),nextPageNumber);
						currentPopularPage.setNextPageHref(href);
					}
				}
			}else{
				currentPopularPage.setNextPageHref(nextPageElement.attr("href"));
			}
		}
		
		private static List<TopicModel> parseTopicList(Document doc, TopicTypes pTopicType, String pTopicListDivClass) {
			ArrayList<Element> ulElements = doc.getElementsByAttributeValue("class", pTopicListDivClass);
			List<TopicModel> topicList = new ArrayList<TopicModel>();
			if(ulElements == null || ulElements.size() == 0){ 
				return topicList;
			}
			Element topicListElement = ulElements.get(0);
			Elements liElements = topicListElement.getElementsByTag("li");
			for(Element liEl : liElements){
				if(liEl.id().contains("sponsored-index")){
					continue;
				}
				Element topicElement = liEl.getElementsByTag("a").get(0);
				String href = topicElement.attr("href");
				String topicText = ((TextNode)topicElement.childNode(0)).text();
				String topicPopularEntryCount = "0";
				TopicModel tm = new TopicModel(href);
				try {
					topicPopularEntryCount = ((Element)topicElement.childNode(1)).text();
					tm.setRelatedEntryCount(topicPopularEntryCount == null ? "0" : topicPopularEntryCount);
				} catch (Exception e) {
					tm.setRelatedEntryCount(null);
				}
				if(href.contains("?")){
					tm.setOriginalUrl(href.split("\\?")[0]);
				}else{
					tm.setOriginalUrl(href);
				}
				tm.setTopicText(topicText);
				tm.setType(pTopicType);
				
				topicList.add(tm);
			}
			return topicList;
		}
	}
}
