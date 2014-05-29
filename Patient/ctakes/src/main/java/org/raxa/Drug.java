package org.raxa;

public class Drug {
	
	private static String drugName;
	private static String frequency;
	private static String frequecyUnit;
	private static String dosage;
	private static String route;
	private static String duration;
	private static String strength;
	private static String strengthUnit;
	private static String form;
	
	
	
	/**Blank Constructor. */
    public Drug() {

    }
    
	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		Drug.drugName = drugName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		Drug.frequency = frequency;
	}

	public String getFrequecyUnit() {
		return frequecyUnit;
	}

	public void setFrequecyUnit(String frequecyUnit) {
		Drug.frequecyUnit = frequecyUnit;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		Drug.dosage = dosage;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		Drug.route = route;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		Drug.duration = duration;
	}

	public String getStrength() {
		return strength;
	}

	public static void setStrength(String strength) {
		Drug.strength = strength;
	}

	public static String getStrengthUnit() {
		return strengthUnit;
	}

	public static void setStrengthUnit(String strengthUnit) {
		Drug.strengthUnit = strengthUnit;
	}

	public static String getForm() {
		return form;
	}

	public static void setForm(String form) {
		Drug.form = form;
	}
	

}
