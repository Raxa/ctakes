package org.raxa;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ctakes.chunker.ae.Chunker;
import org.apache.ctakes.contexttokenizer.ae.ContextDependentTokenizerAnnotator;
import org.apache.ctakes.core.ae.CopyAnnotator;
import org.apache.ctakes.core.ae.OverlapAnnotator;
import org.apache.ctakes.core.ae.SentenceDetector;
import org.apache.ctakes.core.ae.SimpleSegmentAnnotator;
import org.apache.ctakes.core.ae.TokenizerAnnotatorPTB;
import org.apache.ctakes.core.resource.FileResource;
import org.apache.ctakes.core.resource.FileResourceImpl;
import org.apache.ctakes.drugner.ae.CopyDrugAnnotator;
import org.apache.ctakes.drugner.ae.DrugMentionAnnotator;
import org.apache.ctakes.lvg.ae.LvgAnnotator;
import org.apache.ctakes.necontexts.ContextAnnotator;
import org.apache.ctakes.postagger.POSTagger;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.TypeSystemDescription_impl;
import org.apache.uima.util.InvalidXMLException;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.ConfigurationParameterFactory;
import org.uimafit.factory.ExternalResourceFactory;
import org.uimafit.factory.ResourceCreationSpecifierFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;



public class CtakesService 
{

	/** String lookup window for future multiple uses. */
	private static final String LOOKUP_WINDOW_PATH = 
			"org.apache.ctakes.typesystem.type.textspan.LookupWindowAnnotation";

	/** String desc for future multiple uses. */
	private static final String DESC = "desc";

	/** String initial path for future multiple uses. */
	private static final String INIT_PATH = 
			"";

	/** Analysis Engine for extraction of terms from Doctor's text. */
	private static AnalysisEngine analysisEng;

	/** File Resource Class. */
	private static Class<FileResource> fileResClass = 
			org.apache.ctakes.core.resource.FileResource.class;

	/** Implementation of File Resource Class. */
	private static Class<FileResourceImpl> fileResClassImpl = 
			org.apache.ctakes.core.resource.FileResourceImpl.class;

	/** String for holding type system. */
	private static String tsdst = "org.apache.ctakes.typesystem.types.TypeSystem";

	/** Type System Description. */
	private static TypeSystemDescription tsd =
			TypeSystemDescriptionFactory.createTypeSystemDescription(tsdst);


	//initializes the Analysis Engine
	public static void initialize() throws ResourceInitializationException, InvalidXMLException, MalformedURLException{

		//Segmentation
		AnalysisEngineDescription simpleSegmentDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						SimpleSegmentAnnotator.class);

		//Tokenizer
		AnalysisEngineDescription tokenizerDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						TokenizerAnnotatorPTB.class, tsd);

		//Sentence Detection           
		AnalysisEngineDescription sentDetectDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						SentenceDetector.class, 
						SentenceDetector.SD_MODEL_FILE_PARAM, INIT_PATH 
						+ "SentenceDetection/sd-med-model.zip");

		//LVG Annotator
		AnalysisEngineDescription lvgDesc = 
				getLVGAnnotatorDesc();

		//context dependent tokenizer
		AnalysisEngineDescription contextDependentTokenizerDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						ContextDependentTokenizerAnnotator.class);

		//POS Tagger
		AnalysisEngineDescription posTagdesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						POSTagger.class, tsd,
						POSTagger.POS_MODEL_FILE_PARAM, INIT_PATH 
						+ "POSTagger/mayo-pos.zip");

		//Chunker
		AnalysisEngineDescription chunkerDesc = 
				getChunkerDesc();


		//Drug Lookup Window Annotators
		//Overlap Annotators
		AnalysisEngineDescription overlapdesc = 
				getOverlapAnnotatorDesc();

		//Copy Annotator
		AnalysisEngineDescription copyADesc = 
				getCopyAnnotatorDesc();

		//Copy Drug Annotator
		AnalysisEngineDescription copyDrugAnnotDesc = 
				getCopyDrugAnnotatorDesc();


		//Dictionary Lookup
		AnalysisEngineDescription dictlookupDesc = 
				getDictlookupDesc();

		//Drug Mention Annotator
		AnalysisEngineDescription drugMentionDesc = 
				getDrugMentionAnnot();

		//Status Annotator
		AnalysisEngineDescription statusDesc = 
				getStatusDesc();
		
		//Negation Annotator
		AnalysisEngineDescription negationDesc = 
				getNegationDesc();

		//Final Analysis Engine Description
		ArrayList<AnalysisEngineDescription> aedList =
				new ArrayList<AnalysisEngineDescription>();

		aedList.add(simpleSegmentDesc);            
		aedList.add(sentDetectDesc);            
		aedList.add(tokenizerDesc);  
		aedList.add(lvgDesc);
		aedList.add(contextDependentTokenizerDesc);
		aedList.add(posTagdesc);           
		aedList.add(chunkerDesc);                       
		aedList.add(overlapdesc);           
		aedList.add(copyADesc);
		aedList.add(copyDrugAnnotDesc);
		aedList.add(dictlookupDesc);
		aedList.add(drugMentionDesc);
		aedList.add(statusDesc);
		aedList.add(negationDesc);

		// Create the Analysis Engine
		analysisEng = AnalysisEngineFactory.createAggregate(
				getAnalysisEngineDesc(aedList));


	}


	private static AnalysisEngineDescription getNegationDesc() throws ResourceInitializationException {
		
		
		ConfigurationParameter[] configurationParameter = 
				new ConfigurationParameter[8];
		
		ConfigurationParameter maxLeftScopeSize = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"MaxLeftScopeSize", Integer.class, DESC, true);
		ConfigurationParameter maxRightScopeSize = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"MaxRightScopeSize", Integer.class, DESC, true);
		ConfigurationParameter ScopeOrder = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ScopeOrder", String.class, DESC, true);
		ConfigurationParameter ContextAnalyzerClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextAnalyzerClass", String.class, DESC, true);
		ConfigurationParameter ContextHitConsumerClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextHitConsumerClass", String.class, DESC, true);
		ConfigurationParameter WindowAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"WindowAnnotationClass", String.class, DESC, true);
		ConfigurationParameter FocusAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"FocusAnnotationClass", String.class, DESC, true);
		ConfigurationParameter ContextAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextAnnotationClass", String.class, DESC, true);
		
		configurationParameter[0]=maxLeftScopeSize;
		configurationParameter[1]=maxRightScopeSize;
		configurationParameter[2]=ScopeOrder;
		configurationParameter[3]=ContextHitConsumerClass;
		configurationParameter[4]=WindowAnnotationClass;
		configurationParameter[5]=FocusAnnotationClass;
		configurationParameter[6]=ContextAnnotationClass;
		configurationParameter[7]=ContextAnalyzerClass;
		
		String[] scopeOrderArr = new String[2];
		scopeOrderArr[0]="Left";
		scopeOrderArr[1]="Right";
		
		Object[] configVals = new Object[8];
		configVals[0]=7;
		configVals[1]=7;
		configVals[2]=scopeOrderArr;
		configVals[3]="org.apache.ctakes.necontexts.negation.NegationContextHitConsumer";
		configVals[4]="org.apache.ctakes.typesystem.type.textspan.Sentence";
		configVals[5]="org.apache.ctakes.typesystem.type.textsem.IdentifiedAnnotation";
		configVals[6]="org.apache.ctakes.typesystem.type.syntax.BaseToken";
		configVals[7]="org.apache.ctakes.necontexts.negation.NegationContextAnalyzer";
		
		AnalysisEngineDescription negationDesc = AnalysisEngineFactory.createPrimitiveDescription
				(ContextAnnotator.class, null,null,null,null
						,configurationParameter,configVals);
		return negationDesc;
	}

	private static AnalysisEngineDescription getStatusDesc() throws ResourceInitializationException {
		
		ConfigurationParameter[] configurationParameter = 
				new ConfigurationParameter[8];
		
		ConfigurationParameter maxLeftScopeSize = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"MaxLeftScopeSize", Integer.class, DESC, true);
		ConfigurationParameter maxRightScopeSize = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"MaxRightScopeSize", Integer.class, DESC, true);
		ConfigurationParameter ScopeOrder = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ScopeOrder", String.class, DESC, true);
		ConfigurationParameter ContextAnalyzerClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextAnalyzerClass", String.class, DESC, true);
		ConfigurationParameter ContextHitConsumerClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextHitConsumerClass", String.class, DESC, true);
		ConfigurationParameter WindowAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"WindowAnnotationClass", String.class, DESC, true);
		ConfigurationParameter FocusAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"FocusAnnotationClass", String.class, DESC, true);
		ConfigurationParameter ContextAnnotationClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ContextAnnotationClass", String.class, DESC, true);
		
		configurationParameter[0]=maxLeftScopeSize;
		configurationParameter[1]=maxRightScopeSize;
		configurationParameter[2]=ScopeOrder;
		configurationParameter[3]=ContextHitConsumerClass;
		configurationParameter[4]=WindowAnnotationClass;
		configurationParameter[5]=FocusAnnotationClass;
		configurationParameter[6]=ContextAnnotationClass;
		configurationParameter[7]=ContextAnalyzerClass;
		
		String[] scopeOrderArr = new String[2];
		scopeOrderArr[0]="Left";
		scopeOrderArr[1]="Right";
		
		Object[] configVals = new Object[8];
		configVals[0]=10;
		configVals[1]=10;
		configVals[2]=scopeOrderArr;
		configVals[3]="org.apache.ctakes.necontexts.negation.StatusContextHitConsumer";
		configVals[4]="org.apache.ctakes.typesystem.type.textspan.Sentence";
		configVals[5]="org.apache.ctakes.typesystem.type.textsem.IdentifiedAnnotation";
		configVals[6]="org.apache.ctakes.typesystem.type.syntax.BaseToken";
		configVals[7]="org.apache.ctakes.necontexts.negation.StatusContextAnalyzer";
		
		AnalysisEngineDescription negationDesc = AnalysisEngineFactory.createPrimitiveDescription
				(ContextAnnotator.class, null,null,null,null
						,configurationParameter,configVals);
		return negationDesc;
	}

	private static AnalysisEngineDescription getDrugMentionAnnot() throws ResourceInitializationException {
		ConfigurationParameter[] configurationParameter = 
				new ConfigurationParameter[4];
		
		ConfigurationParameter medicationRelatedSection = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"medicationRelatedSection", String.class, DESC, true);
		ConfigurationParameter DISTANCE = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"DISTANCE", Integer.class, DESC, true);
		ConfigurationParameter DISTANCE_ANN_TYPE = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"DISTANCE_ANN_TYPE", String.class, DESC, true);
		ConfigurationParameter STATUS_BOUNDARY_ANN_TYPE = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"STATUS_BOUNDARY_ANN_TYPE", String.class, DESC, true);
		
		configurationParameter[0]=medicationRelatedSection;
		configurationParameter[1]=DISTANCE;
		configurationParameter[2]=DISTANCE_ANN_TYPE;
		configurationParameter[3]=STATUS_BOUNDARY_ANN_TYPE;
		
		
		String[] medicArray = new String[33];
		for(int i=0;i<30;i++){
			medicArray[i]=String.valueOf(20101+i);
		}
		medicArray[30]="20133";
		medicArray[31]="20147";
		medicArray[32]="SIMPLE_SEGMENT";
		
		Object[] configVals = new Object[4];
		configVals[0]=medicArray;
		configVals[1]=1;
		configVals[2]="org.apache.ctakes.typesystem.type.textspan.Sentence";
		configVals[3]="org.apache.ctakes.typesystem.type.textspan.Sentence";
		
		AnalysisEngineDescription drugMentionDesc = AnalysisEngineFactory.createPrimitiveDescription(
				DrugMentionAnnotator.class, tsd, null, null,null,
				configurationParameter,configVals);
		
		return drugMentionDesc;
	}

	private static AnalysisEngineDescription getCopyDrugAnnotatorDesc() throws ResourceInitializationException {
		
		String uimaAnnotation = "uima.tcas.Annotation";
		TypeSystemDescription copyATsd = new TypeSystemDescription_impl();
		copyATsd.addType("org.apache.ctakes.typesystem.type.CopySrcAnnotation",
				null, uimaAnnotation);
		copyATsd.addType("org.apache.ctakes.typesystem.type.CopyDestAnnotation",
				null, uimaAnnotation);
		
		ConfigurationParameter[] configurationParameters = new ConfigurationParameter[4];
		
		ConfigurationParameter srcObjClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"srcObjClass", String.class, DESC, true);
		ConfigurationParameter destObjClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"destObjClass", String.class, DESC, true);
		ConfigurationParameter dataBindMap =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"dataBindMap", String.class, DESC, true);
		ConfigurationParameter sectionOverrideSet =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"sectionOverrideSet", String.class, DESC, true);
		
		dataBindMap.setMultiValued(true);
		sectionOverrideSet.setMultiValued(true);
		
		configurationParameters[0] = srcObjClass;
		configurationParameters[1] = destObjClass;
		configurationParameters[2] = dataBindMap;
		configurationParameters[3] = sectionOverrideSet;
		
		Object[] copyAconfVals = new Object[4];
		copyAconfVals[0] = "org.apache.ctakes.typesystem.type.syntax.NP";
		copyAconfVals[1] = LOOKUP_WINDOW_PATH;
		String[] dataBindArray = new String[2];
		dataBindArray[0] = "getBegin|setBegin";
		dataBindArray[1] = "getEnd|setEnd";
		copyAconfVals[2] = dataBindArray;
		String[] sectionOverRideArr = new String[4];
		sectionOverRideArr[0]="SIMPLE_SEGMENT";
		sectionOverRideArr[1]="20104";
		sectionOverRideArr[2]="20133";
		sectionOverRideArr[3]="20147";
		copyAconfVals[3]=sectionOverRideArr;

		Class<CopyDrugAnnotator> copyAclass = CopyDrugAnnotator.class;
		AnalysisEngineDescription copyDrugAnnotatorDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						copyAclass, copyATsd, null, null, null,
						configurationParameters, copyAconfVals);
		return copyDrugAnnotatorDesc;
		
	}

	private static AnalysisEngineDescription getAnalysisEngineDesc(
			ArrayList<AnalysisEngineDescription> aedList) throws ResourceInitializationException {
		AnalysisEngineDescription analysisEngdesc = 
				AnalysisEngineFactory.createAggregateDescription(
						aedList, getComponents(), 
						null, null, null, null);

		ConfigurationParameter[] finalDescConfParam = 
				new ConfigurationParameter[1];
		ConfigurationParameter chunkCreatorClass = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ChunkCreatorClass", String.class, DESC, true);
		finalDescConfParam[0] = chunkCreatorClass;
		Object[] finalDescConfVals = new Object[1];
		finalDescConfVals[0] = "org.apache.ctakes.chunker.ae.PhraseTypeChunkCreator";

		ResourceCreationSpecifierFactory.setConfigurationParameters(
				analysisEngdesc, finalDescConfParam, finalDescConfVals);

		return analysisEngdesc;
	}

	private static List<String> getComponents() {
		List<String> components = new ArrayList<String>();
		components.add("SimpleSegmentAnnotator");
		components.add("SentenceDetector");
		components.add("TokenizerAnnotator");
		components.add("LVGAnnotator");
		components.add("ContextDependentTokenizer");
		components.add("POSTagger");
		components.add("Chunker");
		components.add("OverlapAnnotator-Lookup");
		components.add("CopyAnnotator-Lookup");
		components.add("CopyDrugAnnotator");
		components.add("Dictionary Lookup");
		components.add("DrugMentionAnnotator");
		components.add("StatusAnnotator");
		components.add("NegationAnnotator");
		return components;
	}

	private static AnalysisEngineDescription getDictlookupDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	private static AnalysisEngineDescription getCopyAnnotatorDesc()
			throws ResourceInitializationException {

		String uimaAnnotation = "uima.tcas.Annotation";
		TypeSystemDescription copyATsd = new TypeSystemDescription_impl();
		copyATsd.addType("org.apache.ctakes.typesystem.type.CopySrcAnnotation",
				null, uimaAnnotation);
		copyATsd.addType("org.apache.ctakes.typesystem.type.CopyDestAnnotation",
				null, uimaAnnotation);
		ConfigurationParameter[] copyAconfParam = new ConfigurationParameter[3];
		ConfigurationParameter srcObjClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"srcObjClass", String.class, DESC, true);
		ConfigurationParameter destObjClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"destObjClass", String.class, DESC, true);
		ConfigurationParameter dataBindMap =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"dataBindMap", String.class, DESC, true);
		dataBindMap.setMultiValued(true);
		copyAconfParam[0] = srcObjClass;
		copyAconfParam[1] = destObjClass;
		copyAconfParam[2] = dataBindMap;

		Object[] copyAconfVals = new Object[3];
		copyAconfVals[0] = "org.apache.ctakes.typesystem.type.syntax.NP";
		copyAconfVals[1] = LOOKUP_WINDOW_PATH;
		String[] dataBindArray = new String[2];
		dataBindArray[0] = "getBegin|setBegin";
		dataBindArray[1] = "getEnd|setEnd";
		copyAconfVals[2] = dataBindArray;

		Class<CopyAnnotator> copyAclass = CopyAnnotator.class;
		AnalysisEngineDescription copyAnnotatorDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						copyAclass, copyATsd, null, null, null,
						copyAconfParam, copyAconfVals);
		return copyAnnotatorDesc;
	}

	private static AnalysisEngineDescription getLVGAnnotatorDesc() throws ResourceInitializationException {
		ConfigurationParameter[] configurationParameters =
				new ConfigurationParameter[11];
		
		ConfigurationParameter useSegment = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"UseSegments", Boolean.class, DESC, true);
		ConfigurationParameter segmentToSkip = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"SegmentsToSkip", String.class, DESC, true);
		ConfigurationParameter useCmdCache = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"UseCmdCache", Boolean.class, DESC, true);
		ConfigurationParameter cmdFileLocation = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"CmdCacheFileLocation", String.class, DESC, true);
		ConfigurationParameter cmdFreqCutOff = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"CmdCacheFrequencyCutoff", Integer.class, DESC, true);
		ConfigurationParameter exclusionSet = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ExclusionSet", String.class, DESC, true);
		ConfigurationParameter xeroxTreebankMap = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"XeroxTreebankMap", String.class, DESC, true);
		ConfigurationParameter postLemmas = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"PostLemmas", Boolean.class, DESC, true);
		ConfigurationParameter useLemmaCache = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"UseLemmaCache", Boolean.class, DESC, true);
		ConfigurationParameter lemmaCacheFileLocation = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"LemmaCacheFileLocation", String.class, DESC, true);
		ConfigurationParameter lemmaCacheFrequencyCutoff = 
				ConfigurationParameterFactory.createPrimitiveParameter(
						"LemmaCacheFrequencyCutoff", Integer.class, DESC, true);
		
		configurationParameters[0]=useSegment;
		configurationParameters[1]=segmentToSkip;
		configurationParameters[2]=useCmdCache;
		configurationParameters[3]=cmdFileLocation;
		configurationParameters[4]=cmdFreqCutOff;
		configurationParameters[5]=exclusionSet;
		configurationParameters[6]=xeroxTreebankMap;
		configurationParameters[7]=lemmaCacheFileLocation;
		configurationParameters[8]=useLemmaCache;
		configurationParameters[9]=lemmaCacheFrequencyCutoff;
		configurationParameters[10]=postLemmas;
		
		
		String[] exclusionSetArr = new String[2];
		exclusionSetArr[0]="the";
		exclusionSetArr[1]="The";
		
		String[] xeroxTreebankArr = new String[11];
		xeroxTreebankArr[0]="adj|JJ";
		xeroxTreebankArr[1]="adv|RB";
		xeroxTreebankArr[2]="aux|AUX";
		xeroxTreebankArr[3]="compl|CS";
		xeroxTreebankArr[4]="conj|CC";
		xeroxTreebankArr[5]="det|DT";
		xeroxTreebankArr[6]="modal|MD";
		xeroxTreebankArr[7]="noun|NN";
		xeroxTreebankArr[8]="prep|IN";
		xeroxTreebankArr[9]="pron|PRP";
		xeroxTreebankArr[10]="verb|VB";
		
		
		Object[] configVals = new Object[11];
		configVals[0] = false;
		configVals[1] = null;
		configVals[2] = false;
		configVals[3] = "Lvg/2005_norm.voc";
		configVals[4] = 20;
		configVals[5] = exclusionSetArr;
		configVals[6] = xeroxTreebankArr;
		configVals[7] = "Lvg/2005_lemma.voc";
		configVals[8] = false;
		configVals[9] = 20;
		configVals[10] = true;

		
		
		AnalysisEngineDescription lvgDesc = AnalysisEngineFactory.createPrimitiveDescription
				(LvgAnnotator.class, tsd, null,null,null,configurationParameters,configVals);
		
		return lvgDesc;
	}

	private static AnalysisEngineDescription getOverlapAnnotatorDesc()
			throws ResourceInitializationException {

		ConfigurationParameter[] configurationParameters =
				new ConfigurationParameter[5];
		ConfigurationParameter actionType =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"ActionType", String.class, DESC, true);
		ConfigurationParameter aObjectClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"A_ObjectClass", String.class, DESC, true);
		ConfigurationParameter bObjectClass =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"B_ObjectClass", String.class, DESC, true);
		ConfigurationParameter overlaptype =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"OverlapType", String.class, DESC, true);
		ConfigurationParameter deleteaction =
				ConfigurationParameterFactory.createPrimitiveParameter(
						"DeleteAction", String.class, DESC, true);

		deleteaction.setMultiValued(true);
		configurationParameters[0] = aObjectClass;
		configurationParameters[1] = bObjectClass;
		configurationParameters[2] = overlaptype;
		configurationParameters[3] = actionType;
		configurationParameters[4] = deleteaction;

		Object[] configVals = new Object[5];

		configVals[0] =
				LOOKUP_WINDOW_PATH;
		configVals[1] =
				LOOKUP_WINDOW_PATH;
		configVals[2] = "A_ENV_B";
		configVals[3] = "DELETE";
		String[] deleteActionArray = new String[1];
		deleteActionArray[0] = "selector=B";
		configVals[4] = deleteActionArray;

		Class<OverlapAnnotator> overlapAnnot = OverlapAnnotator.class;
		AnalysisEngineDescription overlapdesc =
				AnalysisEngineFactory.createPrimitiveDescription(overlapAnnot,
						null, null, null, null,
						configurationParameters, configVals);

		return overlapdesc;
	}

	private static AnalysisEngineDescription getChunkerDesc()
			throws MalformedURLException, ResourceInitializationException, 
			InvalidXMLException {
		String chunkfileres = INIT_PATH
				+ "Chunker/chunker-model.zip";
		String chunkurl = new File(chunkfileres).toURI().toURL().toString();
		String chunkp = "org.apache.ctakes.chunker.ae.DefaultChunkCreator";
		ExternalResourceDescription chunkererd =
				ExternalResourceFactory.createExternalResourceDescription(
						"ChunkerModelFile", fileResClassImpl, chunkurl);
		String chunkerModel = "ChunkerModel";
		AnalysisEngineDescription chunkerDesc =
				AnalysisEngineFactory.createPrimitiveDescription(
						Chunker.class, tsd,
						Chunker.CHUNKER_MODEL_FILE_PARAM, chunkfileres,
						Chunker.CHUNKER_CREATOR_CLASS_PARAM, chunkp,
						chunkerModel, chunkererd);

		ExternalResourceFactory.createDependency(
				chunkerDesc, chunkerModel, fileResClass);

		return chunkerDesc;
	}

	public static void main(String[] args) throws ResourceInitializationException, InvalidXMLException, MalformedURLException{
		initialize();
	}



}
