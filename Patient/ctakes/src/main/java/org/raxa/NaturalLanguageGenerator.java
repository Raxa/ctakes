package org.raxa;

import java.util.HashMap;
import java.util.Map.Entry;

import com.gtranslate.Language;
import com.gtranslate.Translator;

public class NaturalLanguageGenerator {
	
	public static HashMap<String, Abbreviation> usedAbbr;
	
	public static String getNaturalText(Drug drug, HashMap<String, Abbreviation> usedAbbreviation){
		
		String naturalText="";
		usedAbbr = usedAbbreviation;
		
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
		if(drugRoute.equals(""))
			naturalText += getFromAccr("Route") + " ";
				
		//concatenate drug dosage and form
		//eg in the quantity of two tablet/ two drops / two tablespoon
		if(drugDosage.equals(""))
			naturalText+="";
		else
			naturalText += "in the quantity of "+drugDosage+" "+drugForm+", ";
		
		
		//concatenate drug frequency and frequency unit
		//eg 2 times a day etc
		if(drugFrequency.equals("")||drugFrequencyUnit.equals(""))
			naturalText+= getFromAccr("Frequency") + " ";
		else
			naturalText += drugFrequency + " time(s) a " + drugFrequencyUnit+" ";
		
		//concatenate drug duration if not present check in usedAbbr
		//eg for a week, for seven days
		if(drugDuration.equals("")){
			naturalText +=  getFromAccr("Duration");
		}
		else
			naturalText += drugDuration + " ";
		
		//Add other accronyms used if any
		naturalText += getFromAccr("Others").toLowerCase() +".";
		
		return naturalText;
	}

	private static String getFromAccr(String accrType) {
		System.out.println("In get from accr for "+accrType);
		String text = "";
		
		for(Entry<String, Abbreviation> entry : usedAbbr.entrySet()){
			if(entry.getValue().getAcronymType().equals(accrType)){
				text = entry.getValue().getAcronymText();
				usedAbbr.remove(entry);
				System.out.println("Used Abbr left: "+usedAbbr.size());
				return text;
			}
		}
		return text;
	}

	public static String langTranslator(String drugNaturalText, String language) {
		System.out.println("Converting to language: "+language);
		Translator translate = Translator.getInstance();
		
		HashMap<String,String> langMap = new HashMap<String, String>();
		langMap.put("hindi", Language.HINDI);
		langMap.put("gujarati", Language.GUJARATI);
		langMap.put("tamil", Language.TAMIL);
		langMap.put("telugu", Language.TELUGU);
		langMap.put("kannada", Language.KANNADA);
		langMap.put("urdu", Language.URDU);
		langMap.put("english", Language.ENGLISH);
		langMap.put("bengali",Language.BENGALI);
		
		String text = translate.translate(drugNaturalText, Language.ENGLISH, langMap.get(language.toLowerCase()));
		System.out.println(text);
		return text;
	}
	

}
