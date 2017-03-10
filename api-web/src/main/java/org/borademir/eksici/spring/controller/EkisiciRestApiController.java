package org.borademir.eksici.spring.controller;

import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EkisiciRestApiController {
	
	@GetMapping("/search")
	public ResponseEntity<MainPageModel> getCustomer( 
			@RequestParam(value="keyword", required=true) String pKeyword,
			@RequestParam(value="author", required=true) String pAuthor) throws EksiApiException {
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
		searchCriteriaModel.setKeywords(pKeyword); 
		searchCriteriaModel.setAuthor(pAuthor);
		mainPage.setSearchCriteria(searchCriteriaModel);
		GenericPager<TopicModel> searchResults = null;
		StringBuffer buffy = new StringBuffer();
		try {
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					buffy.append(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			return new ResponseEntity<MainPageModel>(mainPage, HttpStatus.OK);
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
