package org.borademir.eksici.spring.controller;

import java.io.IOException;

import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/v1")
public class EkisiciRestApiController {
	
	@GetMapping("/search")
	public ResponseEntity<MainPageModel> getCustomer( 
			@RequestParam(value="keyword", required=true) String pKeyword,
			@RequestParam(value="author", required=true) String pAuthor
			
			) {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
//		searchCriteriaModel.setKeywords("aykut"); 
//		searchCriteriaModel.setAuthor("qlluq");
		
		searchCriteriaModel.setKeywords(pKeyword); 
		searchCriteriaModel.setAuthor(pAuthor);
		
		
		mainPage.setSearchCriteria(searchCriteriaModel);
	//	log.debug(searchCriteriaModel);
	//	log.debug("Search results:");
		GenericPager<TopicModel> searchResults = null;
		StringBuffer buffy = new StringBuffer();
		try {
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					buffy.append(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			
			return new ResponseEntity<MainPageModel>(mainPage, HttpStatus.OK);
		} catch (EksiApiException e) {
			return new ResponseEntity<MainPageModel>(mainPage, HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			return new ResponseEntity<MainPageModel>(mainPage, HttpStatus.NOT_FOUND);
		}
	
		
	}	

}
