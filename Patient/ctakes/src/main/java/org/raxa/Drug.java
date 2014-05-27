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
    
	public static String getDrugName() {
		return drugName;
	}

	public static void setDrugName(String drugName) {
		Drug.drugName = drugName;
	}

	public static String getFrequency() {
		return frequency;
	}

	public static void setFrequency(String frequency) {
		Drug.frequency = frequency;
	}

	public static String getFrequecyUnit() {
		return frequecyUnit;
	}

	public static void setFrequecyUnit(String frequecyUnit) {
		Drug.frequecyUnit = frequecyUnit;
	}

	public static String getDosage() {
		return dosage;
	}

	public static void setDosage(String dosage) {
		Drug.dosage = dosage;
	}

	public static String getRoute() {
		return route;
	}

	public static void setRoute(String route) {
		Drug.route = route;
	}

	public static String getDuration() {
		return duration;
	}

	public static void setDuration(String duration) {
		Drug.duration = duration;
	}

	public static String getStrength() {
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
