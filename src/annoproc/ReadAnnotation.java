package annoproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import utils.LineReader;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.CoreMap;

public class ReadAnnotation {
	public static int index = 1;
	
	public static SLAnnotation readAnnoFile(File f){
		LineReader lr = new LineReader(f);
		// We do not use the who line to do anything at this moment ...
		String wholine = lr.readNextLine();
		if(!wholine.startsWith("WHO;")){
			System.err.println("Something wrong when reading the WHO line. [ReadAnnotation.java]");
		}
		String whatline = lr.readNextLine();
		if(!whatline.startsWith("WHAT;")){
			System.err.println("Something wrong when reading the WHAT line. [ReadAnnotation.java]");
		}
		String senline = lr.readNextLine();
		if(!senline.startsWith("SENTENCES;")){
			System.err.println("Something wrong when reading the SENTENCES line. [ReadAnnotation.java]");
		}
		String keyline = lr.readNextLine();
		if(!keyline.startsWith("KEYS;")){
			System.err.println("Something wrong when reading the KEY line. [ReadAnnotation.java]");
		}
		String tagline = lr.readNextLine();
		if(!tagline.startsWith("TAGS;")){
			System.err.println("Something wrong when reading the WHO line. [ReadAnnotation.java]");
		}
		
		// In WHAT line, the thing we care about is the company name, so let's get it
		// The file name should also be in this line, but for now, let's just ignore it
		String[] temp = whatline.split(";");
		String companyName = temp[temp.length-1].trim();
		temp = senline.split(";");
		String text = temp[temp.length-1].trim();
		temp = keyline.split(";");
		Hashtable<String,String> keytable = new Hashtable<String,String>();
		for (int i = 1; i < temp.length; i++){
			String[] tt = temp[i].split(":");
			keytable.put(tt[1].trim(), tt[0].trim());
		}
		// For now, just randomly select one (the last one for now... So use a
		// hashtable here should be good)
		temp = tagline.split(";");
		Hashtable<String,String> tagtable = new Hashtable<String,String>();
		for (int i = 1; i < temp.length; i++){
			String[] tt = temp[i].split(":");
			tagtable.put(tt[0].trim(), tt[1].trim());
		}
		// Should finish all the things...
		
		Hashtable<String,Integer> indextable = new Hashtable<String,Integer>();
		
		lr.closeAll();
		
		// Use a data structure to store all these
		return new AnnoDataStru(f.getName(), companyName, text, keytable, tagtable, indextable);
	}
	
	public static ArrayList<SLAnnotation> readFoldAnno(File dir){
		ArrayList<SLAnnotation> list = new ArrayList<SLAnnotation>();
		File[] fs = dir.listFiles();
		for(File f : fs){
			System.out.println("Reading: "+ f.getName());
			SLAnnotation ads = readAnnoFile(f);
			list.add(ads);
		}
		return list;
	}
	
	public static void main(String[] args) {
		//ArrayList<AnnoDataStru> list = readFoldAnno(new File("Log"));
		//for(AnnoDataStru ads : list){
		//	System.out.println(ads);
		//}
		/*
		AnnoDataStru ads = readAnnoFile(new File("Annotate/2135-8-K-20060126.press.toked.nlp.readable.line210.txt"));
		System.out.println(ads);
		CoreMap cm = ads.getSentenceSD();
		SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
		List<IndexedWord> ws = dependencies.getAllNodesByWordPattern("Dutch");
		for(IndexedWord iw : ws){
			Collection<IndexedWord> rs = dependencies.getChildren(iw);
			for(IndexedWord iww: rs){
				GrammaticalRelation gr = dependencies.reln(iw, iww);
				System.out.println(iw + "\t" + iww + "\t" +gr.getShortName());
			}
			Collection<IndexedWord> rs2 = dependencies.getParents(iw);
			for(IndexedWord iww: rs2){
				GrammaticalRelation gr = dependencies.reln(iww, iw);
				System.out.println(iww.originalText() + "\t" + iw + "\t" +gr.getShortName());
			}
		}
		*/
	}
	

}
