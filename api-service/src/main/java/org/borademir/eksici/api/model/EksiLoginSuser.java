package org.borademir.eksici.api.model;

import java.util.List;

public class EksiLoginSuser {
	
	private SuserModel suserInfo ;
	
	private String sozlukToken ;
	
	private List<Conversation> conversationList ;

	public String getSozlukToken() {
		return sozlukToken;
	}

	public void setSozlukToken(String sozlukToken) {
		this.sozlukToken = sozlukToken;
	}

	public SuserModel getSuserInfo() {
		return suserInfo;
	}

	public void setSuserInfo(SuserModel suserInfo) {
		this.suserInfo = suserInfo;
	}

	public List<Conversation> getConversationList() {
		return conversationList;
	}

	public void setConversationList(List<Conversation> conversationList) {
		this.conversationList = conversationList;
	}

}
