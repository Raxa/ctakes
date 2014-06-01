package org.raxa;

public class Drug {
	
	private String drugName = "";
	private String frequency = "";
	private String frequencyUnit = "";
	private String dosage = "";
	private String route = "";
	private String duration="";
	private String strength="";
	private String strengthUnit="";
	private String form="";
	
	
	
	/**Blank Constructor. */
    public Drug() {

    }
    
    public Drug(String drugName, String freString, String freqString, String dosString,
    		String rouString, String durString, String streString, String strenuString, String foString) {
    		
    	this.drugName = drugName;
    	this.frequency = freString;
    	this.frequencyUnit=freqString;
    	this.dosage=dosString;
    	this.form=foString;
    	this.duration=durString;
    	this.route=rouString;
    	this.strength=streString;
    	this.strengthUnit=strenuString;
    	
    }
    
	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequecyUnit() {
		return frequencyUnit;
	}

	public void setFrequecyUnit(String frequecyUnit) {
		this.frequencyUnit = frequecyUnit;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
	
	/*@Override
	public String toString(){
		return new StringBuffer("Drug Name : ").append(drugName)
				.append("Dosage : ").append(dosage)
				.append("Strength : ").append(strength)
				.append("Strength Unit : ").append(strengthUnit)
				.append("Duration : ").append(duration)
				.append("Form : ").append(form)
				.append("Frequency : ").append(frequency)
				.append("Frequency Unit : ").append(frequecyUnit)
				.append("Route : ").append(route)			
				.toString();
	}*/
	

}
