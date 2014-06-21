package org.raxa;

public class Abbreviation {
	
	private String acronym;
	private String acronymText;
	private String acronymType;
	
	
	public Abbreviation(String acronym, String acronymText,String acronymType){
		this.acronym=acronym;
		this.acronymText=acronymText;
		this.acronymType=acronymType;		
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public String getAcronymText() {
		return acronymText;
	}
	public void setAcronymText(String acronymText) {
		this.acronymText = acronymText;
	}
	public String getAcronymType() {
		return acronymType;
	}
	public void setAcronymType(String acronymType) {
		this.acronymType = acronymType;
	}

}
