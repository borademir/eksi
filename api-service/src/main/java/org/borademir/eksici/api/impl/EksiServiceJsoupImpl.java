package org.borademir.eksici.api.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.Autocomplete;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EntryModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.PageInfoModel;
import org.borademir.eksici.api.model.SuserModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.api.model.TopicTypes;
import org.borademir.eksici.api.test.ServiceTester;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.borademir.eksici.util.JSoupUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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
	public List<SuserModel> favorites(String targetUrl)throws EksiApiException, IOException {
		Connection conn = Jsoup.connect(targetUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("X-Requested-With","XMLHttpRequest")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.header("authority", "eksisozluk.com")
		.header("cookie", "alertsnap=636278554610692200; iq=03f0309a86d74d2c9c4177a8abd5e386; ASP.NET_SessionId=hui4lczufv124v004zypdres; __gfp_64b=aAMay2Jj_HBM_DjFfg51MSOtN2rx6Yd1xomJLR7iU6P.Q7; __RequestVerificationToken=XhbFHfcrw_RolvJnWTyU_-0TGyWH8z5QSGGLDpX1CEt_lDDpO_JqZOh1Edrhl6pk0Ou9VW46_P4i3Jt_oEG8j6JJzCItSmE_jEvhTgn20LY1; alertsnap=636291628291451400; a=1RnPURblGlIKknRtqxtbuIXfZxWcb56Jiyq77Rggptkonb8p5S7JN3oPyxEWMFpRL1JVkkcxd42lKoEDLtknY0kIMmJtqKcKgfqETJLmQVvEQqahk4cc/xIn71PMgIFxAfc2DHj5mFm+aL1DEUB0jIe8TbwHk0TRImipxwHccc0=; sticky_id=c6b84c1b75469737575eb170ca4d1693; _ga=GA1.2.1520393962.1490592560; _gid=GA1.2.1586832044.1494848879; _gat=1; __asc=8c7b5fa815c0bbe3eb65dd52ab5; __auc=e4eae55815b0e3d7354ffad6837; lastnwcrtid_314=^{^}; lastnwcrtid_318=^{^}")
		.method(Method.GET);
		
		
//		curl "https://eksisozluk.com/entry/favorileyenler?entryId=59551581^&_=1494847440942" 
		
		Response response = conn.execute();
		
		log.debug("Loaded : " + response.url());
		Document doc = response.parse();
		System.out.println(doc.html());
		return null;
	}
	
	@Override
	public Autocomplete autocomplete(String pUrl) throws IOException {

		
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
		
		JSONObject jsonObj = new JSONObject(doc.text());
//		{"Titles":["aykut","aykut kocaman","aykut erdoğdu","aykut demir","31 aralık 2016 aykut demir\u0027in gelinsiz düğünü","aykut erçetin","aykut zahid akman"],"Query":"aykut","Nicks":["aykut","aykut23","aykutungamsesi"]}

		Autocomplete serviceResponse = new Autocomplete();
		if(jsonObj.has("Query")){
			serviceResponse.setQuery(jsonObj.getString("Query"));
			serviceResponse.setTopicList(new ArrayList<TopicModel>());
			serviceResponse.setSuserList(new ArrayList<SuserModel>());
		
			if(jsonObj.has("Titles")){
				JSONArray titlesJsonArray = jsonObj.getJSONArray("Titles");
				for(int i=0;i<titlesJsonArray.length();i++){
					String title = titlesJsonArray.getString(i);
					TopicModel tm = new TopicModel("?q=" + title);  // like bknz.
					tm.setTopicText(title);
					serviceResponse.getTopicList().add(tm);
				}
			}
			
			if(jsonObj.has("Nicks")){
				JSONArray nickJsonArray = jsonObj.getJSONArray("Nicks");
				for(int i=0;i<nickJsonArray.length();i++){
					String nick = nickJsonArray.getString(i);
					SuserModel sm = new SuserModel();
					sm.setNick(nick);
					serviceResponse.getSuserList().add(sm);
				}
			
			}
		}
		
		return serviceResponse;
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
	private PageInfoModel getEntryNextPageHref(String pUrl, TopicModel returnTopic){
		try {
			if(pUrl.contains("focusto=")){
				URI uri = new URI(pUrl);
				pUrl = uri.getPath();
				String focusTo = uri.getQuery().replaceAll("focusto=", "");
				returnTopic.setFocusTo(focusTo);
				
			}else{
				pUrl = pUrl.replaceAll(EksiciResourceUtil.getHeaderReferrer(), "");
			}
			
			boolean anyParamExists = false;
			String[] splitted = pUrl.split("\\?");
			if(splitted.length>1){
				if(splitted[1] != null && splitted[1].length()>0){
					anyParamExists = true;
				}
			}
			if(!anyParamExists){
				PageInfoModel pi = new PageInfoModel();
				pi.setPageNumber(2);
				pi.setPageHref(pUrl + "?p=2");
				return pi;
			}
			String[] queryParams = splitted[1].split("&");
			for(String queryParam : queryParams){
				String[] pageParamArray = queryParam.split("=");
				if("p".equals(pageParamArray[0])){
					String existingPageParam = pageParamArray[0] + "=" + pageParamArray[1];
					int nextPageNum = Integer.valueOf(pageParamArray[1])+1;
					String newPageParam = "p=" + (nextPageNum);
					PageInfoModel pi = new PageInfoModel();
					pi.setPageNumber(nextPageNum);
					pi.setPageHref(pUrl.replaceAll(existingPageParam, newPageParam));
					return pi;
				}
			}
		} catch (Exception e) {
		}
		PageInfoModel pi = new PageInfoModel();
		pi.setPageNumber(2);
		pi.setPageHref(pUrl + "&p=2");
		return pi;
	}
	
	@Override
	public TopicModel retrieveEntries(String pUrl,boolean pUsePageHrefTemplate) throws IOException {
		
		
		Connection conn = Jsoup.connect(pUrl).ignoreContentType(true)
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.data("p",String.valueOf(pUrl))
		.header("Referer",EksiciResourceUtil.getHeaderReferrer())
		.header("Accept-Encoding", "gzip, deflate, sdch, br")
		.header("Accept-Language", "en-US,en;q=0.8,tr;q=0.6")
		.header("User-Agent",EksiciResourceUtil.getUserAgent())
		.ignoreHttpErrors(true)
		.method(Method.GET);
		
//		if(pSukelaMod != null){
//			conn.data("a", pSukelaMod.name().toLowerCase());
//		}
		Response response = conn.execute();
		if(pUrl.startsWith(EksiciResourceUtil.getHeaderReferrer() + "?q=")){
			pUrl = EksiciResourceUtil.getHeaderReferrer() + response.url().getPath();
		}
		TopicModel returnTopic = new TopicModel(pUrl);
		
		Document doc = response.parse();
		
		/**
		 * böyle bir şey yok. ama bunu demek istemiş olabilir misiniz: blogu icin impl edildi.
		 * eksisozluk bknz lari bulamasa da response olarak 404 donuyor ama sayfa da response ediyor.
		 * 
		**/
		if(response.statusCode() == 404){
			returnTopic.setErrorText("böyle bir şey yok");
			Elements suggesTopicsElements = doc.getElementsByAttributeValue("class", "suggested-title");
			if(suggesTopicsElements != null && suggesTopicsElements.size() > 0){
				for(Element suggestTopicEl : suggesTopicsElements){
					String href = suggestTopicEl.attr("href");
					String text = suggestTopicEl.text();
					
					TopicModel suggestTopic = new TopicModel(href);
					suggestTopic.setTopicText(text);
					
					if(returnTopic.getSuggestedTopicList() == null){
						returnTopic.setSuggestedTopicList(new ArrayList<TopicModel>());
						returnTopic.setErrorText("böyle bir şey yok. ama bunu demek istemiş olabilir misiniz");
					}
					
					returnTopic.getSuggestedTopicList().add(suggestTopic);
				}
			}
		}else{
			
			Element pagerDivelement = doc.select("div.pager").first();
			if(pagerDivelement == null){
				returnTopic.setCurrentEntryPage(1);
				returnTopic.setTotalEntryPage(1);
			}else{
				int maxPage = Integer.valueOf(pagerDivelement.attr("data-pagecount"));
				int currentPage = Integer.valueOf(pagerDivelement.attr("data-currentpage"));
				returnTopic.setCurrentEntryPage(currentPage);
				returnTopic.setTotalEntryPage(maxPage);
				
//				Element nextAnchor = pagerDivelement.select("a.next").first();
//				if(nextAnchor != null){
//					returnTopic.setNextPageHref(nextAnchor.attr("href"));
//				}else{}

				PageInfoModel nextPageInfo = getEntryNextPageHref(pUrl,returnTopic);
				returnTopic.setNextPageHref(nextPageInfo.getPageHref());
				
				String nextPageReplacement = "p=" + nextPageInfo.getPageNumber();
				
				if(pUsePageHrefTemplate){
					returnTopic.setPagingHrefTemplate(nextPageInfo.getPageHref().replaceAll(nextPageReplacement, "p="));
				}else{
					returnTopic.setPageList(new ArrayList<PageInfoModel>());
					
					for(int i=1;i<=maxPage;i++){
						PageInfoModel pager = new PageInfoModel();
						pager.setPageNumber(i);
						pager.setPageHref(nextPageInfo.getPageHref().replaceAll(nextPageReplacement, "p="+i));
						returnTopic.getPageList().add(pager);
					}
				}

			
			}
			
			Elements sukelaElements = doc.getElementsByAttributeValue("class", "nice-mode-toggler");
			
			if(sukelaElements != null && sukelaElements.size() > 0){
				Element spanEl = sukelaElements.get(0);
				Elements hrefElements = spanEl.getElementsByTag("a");
				if(hrefElements != null && hrefElements.size() > 0){
					if(hrefElements.size() == 1){
						returnTopic.setNiceAllHref(hrefElements.get(0).attr("href"));
					}else if(hrefElements.size() == 2){
						returnTopic.setNiceAllHref(hrefElements.get(0).attr("href"));
						returnTopic.setNiceTodayHref(hrefElements.get(1).attr("href"));
					}
				}
			}
			
			Elements moreEntryElements = doc.getElementsByAttributeValue("class", "showall more-data");
			
			if(moreEntryElements != null && moreEntryElements.size() > 0){
				if(moreEntryElements.size() < 3){
					TopicModel tBefore = new TopicModel(URLDecoder.decode(moreEntryElements.get(0).attr("href"),"UTF-8"));
					tBefore.setTopicText(moreEntryElements.get(0).text());
					returnTopic.setBeforeEntries(tBefore);
					if(moreEntryElements.size() == 2){
						TopicModel tAfter = new TopicModel(URLDecoder.decode(moreEntryElements.get(1).attr("href"),"UTF-8"));
						tAfter.setTopicText(moreEntryElements.get(1).text());
						returnTopic.setAfterEntries(tAfter);
					}
				}
			}
			
			
			
			Elements topicInfoElements = doc.getElementsByAttributeValue("itemprop", "url");
			if(topicInfoElements != null && topicInfoElements.size() > 0){
				Element topicLink = topicInfoElements.get(0);
				returnTopic.setOriginalUrl(topicLink.attr("href"));
				returnTopic.setTopicText(topicLink.text());
			}
			
			Element entryListUlElement = doc.getElementById("entry-list");
			Elements liElements = entryListUlElement.getElementsByTag("li");
			
			if(returnTopic.getEntryList() == null){
				returnTopic.setEntryList(new ArrayList<EntryModel>());
			}
			
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
				suser.setNick(entryAuthor);
				suser.setSuserId(entryAuthorId);
				entryModel.setSuser(suser);
				entryModel.setEntryId(entryId);
				entryModel.setFavoriteCount(favoriteCount);
				
				
				
				
				entryModel.setEntryDate(entryDate);
				
//			try {
//				entryModel.setEntryDate(EksiciDateUtil.formateEntryDate(entryDate));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
				
				Element authorElement = infoElement.getElementsByAttributeValue("class","entry-author").get(0);
				
				suser.setHref(authorElement.attr("href"));
				
				Element contentElement = liEl.getElementsByAttributeValue("class", "content").get(0);
				
				entryModel.setEntryText(contentElement.text());
				entryModel.setEntryHtml(contentElement.html());
				
				returnTopic.getEntryList().add(entryModel);
				
			}
		}
		
		return returnTopic;
	
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
	@Override
	public SuserModel suser(String targetUrl) throws EksiApiException,IOException {
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
		SuserModel retVal = new SuserModel();
		Elements suserNotFoundElements = doc.getElementsByAttributeValue("class","field-validation-error");
		if(suserNotFoundElements != null && suserNotFoundElements.size() > 0){
			retVal.setErrorMessage(suserNotFoundElements.get(0).text());
			return retVal;
		}
		
		Element userProfileEl = doc.select("#user-profile-title > a").first();
		if(userProfileEl != null) {
			retVal.setNick(userProfileEl.text());
			retVal.setTopicHref(userProfileEl.attr("href"));
		}
		
		retVal.setTotalEntryCount(JSoupUtil.getElementTextById(doc,"entry-count-total"));
		retVal.setLastMonthEntryCount(JSoupUtil.getElementTextById(doc, "entry-count-lastmonth"));
		retVal.setLastWeekEntryCount(JSoupUtil.getElementTextById(doc, "entry-count-lastweek"));
		retVal.setTodayEntryCount(JSoupUtil.getElementTextById(doc, "entry-count-today"));
		retVal.setLastEntryTime(JSoupUtil.getElementTextById(doc, "last-entry-time"));
		
		Element introEntry =  doc.select("#quote-entry > h2 > a").first();
		if(introEntry != null){
			EntryModel introEntryModel = new EntryModel(introEntry.attr("href"));
			introEntryModel.setEntryText(introEntry.text());
			retVal.setProfileIntroEntry(introEntryModel);
			
			introEntryModel.setEntryHtml(JSoupUtil.getElementInnerHtmlBySelector(doc, "#quote-entry > .content"));
			introEntryModel.setEntryDate(JSoupUtil.getElementTextBySelector(doc, "#quote-entry > footer"));
		}
		
		System.out.println(doc.html());
		return retVal;
	}

}
