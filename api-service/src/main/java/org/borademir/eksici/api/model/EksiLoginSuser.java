package org.borademir.eksici.api.model;

public class EksiLoginSuser {
	
	private SuserModel suserInfo ;
	
	private String sozlukToken ;

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

}
