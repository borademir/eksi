package org.borademir.eksici.spring.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.ChannelModel;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class EksiciRestApiController {
	
	static final Logger log = Logger.getLogger(EksiciRestApiController.class);
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView sayHello(ModelMap model) {
        return new ModelAndView("index");
    }
 
    
	@SuppressWarnings("unused")
	@GetMapping("/topic/search")
	public ResponseEntity<List<GenericPager<TopicModel>>> search( 
			@RequestParam(value="keyword", required=true) String pKeyword,
			@RequestParam(value="author", required=true) String pAuthor) throws EksiApiException {
		
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
	
	
	@GetMapping("/topic/popular")
	public ResponseEntity<List<GenericPager<TopicModel>>> popularTopics() throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		try {
			GenericPager<TopicModel> popularTopicCurrentPage = null;
			log.debug("Popular Topics:");
			while((popularTopicCurrentPage = eksiciService.retrievePopularTopics(mainPage)) != null){
				for(TopicModel tm : popularTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getPopularTopics(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	
	
	@GetMapping("/topic/today")
	public ResponseEntity<List<GenericPager<TopicModel>>> todaysTopics() throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		try {
			log.debug("Todays Topics:");
			GenericPager<TopicModel> todaysTopicCurrentPage = null;
			while((todaysTopicCurrentPage = eksiciService.retrieveTodaysTopics(mainPage)) != null){
				for(TopicModel tm : todaysTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
				}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getTodaysTopics(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}
	

	@GetMapping("/topic/deserted")
	public ResponseEntity<List<GenericPager<TopicModel>>> desertedTopics() throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		try {
			log.debug("Deserted Topics:");
			GenericPager<TopicModel> desertedTopicCurrentPage = null;
			while((desertedTopicCurrentPage = eksiciService.retrieveDesertedTopics(mainPage)) != null){
				for(TopicModel tm : desertedTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getDesertedTopics(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	@GetMapping("/topic/videos")
	public ResponseEntity<List<GenericPager<TopicModel>>> videoTopics() throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		try {
			log.debug("Video Topics:");
			GenericPager<TopicModel> videoTopicCurrentPage = null;
			while((videoTopicCurrentPage = eksiciService.retrieveVideos(mainPage)) != null){
				for(TopicModel tm : videoTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getVideoTopics(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}

	@GetMapping("/topic/todayinhistory/{year}")
	public ResponseEntity<List<GenericPager<TopicModel>>> todayInHistory(@PathVariable("year") int pYear) throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		try {
			log.debug("Today In History Topics:");
			GenericPager<TopicModel> desertedTopicCurrentPage = null;
			while((desertedTopicCurrentPage = eksiciService.retrieveTodayInHistoryTopics(mainPage,pYear)) != null){
				for(TopicModel tm : desertedTopicCurrentPage.getContentList()){
					log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
					}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(mainPage.getTodayInHistoryTopics(), HttpStatus.OK);
		} catch (Exception e) {
			throw new EksiApiException(e.getMessage());
		} 
	}


	@GetMapping("/channels")
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

	
	@GetMapping("/channels/{channel}/topics") 
	public ResponseEntity<List<GenericPager<TopicModel>>> channelTopics(@PathVariable("channel") String pChannelName) throws EksiApiException {
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		try {
			log.debug("Channel Topics:" + URLEncoder.encode(pChannelName,"UTF-8"));
			GenericPager<TopicModel> channelTopics = null;
			ChannelModel channelInput = null;
			
			List<ChannelModel> channels = eksiciService.retrieveChannels();
			for(ChannelModel channel : channels){
				String decodedHref = URLDecoder.decode(channel.getHref(), "UTF-8");
				log.debug(decodedHref + " - " + channel.getName());
				if(channel.getName().equals("#"+pChannelName)){
					channelInput = channel;
					break;
				}
			}
			if(channelInput == null) {
				throw new EksiApiException(pChannelName + " bulunamadı.");
			}
			while((channelTopics = eksiciService.retrieveChannelTopics(channelInput)) != null){
			for(TopicModel tm : channelTopics.getContentList()){
				log.debug(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getHref());
			}
			}
			return new ResponseEntity<List<GenericPager<TopicModel>>>(channelInput.getTopics(), HttpStatus.OK);
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

}
