package org.raxa;

public class NaturalLanguageGenerator {
	
	public static String getNaturalText(Drug drug){
		
		String naturalText="";
		
		//get the parameters
		String drugName = drug.getDrugName();
		String drugStrength = drug.getStrength();
		String drugStrengthUnit= drug.getStrengthUnit();
		String drugRoute = drug.getRoute();
		String drugFrequency= drug.getFrequency();
		String drugFrequencyUnit = drug.getFrequecyUnit();
		String drugForm = drug.getForm();
		String drugDuration = drug.getDuration();
		String drugDosage = drug.getDosage();

		//concatenate drug name and drug form
		//eg aspirin/atropine etc
		naturalText += drugName + " ";
		
		//concatenate drug strength
		//eg of the strength 50 mg/ 50 ml
		if(drugStrength.equals("")||drugStrengthUnit.equals(""))
			naturalText+="";
		else
			naturalText += "of strength " + drugStrength + " " + drugStrengthUnit + ", ";
		
		//concatenate drug route TODO
		//eg should be taken orally 
		if(drugRoute.equalsIgnoreCase("Enteral_Oral"))
			naturalText += "should be taken orally ";
				
		//concatenate drug dosage and form
		//eg in the quantity of two tablet/ two drops / two tablespoon
		if(drugDosage.equals(""))
			naturalText+="";
		else
			naturalText += "in the quantity of "+drugDosage+" "+drugForm+", ";
		
		
		//concatenate drug frequency and frequency unit
		//eg 2 times a day etc
		if(drugFrequency.equals("")||drugFrequencyUnit.equals(""))
			naturalText+="";
		else
			naturalText += drugFrequency + " time(s) a " + drugFrequencyUnit+" ";
		
		//concatenate drug duration
		//eg for a week, for seven days
		naturalText += drugDuration;
		
		return naturalText;
	}
	

}
