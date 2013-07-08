package annoproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import utils.LineReader;

public class ReadAnnotation {
	public static AnnoDataStru readAnnoFile(File f){
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
		
		
		lr.closeAll();
		
		// Use a data structure to store all these
		return new AnnoDataStru(companyName, text, keytable, tagtable);
	}
	
	public static ArrayList<AnnoDataStru> readFoldAnno(File dir){
		ArrayList<AnnoDataStru> list = new ArrayList<AnnoDataStru>();
		File[] fs = dir.listFiles();
		for(File f : fs){
			AnnoDataStru ads = readAnnoFile(f);
			list.add(ads);
		}
		return list;
		
	}
	
	public static void main(String[] args) {
		ArrayList<AnnoDataStru> list = readFoldAnno(new File("Log"));
		for(AnnoDataStru ads : list){
			System.out.println(ads);
		}
	}
	

}
