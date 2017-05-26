package org.borademir.eksici.spring.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.Autocomplete;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.EksiLoginSuser;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.SuserModel;
import org.borademir.eksici.api.model.TopicModel;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class EksiciRestApiController {
	
	private static final String VERSION_ONE = "/v1";
	
	static final Logger log = Logger.getLogger(EksiciRestApiController.class);
	
	private @Autowired HttpServletRequest httpServletRequest;
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView sayHello(ModelMap model) {
        return new ModelAndView("index");
    }
 
    
	@SuppressWarnings("unused")
	@GetMapping(VERSION_ONE + "/topic/search")
	public ResponseEntity<List<GenericPager<TopicModel>>> search( 
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="keyword", required=false) String pKeyword,
			@RequestParam(value="author", required=false) String pAuthor) throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
		searchCriteriaModel.setKeywords(pKeyword); 
		searchCriteriaModel.setAuthor(pAuthor);
		mainPage.setSearchCriteria(searchCriteriaModel);
		GenericPager<TopicModel> searchResults = null;
		try {
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
//					buffy.append(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getSearchResults(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	
	
	@GetMapping(VERSION_ONE + "/topic/popular")
	public ResponseEntity<GenericPager<TopicModel>> popularTopics(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="nextPageHref", required=false) String pNextHref
			) throws EksiApiException {
		pNextHref = parseNextToken(pNextHref,"nextPageHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Popular Topics:");
			String targetUrl = EksiciResourceUtil.getPopularTopicsUrl();
			if(pNextHref != null){
				targetUrl = EksiciResourceUtil.getHeaderReferrer() +  pNextHref;
			}
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrievePopularTopics(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	
	
	@GetMapping(VERSION_ONE + "/topic/today")
	public ResponseEntity<GenericPager<TopicModel>> todaysTopics(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="nextPageHref", required=false) String pNextHref) throws EksiApiException {
		pNextHref = parseNextToken(pNextHref,"nextPageHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Todays Topics:");
			String targetUrl = EksiciResourceUtil.getTodaysTopicsUrl(System.currentTimeMillis());
			if(pNextHref != null){
				targetUrl = EksiciResourceUtil.getHeaderReferrer() +  pNextHref;
			}
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodaysTopics(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	
	}

	@GetMapping(VERSION_ONE + "/topic/deserted")
	public ResponseEntity<GenericPager<TopicModel>> desertedTopics(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="nextPageHref", required=false) String pNextHref) throws EksiApiException {
		pNextHref = parseNextToken(pNextHref,"nextPageHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getDesertedTopicsUrl(System.currentTimeMillis());
			if(pNextHref != null){
				targetUrl = EksiciResourceUtil.getHeaderReferrer() +  pNextHref;
			}
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveDesertedTopics(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	@GetMapping(VERSION_ONE + "/topic/videos")
	public ResponseEntity<GenericPager<TopicModel>> videoTopics(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="nextPageHref", required=false) String pNextHref) throws EksiApiException {
		pNextHref = parseNextToken(pNextHref,"nextPageHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getVideosUrl(System.currentTimeMillis());
			if(pNextHref != null){
				targetUrl = EksiciResourceUtil.getHeaderReferrer() +  pNextHref;
			}
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveVideos(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	
	}

	@GetMapping(VERSION_ONE + "/topic/todayinhistory/{year}")
	public ResponseEntity<GenericPager<TopicModel>> todayInHistory(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@PathVariable("year") int pYear,@RequestParam(value="nextPageHref", required=false) String pNextHref) throws EksiApiException {
		pNextHref = parseNextToken(pNextHref,"nextPageHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getTodayInHistoryTopicsUrl(System.currentTimeMillis(),pYear);
			if(pNextHref != null){
				targetUrl = EksiciResourceUtil.getHeaderReferrer() +  pNextHref;
			}
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveTodayInHistoryTopics(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}


	@GetMapping(VERSION_ONE + "/channels")
	public ResponseEntity<List<ChannelModel>> channels() throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Channels:");
			List<ChannelModel> channels = eksiciService.retrieveChannels();
			for(ChannelModel channel : channels){
				log.debug(channel.getName() + " (" + channel.getTitle() + ") -- " + channel.getHref() );
			}
			return new ResponseEntity<List<ChannelModel>>(channels, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	
	@GetMapping(VERSION_ONE + "/channels/topics") 
	public ResponseEntity<GenericPager<TopicModel>> channelTopics(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="topicsHref", required=true) String pNextHref) throws EksiApiException {

		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			if(pNextHref.contains("?")){
				String[] splitted = pNextHref.split("\\?");
				pNextHref =  URLEncoder.encode(splitted[0],"UTF-8") + "?" + splitted[1];
			}else{
				pNextHref =  URLEncoder.encode(pNextHref,"UTF-8");
			}
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getHeaderReferrer() + pNextHref;
			GenericPager<TopicModel> popularTopicCurrentPage = eksiciService.retrieveChannelTopics(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(popularTopicCurrentPage, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	
	@GetMapping(VERSION_ONE + "/autocomplete") 
	public ResponseEntity<Autocomplete> autocomplete(@RequestParam(value="query", required=true) String pQueryParam) throws EksiApiException {

		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("autocomplete:");
			String targetUrl = EksiciResourceUtil.getAutocompleteUrl(System.currentTimeMillis(), pQueryParam);
			Autocomplete autocomplete = eksiciService.autocomplete(targetUrl);
			return new ResponseEntity<Autocomplete>(autocomplete, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	@GetMapping(VERSION_ONE + "/login") 
	public ResponseEntity<?> login(@RequestParam(value="email", required=true) String pEmail,@RequestParam(value="password", required=true) String pPassword) throws EksiApiException {

		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("login:");
			String targetUrl = EksiciResourceUtil.getLoginUrl();
			EksiLoginSuser suser = eksiciService.login(targetUrl,pEmail, pPassword);
			return new ResponseEntity<EksiLoginSuser>(suser, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	
	@GetMapping(VERSION_ONE + "/login/{token}") 
	public ResponseEntity<?> loginWithToken(@PathVariable("token") String pToken) throws EksiApiException {

		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("login with token:");
			String targetUrl = EksiciResourceUtil.getMessageUrl();
			EksiLoginSuser suser = eksiciService.loginWithToken(targetUrl,pToken);
			return new ResponseEntity<EksiLoginSuser>(suser, HttpStatus.OK);
		} catch (Exception e) {e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	
	@GetMapping(VERSION_ONE + "/messages") 
	public ResponseEntity<?> messages(@RequestHeader(value="Authorization",required=false) String pSozlukToken) throws EksiApiException {

		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("message:");
			String targetUrl = EksiciResourceUtil.getMessageUrl();
			EksiLoginSuser suser = eksiciService.messages(targetUrl,pSozlukToken);
			return new ResponseEntity<EksiLoginSuser>(suser, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	
	@GetMapping(VERSION_ONE + "/topics/entries") 
	public ResponseEntity<TopicModel> topicsEntries(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="topicsHref", required=true) String topicsHref) throws EksiApiException {
		topicsHref = parseNextToken(topicsHref,"topicsHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getHeaderReferrer() + topicsHref;
			TopicModel retTopic = eksiciService.retrieveEntries(targetUrl,false);
			return new ResponseEntity<TopicModel>(retTopic, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	
	@GetMapping(VERSION_ONE + "/topic/entries") 
	public ResponseEntity<TopicModel> topicEntries(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@RequestParam(value="topicsHref", required=true) String topicsHref) throws EksiApiException {
		topicsHref = parseNextToken(topicsHref,"topicsHref");
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Deserted Topics:");
			String targetUrl = EksiciResourceUtil.getHeaderReferrer() + topicsHref;
			TopicModel retTopic = eksiciService.retrieveEntries(targetUrl,true);
			return new ResponseEntity<TopicModel>(retTopic, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	
	
	@GetMapping(VERSION_ONE + "/entry/{entryId}")
	public ResponseEntity<TopicModel> entry(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@PathVariable("entryId") long pEntryId) throws EksiApiException {
		return topicEntries(pSozlukToken,"/entry/" +pEntryId );
	}
	
	
	@GetMapping(VERSION_ONE + "/suser/{suserNick}")
	public ResponseEntity<SuserModel> suser(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@PathVariable("suserNick") String pSuserNick) throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("suser:" + pSuserNick);
			String targetUrl = EksiciResourceUtil.getSuserUrl(pSuserNick);
			SuserModel resp = eksiciService.suser(targetUrl);
			return new ResponseEntity<SuserModel>(resp, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	
	}
	
	@GetMapping(VERSION_ONE + "/suser/{suserNick}/stats/entry/{statType}")
	public ResponseEntity<GenericPager<TopicModel>> suserEntryStats(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@PathVariable("suserNick") String pSuserNick,@PathVariable("statType") String pStatType) throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("suser:" + pSuserNick);
			String targetUrl = EksiciResourceUtil.getUserEntryStatsUrl(pStatType,pSuserNick,System.currentTimeMillis());
			GenericPager<TopicModel> resp = eksiciService.suserEntryStats(targetUrl);
			return new ResponseEntity<GenericPager<TopicModel>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	
	}
	
	@GetMapping(VERSION_ONE + "/entry/{entryId}/favorites")
	public ResponseEntity<List<SuserModel>> entryFavorities(
			@RequestHeader(value="Authorization",required=false) String pSozlukToken,
			@PathVariable("entryId") long pEntryId) throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("autocomplete:");
			String targetUrl = EksiciResourceUtil.getFavoritesUrl(System.currentTimeMillis(), pEntryId);
			List<SuserModel> resp = eksiciService.favorites(targetUrl,pSozlukToken);
			return new ResponseEntity<List<SuserModel>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	
	}
	
	@ExceptionHandler(EksiApiException.class)
	public ResponseEntity<EksiciRestErrorResponse> exceptionHandler(Exception ex) {
		EksiciRestErrorResponse error = new EksiciRestErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<EksiciRestErrorResponse>(error, HttpStatus.OK);
	}
	
	private String parseNextToken(String pNextHref,String pParamName) {
		if(httpServletRequest.getQueryString() != null && httpServletRequest.getQueryString().contains(pParamName)){
			return httpServletRequest.getQueryString().replaceAll(pParamName + "=", "");
		}
		return pNextHref;
	}

}
