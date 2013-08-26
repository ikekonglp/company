//package annoproc;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import edu.stanford.nlp.ling.IndexedWord;
//import edu.stanford.nlp.semgraph.SemanticGraph;
//import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
//import edu.stanford.nlp.trees.GrammaticalRelation;
//import edu.stanford.nlp.util.CoreMap;
//
//import utils.Algorithm;
//import utils.CounterTable;
//import utils.LineWriter;
//import utils.Stemmer;
//
///**
// * In this class, what we are going to do is to construct 1/0 (at least for now
// * 1/0) features for logistic regression used in CReg. 
// * 
// * Author: Lingpeng Kong
// * E-Mail: lingpenk at cs dot cmu dot edu 
// * June 24, 2013 at Carnegie Mellon University
// * 
// */
//@Deprecated
//public class BuildingFeatures {
//	/*
//	 The feature structure now is:
//	 If the unigram or bigram only occur once in the entire corpus, it seems we have a good reason just ignore them... and that's what we do...
//	 [
//	  (The similarity between the Company Name and the Key in question, for example, "FORD MOTOR CO" & "Ford"),
//	  (Unigram features for the Key in question field. It seems that some government organizations should be found this way. For example, "u_s_department_of_justice" (split the '_' first...), )
//	  (Unigram features for the Text -- to get kind of a global view for the text),
//	  (Bigram features for the Text -- to get kind of a global view for the text),
//	  (Unigram features for the 3-word left & right context for the key in question).
//	  ===============
//	  The plan is to add the dependency parent unigram and dependency child unigram (this should be a useful feature)
//	  
//	 ]
//	 */
//	
//	public static ArrayList<AnnoDataStru> docList;
//	public static void init(){
//		 docList = ReadAnnotation.readFoldAnno(new File("Annotate"));
//		 buildUnigramFrameForKeyField();
//		 buildUnigramFrameForTextField();
//		 buildBigramFrameForTextField();
//		 buildUnigramFrameForDependencyParentField();
//		 buildUnigramFrameForKeyField();
//		 
//	}
//	
//	public static CounterTable unigramDependencyParentFieldTable;
//	public static void buildUnigramFrameForDependencyParentField(){
//		unigramDependencyParentFieldTable = new CounterTable();
//		for(AnnoDataStru ads : docList){
//			for(String keyString: ads.keytable.keySet()){
//				keyString = keyString.substring(0, keyString.indexOf('/'));
//				String[] kspl = keyString.split("_");
//				for(String w : kspl){
//					
//				}
//			}
//		}
//		unigramKeyFieldTable.removeSingleAndBuildIndex();
//		System.out.println("Finishing building the unigramDependencyParentFieldTable, size is " + unigramDependencyParentFieldTable.getSize());
//	}
//	private static double[] generateDependencyParentFieldUnigram(String keyString, AnnoDataStru ads){
//		double[] a = emptyArray(unigramDependencyParentFieldTable.getSize());
//		
//		CoreMap cm = ads.getSentenceSD();
//		SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
//		for(String keys : keyString.split("_")){
//		List<IndexedWord> ws = dependencies.getAllNodesByWordPattern(keys);
//		for(IndexedWord iw : ws){
//			Collection<IndexedWord> rs2 = dependencies.getParents(iw);
//			for(IndexedWord ww : rs2){
//				String w = ww.originalText().trim();
//				String m = Stemmer.stem(w.trim());
//				int index = unigramDependencyParentFieldTable.getIndex(m);
//				if( index >= 0 ){
//					a[index] = 1;
//				}
//			}
//		}
//		}
//		return a;
//	}
//	
//	private static double[] generateDependencyChildFieldUnigram(String keyString, AnnoDataStru ads){
//		double[] a = emptyArray(unigramDependencyChildFieldTable.getSize());
//		
//		CoreMap cm = ads.getSentenceSD();
//		SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
//		for(String keys : keyString.split("_")){
//		List<IndexedWord> ws = dependencies.getAllNodesByWordPattern(keys);
//		for(IndexedWord iw : ws){
//			Collection<IndexedWord> rs2 = dependencies.getParents(iw);
//			for(IndexedWord ww : rs2){
//				String w = ww.originalText().trim();
//				String m = Stemmer.stem(w.trim());
//				int index = unigramDependencyChildFieldTable.getIndex(m);
//				if( index >= 0 ){
//					a[index] = 1;
//				}
//			}
//		}
//		}
//		return a;
//	}
//	
//	public static CounterTable unigramDependencyChildFieldTable;
//	public static void buildUnigramFrameForDependencyChildField(){
//		unigramDependencyChildFieldTable = new CounterTable();
//		for(AnnoDataStru ads : docList){
//			for(String keyString: ads.keytable.keySet()){
//				keyString = keyString.substring(0, keyString.indexOf('/'));
//				String[] kspl = keyString.split("_");
//				for(String w : kspl){
//					CoreMap cm = ads.getSentenceSD();
//					SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
//					List<IndexedWord> ws = dependencies.getAllNodesByWordPattern(w);
//					for(IndexedWord iw : ws){
//						Collection<IndexedWord> rs = dependencies.getChildren(iw);
//						outter:for(IndexedWord iww: rs){
//							for(String kw : kspl){
//								if(iww.originalText().equals(kw)){
//									continue outter;
//								}
//							}
//							GrammaticalRelation gr = dependencies.reln(iw, iww);
//							System.out.println(iw + "\t" + iww + "\t" +gr.getShortName());
//							String m = Stemmer.stem(iww.originalText().trim());
//							unigramDependencyChildFieldTable.AddCount(m);
//						}
//					}
//				}
//			}
//		}
//		unigramDependencyChildFieldTable.removeSingleAndBuildIndex();
//		System.out.println("Finishing building the unigramDependencyChildFieldTable, size is " + unigramDependencyChildFieldTable.getSize());
//	}
//	
//	
//	
//	public static CounterTable unigramKeyFieldTable;
//	public static void buildUnigramFrameForKeyField(){
//		unigramKeyFieldTable = new CounterTable();
//		for(AnnoDataStru ads : docList){
//			for(String keyString: ads.keytable.keySet()){
//				keyString = keyString.substring(0, keyString.indexOf('/'));
//				for(String w : keyString.split("_")){
//					String m = Stemmer.stem(w.trim());
//					unigramKeyFieldTable.AddCount(m);
//				}
//			}
//		}
//		unigramKeyFieldTable.removeSingleAndBuildIndex();
//		System.out.println("Finishing building the unigramKeyFieldTable, size is " + unigramKeyFieldTable.getSize());
//	}
//	
//	public static CounterTable unigramTextFieldTable;
//	public static void buildUnigramFrameForTextField(){
//		unigramTextFieldTable = new CounterTable();
//		for(AnnoDataStru ads : docList){
//			String[] wtext = ads.text.split("\\s+");
//			for(String w : wtext){
//				w = w.trim();
//				if(w.matches("[A-Za-z]+")){
//					
//					String m = Stemmer.stem(w.trim());
//					unigramTextFieldTable.AddCount(m);
//				}
//			}
//		}
//		unigramTextFieldTable.removeSingleAndBuildIndex();
//		System.out.println("Finishing building the unigramTextFieldTable, size is " + unigramTextFieldTable.getSize());
//	}
//	
//	private static double[] generateContextFieldUnigram(String comkey, String text){
//		double[] a = emptyArray(unigramTextFieldTable.getSize());
//		String[] wtext = text.split("\\s+");
//		for(int i = 0; i < wtext.length; i++){
//			if(wtext[i].trim().equals(comkey)){
//				for(int j = -3; j <=3; j++){
//					String cw = getContextWord(wtext, i, j);
//					String m = Stemmer.stem(cw.trim());
//					int index = unigramTextFieldTable.getIndex(m);
//					if( index >= 0 ){
//						a[index] = 1;
//						//System.out.println(m);
//					}
//				}
//			}
//		}
//		return a;
//	}
//	private static String getContextWord(String[] wtext, int keyIndex, int offset){
//		if((keyIndex+offset) >= wtext.length || (keyIndex+offset) < 0 || offset == 0){
//			return "";
//		}
//		return wtext[keyIndex+offset];
//	}
//	
//	private static double[] generateTextFieldUnigram(String text){
//		double[] a = emptyArray(unigramTextFieldTable.getSize());
//		String[] wtext = text.split("\\s+");
//		for(String w : wtext){
//			w = w.trim();
//			String m = Stemmer.stem(w.trim());
//			int index = unigramTextFieldTable.getIndex(m);
//			if( index >= 0 ){
//				//System.out.println(w+"\t"+m);
//				a[index] = 1;
//			}
//		}
//		return a;
//	}
//	
//	public static CounterTable bigramTextFieldTable;
//	public static void buildBigramFrameForTextField(){
//		bigramTextFieldTable = new CounterTable();
//		for(AnnoDataStru ads : docList){
//			String[] wtext = ads.text.split("\\s+");
//			for(int i = 0; i < wtext.length-2; i++){
//				String w1 = wtext[i].trim();
//				String w2 = wtext[i+1].trim();
//
//				if(w1.matches("[A-Za-z]+") && w2.matches("[A-Za-z]+")){
//					String m1 = Stemmer.stem(w1.trim());
//					String m2 = Stemmer.stem(w2.trim());
//					bigramTextFieldTable.AddCount(m1+"_"+m2);
//				}
//			}
//		}
//		bigramTextFieldTable.removeSingleAndBuildIndex();
//		System.out.println("Finishing building the bigramTextFieldTable, size is " + bigramTextFieldTable.getSize());
//	}
//	private static double[] generateTextFieldBigram(String text){
//		double[] a = emptyArray(bigramTextFieldTable.getSize());
//		String[] wtext = text.split("\\s+");
//		for(int i = 0; i < wtext.length-2; i++){
//			String w1 = wtext[i].trim();
//			String w2 = wtext[i+1].trim();
//			String m1 = Stemmer.stem(w1.trim());
//			String m2 = Stemmer.stem(w2.trim());
//			int index = bigramTextFieldTable.getIndex(m1+"_"+m2);
//			if( index >= 0 ){
//				//System.out.println(m1+"\t"+m2);
//				a[index] = 1;
//			}
//		}
//		return a;
//	}
//	
//	
//	private static double[] emptyArray(int size){
//		double[] a = new double[size];
//		for(int i = 0; i < a.length; i++){
//			a[i] = 0;
//		}
//		return a;
//	}
//	
//	private static double[] generateKeyFieldUnigram(String key){
//		double[] a = emptyArray(unigramKeyFieldTable.getSize());
//		for(String w : key.split("_")){
//			String m = Stemmer.stem(w.trim());
//			int index = unigramKeyFieldTable.getIndex(m);
//			if( index >= 0 ){
//				a[index] = 1;
//			}
//		}
//		return a;
//	}
//	
//	// For one structure, print the feature vector for it ...
//	public static int instanceInd = 0;
//	public static void printFeatureVector(AnnoDataStru ads, LineWriter fw, LineWriter rw, boolean useSpecific){
//		
//		for(String keyString: ads.keytable.keySet()){
//			ArrayList<Double> featureList = new ArrayList<Double>();
//			
//			String corekey = keyString.substring(0,keyString.indexOf('/'));
//			
//			featureList.add(getSim(ads.companyName, corekey));
//			
////			double[] kuf = generateKeyFieldUnigram(corekey);
////			for(double kd : kuf){
////				featureList.add(kd);
////			}
////			
////			
////			
////			double[] tuf = generateTextFieldUnigram(ads.text);
////			for(double td : tuf){
////				featureList.add(td);
////			}
////			
////			double[] tbf = generateTextFieldBigram(ads.text);
////			for(double td : tbf){
////				featureList.add(td);
////			}
////			
////			double[] ctf = generateContextFieldUnigram(keyString, ads.text);
////			for(double cd : ctf){
////				featureList.add(cd);
////			}
//			
//			double[] dpuf = generateDependencyParentFieldUnigram(keyString,ads);
//			for(double cd : dpuf){
//				featureList.add(cd);
//			}
//			
//			double[] dcuf = generateDependencyChildFieldUnigram(keyString,ads);
//			for(double cd : dcuf){
//				featureList.add(cd);
//			}
//			
//			
//			if(!useSpecific){
//				System.out.println(instanceInd + "\t" + ads.tagtable.get(ads.keytable.get(keyString)));
//				rw.writeln(instanceInd + "\t" + ads.tagtable.get(ads.keytable.get(keyString)));
//			}else{
//				System.out.println(instanceInd + "\t" + ads.getMostSpecificLabel(keyString));
//				rw.writeln(instanceInd + "\t" + ads.getMostSpecificLabel(keyString));
//			}
//			
//			
//			System.out.print(instanceInd + "\t{");
//			fw.write(instanceInd + "\t{");
//			instanceInd++;
//			for(int fi = 0; fi < featureList.size()-1; fi++){
//				System.out.print("\"f"+ fi +"\": " + featureList.get(fi) + ", ");
//				fw.write("\"f"+ fi +"\": " + featureList.get(fi) + ", ");
//			}
//			System.out.print("\"f"+ (featureList.size()-1) +"\": " + featureList.get(featureList.size()-1) +"}");
//			System.out.println();
//			fw.writeln("\"f"+ (featureList.size()-1) +"\": " + featureList.get(featureList.size()-1) +"}");
//			
//		}
//		
//	}
//	
//	public static void generateFeatures(File featurefile, File repfile){
//		LineWriter fw = new LineWriter(featurefile);
//		LineWriter rw = new LineWriter(repfile);
//		for(AnnoDataStru ads: docList){
//			printFeatureVector(ads,fw,rw,true);
//		}
//		fw.closeAll();
//		rw.closeAll();
//		
//	}
//	
//	private static double getSim(String companyName, String key){
//		double d = Algorithm.computeLevenshteinDistance(companyName.toLowerCase(), key.toLowerCase());
//		return d/(Math.min(companyName.length(), key.length()) + 1.0);
//	}
//	
//	public static void main(String[] args) {
//		init();
//		generateFeatures(new File("feature"), new File("rep"));
//	}
//
//}
