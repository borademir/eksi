package org.borademir.eksici.api.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.borademir.eksici.api.model.base.PagingModel;
/**
 * @author bora.demir
 */
public class GenericPager<T> extends PagingModel{
	
	static Logger log = Logger.getLogger(GenericPager.class);

	private List<T> contentList ;
	
	private String description ;
	
	public GenericPager(String href) {
		super(href);
	}
	
	public List<T> getContentList() {
		return contentList;
	}
	
	public void setContentList(List<T> contentList) {
		this.contentList = contentList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
