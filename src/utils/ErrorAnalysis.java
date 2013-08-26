package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import annoproc.ReadAnnotation;
import annoproc.SLAnnotation;

public class ErrorAnalysis {
	public static HashMap<String, Integer> majorGroupAll = new HashMap<String, Integer>();
	
	private static String getLine(int i, File al){
		LineReader lr = new LineReader(al);
		while(lr.hasNextLine()){
			String s = lr.readNextLine().trim();
			if(s.startsWith(""+i)){
				lr.closeAll();
				return s;
			}
		}
		return null;
		
	}
	
	public static void getStat(File folder, int numberOfFold) {
		HashMap<String, Integer>[] majorGroup = new HashMap[numberOfFold];
		
		for (int i = 0; i < numberOfFold; i++) {
			majorGroup[i] = new HashMap<String, Integer>();
		}
		for (int i = 0; i < numberOfFold; i++) {
			LineReader lr = new LineReader(folder.getAbsolutePath() + "/" + i
					+ "_test_rep");
			while (lr.hasNextLine()) {
				String line = lr.readNextLine();
				// System.out.println(line);
				String label = (line.trim().split("\t"))[1].trim();
				// System.out.println(label);
				if (majorGroupAll.containsKey(label)) {
					majorGroupAll.put(label, majorGroupAll.get(label) + 1);
				} else {
					majorGroupAll.put(label, 1);
				}
				if (majorGroup[i].containsKey(label)) {
					majorGroup[i].put(label, majorGroup[i].get(label) + 1);
				} else {
					majorGroup[i].put(label, 1);
				}

			}
			lr.closeAll();
		}
		int allsum = 0;
		int allmax = -1;
		System.out
				.println("==================== All =============================");
		for (String key : majorGroupAll.keySet()) {

			System.out.println(key + "\t" + majorGroupAll.get(key));
			allsum = allsum + majorGroupAll.get(key);
			if (allmax < majorGroupAll.get(key)) {
				allmax = majorGroupAll.get(key);
			}

		}
		System.out.println("==========Major Percentage: "
				+ ((double) allmax / (double) allsum) + "=========");

		int i = 0;
		for (HashMap<String, Integer> g : majorGroup) {
			System.out.println("====================Fold " + i
					+ "=============================");
			i++;
			int sum = 0;
			int max = -1;
			for (String key : g.keySet()) {
				System.out.println(key + "\t" + g.get(key));
				sum = sum + g.get(key);
				if (max < g.get(key)) {
					max = g.get(key);
				}
			}

			System.out.println("==========Major Percentage: "
					+ ((double) max / (double) sum) + "=========");
		}

	}

	public static void confusionMatrix(File dir, int numberOfFold) {
		HashMap<String, Integer> clerror = new HashMap<String, Integer>();
		HashMap<String, Integer> confusion = new HashMap<String, Integer>();
		for (int i = 0; i < numberOfFold; i++) {
			LineReader gold = new LineReader(dir.getAbsolutePath() + "/" + i
					+ "_test_rep");
			LineReader predict = new LineReader(dir.getAbsolutePath() + "/" + i
					+ "_test_predict");
			while(gold.hasNextLine()){
				String gl = gold.readNextLine().trim();
				String pl = predict.readNextLine().replaceAll("\\\\", "").trim();
				if(gl.equals("")){
					break;
				}
				String gid = (gl.split("\t"))[0].trim();
				String pid = (pl.split("\t"))[0].trim();
				String gre = (gl.split("\t"))[1].trim();
				String pre = (pl.split("\t"))[1].trim();
				
				if(!gid.equals(pid)){
					System.err.println("What?");
					break;
				}
				
				if(!gre.equals(pre)){
					if(clerror.containsKey(gre)){
						clerror.put(gre, clerror.get(gre) + 1);
					}else{
						clerror.put(gre, 1);
					}
					
					String error_code = gre + "_" + pre;
					if(confusion.containsKey(error_code)){
						confusion.put(error_code, confusion.get(error_code) + 1);
					}else{
						confusion.put(error_code, 1);
					}
				}
			}
			gold.closeAll();
			predict.closeAll();
		}
		
		System.out.println("=========Class Number & Error Rate===========");
		for(String k : majorGroupAll.keySet()){
			System.out.println(k + "\t" + majorGroupAll.get(k) + "\t" + (double)(clerror.containsKey(k) ? clerror.get(k) : 0)/(double)majorGroupAll.get(k));
			
		}
		System.out.println("=============================================");
		
		System.out.println("=========Confusion Matrix============");
		for(String k : confusion.keySet()){
			System.out.println(k + "\t" + confusion.get(k));
		}
		System.out.println("=====================================");
	}
	
	public static void printError(File dir, int numberOfFold, String ll){
		for (int i = 0; i < numberOfFold; i++) {
			LineReader gold = new LineReader(dir.getAbsolutePath() + "/" + i
					+ "_test_rep");
			LineReader predict = new LineReader(dir.getAbsolutePath() + "/" + i
					+ "_test_predict");
			while(gold.hasNextLine()){
				String gl = gold.readNextLine().trim();
				String pl = predict.readNextLine().replaceAll("\\\\", "").trim();
				if(gl.equals("")){
					break;
				}
				String gid = (gl.split("\t"))[0].trim();
				String pid = (pl.split("\t"))[0].trim();
				String gre = (gl.split("\t"))[1].trim();
				String pre = (pl.split("\t"))[1].trim();
				
				if(!gid.equals(pid)){
					System.err.println("What?");
					break;
				}
				
				if((gre.equals(ll)) && !gre.equals(pre)){
					//System.out.println(Integer.parseInt(gid));
					System.out.println(getLine(Integer.parseInt(gid), new File("al")));
				}
			}
			gold.closeAll();
			predict.closeAll();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		getStat(new File("CReg"), 10);
		confusionMatrix(new File("CReg"), 10);
		printError(new File("CReg"), 10, "NonCorp");
	}

}
