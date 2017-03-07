package org.borademir.eksici.api.model;

import java.util.Date;

import org.borademir.eksici.api.model.base.EksiBaseModel;
/**
 * @author bora.demir
 */
public class EntryModel extends EksiBaseModel {
	
	private String entryId;
	
	private SuserModel suser ;
	
	private String dataIsFavorite;
	
	private int favoriteCount;
	
	private int commentCount;
	
	private String entryText ;
	
	private String entryHtml ; 
	
	private String entryHref;
	
	private Date entryDate; 

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

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
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

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	

}
