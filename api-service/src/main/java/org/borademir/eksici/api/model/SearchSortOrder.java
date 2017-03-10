package org.borademir.eksici.api.model;

public enum SearchSortOrder {
	
	DATE("Date") , COUNT("Count"),TOPIC("Topic");
	
    private final String name;       

    private SearchSortOrder(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String getName() {
		return name;
	}

}
