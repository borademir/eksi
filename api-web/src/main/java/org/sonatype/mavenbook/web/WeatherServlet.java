package org.sonatype.mavenbook.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.borademir.eksici.api.EksiApiException;
import org.borademir.eksici.api.EksiApiServiceFactory;
import org.borademir.eksici.api.IEksiService;
import org.borademir.eksici.api.model.GenericPager;
import org.borademir.eksici.api.model.MainPageModel;
import org.borademir.eksici.api.model.SearchCriteriaModel;
import org.borademir.eksici.api.model.TopicModel;

public class WeatherServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
    	
//		String keyword = request.getParameter("keyword" );
		
		IEksiService eksiciService = EksiApiServiceFactory.createService();
		MainPageModel mainPage = new MainPageModel();
		SearchCriteriaModel searchCriteriaModel = new SearchCriteriaModel();
		searchCriteriaModel.setKeywords("aykut"); 
		searchCriteriaModel.setAuthor("qlluq");
		mainPage.setSearchCriteria(searchCriteriaModel);
	//	log.debug(searchCriteriaModel);
	//	log.debug("Search results:");
		GenericPager<TopicModel> searchResults = null;
		StringBuffer buffy = new StringBuffer();
		PrintWriter out = response.getWriter();
		try {
			while((searchResults = eksiciService.search(mainPage)) != null){
				for(TopicModel tm : searchResults.getContentList()){
					buffy.append(tm.getTopicText() + "(" + tm.getRelatedEntryCount() + ") - " + tm.getOriginalUrl());
				}
			}
			out.println(buffy);
		} catch (EksiApiException e) {
			out.println( "Error " + e.getMessage() );
		}
	
	    out.flush();
	    out.close();
	
    }
	
}