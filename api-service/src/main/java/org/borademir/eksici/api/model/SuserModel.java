package org.borademir.eksici.api.model;
/**
 * @author bora.demir
 */
public class SuserModel {
	
	private String nick;
	
	private String suserId;
	
	private String href ;
	
	private String topicHref ;

	private String errorMessage ; 
	
	private EntryModel profileIntroEntry ;
	
	private String totalEntryCount ;
	
	private String lastMonthEntryCount ;
	
	private String lastWeekEntryCount ;
	
	private String todayEntryCount ;
	
	private String lastEntryTime ; 
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSuserId() {
		return suserId;
	}

	public void setSuserId(String suserId) {
		this.suserId = suserId;
	}

	public String getTopicHref() {
		return topicHref;
	}

	public void setTopicHref(String topicHref) {
		this.topicHref = topicHref;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public EntryModel getProfileIntroEntry() {
		return profileIntroEntry;
	}

	public void setProfileIntroEntry(EntryModel profileIntroEntry) {
		this.profileIntroEntry = profileIntroEntry;
	}

	public String getLastEntryTime() {
		return lastEntryTime;
	}

	public void setLastEntryTime(String lastEntryTime) {
		this.lastEntryTime = lastEntryTime;
	}

	public String getTotalEntryCount() {
		return totalEntryCount;
	}

	public void setTotalEntryCount(String totalEntryCount) {
		this.totalEntryCount = totalEntryCount;
	}

	public String getLastMonthEntryCount() {
		return lastMonthEntryCount;
	}

	public void setLastMonthEntryCount(String lastMonthEntryCount) {
		this.lastMonthEntryCount = lastMonthEntryCount;
	}

	public String getLastWeekEntryCount() {
		return lastWeekEntryCount;
	}

	public void setLastWeekEntryCount(String lastWeekEntryCount) {
		this.lastWeekEntryCount = lastWeekEntryCount;
	}

	public String getTodayEntryCount() {
		return todayEntryCount;
	}

	public void setTodayEntryCount(String todayEntryCount) {
		this.todayEntryCount = todayEntryCount;
	}
	
	

}
