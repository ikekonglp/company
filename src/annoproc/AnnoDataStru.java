package annoproc;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import utils.SerializeTool;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class AnnoDataStru extends SLAnnotation{
	
	public static final int MOST_GENERAL_LABEL = 0;
	public static final int MOST_SPECIFIC_LABEL = 1;
	public static int label_select = MOST_GENERAL_LABEL;
	
	// Here we choose the label we want for the rep vector
	// At the beginning, we have the most general or most specific option
	public static void chooseLabel(int label_s){
		label_select = label_s;
	}
	
	public final String fileName;
	public final String companyName;
	public final String text;
	public final Hashtable<String,String> keytable;
	public final Hashtable<String,String> tagtable;
	public final Hashtable<String,Integer> indextable;
	
	// This is used for preventing the sequence of iterating the keyset changes
	public final String[] keys;
	
	public AnnoDataStru(String fileName, String companyName, String text,
			Hashtable<String, String> keytable,
			Hashtable<String, String> tagtable, Hashtable<String, Integer> indextable) {
		super();
		this.fileName = fileName;
		this.companyName = companyName;
		this.text = text;
		this.keytable = keytable;
		this.tagtable = tagtable;
		this.indextable = indextable;
		keys = keytable.keySet().toArray(new String[0]);
		for(int i = 0; i < keys.length; i++){
			indextable.put(keys[i], ReadAnnotation.index);
			ReadAnnotation.index = ReadAnnotation.index + 1;
		}
	}
	
	public int getAnnoIndex(String k){
		return indextable.get(k);
	}
	
	public String getMostSpecificLabel(String key){
		String firstkey = keytable.get(key);
		String ntag = tagtable.get(firstkey);
		String currentkey = firstkey;
		String stag = ntag;
		String label = ntag;
		while(true){
			currentkey = currentkey + "-" + stag;
			stag = tagtable.get(currentkey);
			if(stag==null) break;
			label = label + "-" + stag;
		}
		return label;
	}
	
	public String getMostGenerallLabel(String key){
		return tagtable.get(keytable.get(key));
	}

	public String toString(){
		String s = companyName + "\n" + text + "\n" ; 
		for(String k : keytable.keySet()){
			s = s + k + ":" + keytable.get(k) + "\n";
		}
		s = s + "\n";
		for(String k : tagtable.keySet()){
			s = s + k + ":" + tagtable.get(k) + "\n";
		}
		//SemanticGraph dependencies = sentenceSD.get(CollapsedCCProcessedDependenciesAnnotation.class);
		//dependencies.prettyPrint();
		return s;
	}

	@Override
	public Annotation StanfordNLPForPara() {
		return SerializeTool.readAnnotation(new File("download/" + fileName.substring(0,fileName.indexOf(".nlp")+4)+".obj"));
	}
	   
	@Override
	public CoreMap StanfrodNLPForSentence() {
		Annotation anno = SerializeTool.readAnnotation(new File("download/" + fileName.substring(0,fileName.indexOf(".nlp")+4)+".obj"));
		System.out.println("download/" + fileName.substring(0,fileName.indexOf(".nlp")+4)+".obj");
		List<CoreMap> sentences = anno.get(SentencesAnnotation.class);
		int linenum = Integer.parseInt(fileName.substring(fileName.indexOf(".line")+5, fileName.lastIndexOf(".")));
		return sentences.get(linenum - 1);
	}
	
	@Override
	public String[] generateRepVector() {
		String[] reps = new String[keys.length];
		for(int i = 0; i < keys.length; i++){
			if(label_select==MOST_GENERAL_LABEL){
				reps[i] = getMostGenerallLabel(keys[i]);
			}else if(label_select==MOST_SPECIFIC_LABEL){
				reps[i] = getMostSpecificLabel(keys[i]);
			}else{
				return null;
			}
		}
		return reps;
	}
	
	

}
