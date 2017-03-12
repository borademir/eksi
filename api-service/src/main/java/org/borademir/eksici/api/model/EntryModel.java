package org.borademir.eksici.api.model;

import org.borademir.eksici.api.model.base.EksiBaseModel;
/**
 * @author bora.demir
 */
public class EntryModel extends EksiBaseModel {
	
	private String entryId;
	
	private SuserModel suser ;
	
	private String dataIsFavorite;
	
	private Integer favoriteCount;
	
	private Integer commentCount;
	
	private String entryText ;
	
	private String entryHtml ; 
	
	private String entryHref;
	
	private String entryDate; 

	public EntryModel(String href) {
		super(href);
	}
	
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getDataIsFavorite() {
		return dataIsFavorite;
	}

	public void setDataIsFavorite(String dataIsFavorite) {
		this.dataIsFavorite = dataIsFavorite;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getEntryText() {
		return entryText;
	}

	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}

	public String getEntryHtml() {
		return entryHtml;
	}
	
	public void setEntryHtml(String entryHtml) {
		this.entryHtml = entryHtml;
	}

	public SuserModel getSuser() {
		return suser;
	}

	public void setSuser(SuserModel suser) {
		this.suser = suser;
	}

	public String getEntryHref() {
		return entryHref;
	}

	public void setEntryHref(String entryHref) {
		this.entryHref = entryHref;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	

}
