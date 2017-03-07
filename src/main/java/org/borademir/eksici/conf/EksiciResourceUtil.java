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
			log.error("Can not load ekisici properies file" , ex);
//			ex.printStackTrace();
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

	public static String getUserAgent(){
		return PROPERTIES.getProperty("url.useragent") ;
	}
	
	
	public static String getHeaderReferrer(){
		return PROPERTIES.getProperty("header.referer");
	}
}
