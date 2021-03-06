package org.borademir.eksici.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author bora.demir
 */
public class EksiciResourceUtil {
	
	
//	static {
//		System.setProperty("http.proxyHost"  , "172.16.64.74");
//		System.setProperty("http.proxyPort"  , "8080");
//		System.setProperty("https.proxyHost" , "172.16.64.74");
//		System.setProperty("https.proxyPort" , "8080");
//	}
	
	static final Properties PROPERTIES = load()  ;
	
	public static void main(String[] args) {
		log.info(getPopularTopicsUrl());
	}
	
	static Logger log = Logger.getLogger(EksiciResourceUtil.class);
	
	private static Properties load(){
		
//		BasicConfigurator.configure();
		
		Properties retProp = new Properties();
		InputStream input = null;
		;
		try {
			
			input = new FileInputStream(EksiciResourceUtil.class.getResource("/config.properties").getFile());
			retProp.load(input);

		} catch (IOException ex) {
//			log.error("Can not load ekisici properies file" , ex);
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retProp;
	}
	
	public static String getPopularTopicsUrl(){
		return PROPERTIES.getProperty("url.populertopics");
	}
	
	public static String getTodaysTopicsUrl(long pTimeInMs){
		return PROPERTIES.getProperty("url.todaystopics") + pTimeInMs;
	}
	
	public static String getDesertedTopicsUrl(long pTimeInMs){
		return PROPERTIES.getProperty("url.desertedtopics") + pTimeInMs;
	}
	
	public static String getUserEntryStatsUrl(String pEntryType , String pSuserNick , long pTimeInMs){
		return String.format(PROPERTIES.getProperty("url.suser.entry.stats"), pEntryType, pSuserNick, String.valueOf(pTimeInMs));
	}
	
	public static String getAutocompleteUrl(long pTimeInMs, String pKeyword){
		return PROPERTIES.getProperty("url.autocomplete") + pTimeInMs + "&q=" + pKeyword ; //+ "^";
	}
	
	public static String getFavoritesUrl(long pTimeInMs, long pEntryId){
		return PROPERTIES.getProperty("url.favorites") + pTimeInMs + "&entryId=" + pEntryId ; //+ "^";
	}
	
	public static String getVideosUrl(long pTimeInMs){
		return PROPERTIES.getProperty("url.videos") + pTimeInMs;
	}
	
	public static String getTodayInHistoryTopicsUrl(long pTimeInMs, int pYear){
		return PROPERTIES.getProperty("url.todayinhistory") + pTimeInMs + "&year=" + pYear;
	}
	
	public static String getUserAgent(){
		return PROPERTIES.getProperty("url.useragent") ;
	}
	
	public static String getSearchUrl(){
		return PROPERTIES.getProperty("url.search") ;
	}
	
	public static String getHeaderReferrer(){
		return PROPERTIES.getProperty("header.referer");
	}

	public static String getSuserUrl(String pSuserNick) {
		return PROPERTIES.getProperty("url.suser") + pSuserNick;
	}

	public static String getLoginUrl() {
		return PROPERTIES.getProperty("url.login");
	}

	public static String getMessageUrl() {
		return PROPERTIES.getProperty("url.message");
	}
}
