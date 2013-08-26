package annoproc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import utils.LineReader;
import utils.UtilFunction;

/**
 * Gazette is a dictionary which tells us some NERs which we do know there names beforehand.
 * 
 * @author Lingpeng Kong
 *
 */
public class Gazette {
	
	private List<List<String>> nameList;
	private HashSet<String> gazette;
	private String gazetteName;
	
	public static List<List<String>>buildListFromFile(File f){
		List<List<String>> re = new ArrayList<List<String>>();
		LineReader lr = new LineReader(f);
		while(lr.hasNextLine()){
			String line = lr.readNextLine();
			if(line.equals("")) continue;
			List<String> list = new ArrayList<String>();
			String content = (line.trim().split("\\t"))[0].trim();
			list.add(content);
			System.out.println("Add:\t" + content);
			if(content.matches(".+\\(.+\\)")){
				String sub1 = content.substring(0, content.indexOf("(")).trim();
				String sub2 = content.substring(content.indexOf("(")+1, content.lastIndexOf(")"));
				list.add(sub1);
				list.add(sub2);
				System.out.println("Add:\t" + sub1);
				System.out.println("Add:\t" + sub2);
			}
			re.add(list);
		}
		lr.closeAll();
		return re;
	}
	
	public Gazette(List<List<String>> nl, String gn){
		gazetteName = gn;
		nameList = nl;
		construct();
	}
	
	public String getName(){
		return gazetteName;
	}
	
	private void construct(){
		gazette = new HashSet<String>();
		for(List<String> n : nameList){
			for(String s : n){
				gazette.add(s.toLowerCase());
			}
		}
	}
	
	public int inGazette(String s){
		return gazette.contains(s.toLowerCase()) ? 1 : 0;
	}
	
	public double getMaxiamSim(String ss){
		double max = -1;
		String m = ss.toLowerCase();
		for(List<String> n : nameList){
			for(String s : n){
				double sim = UtilFunction.getSim(s, m);
				if(sim > max){
					max = sim;
				}
			}
		}
		return max;
	}
	
	public int mPrefix(String ss){
		for(List<String> n : nameList){
			for(String s : n){
				if(UtilFunction.matchPrefix(ss, s)==1){
					return 1;
				}
			}
		}
		return 0;
	}
	public int mPrefixAbb(String ss){
		for(List<String> n : nameList){
			for(String s : n){
				if(UtilFunction.matchPrefixAbb(ss, s)==1){
					return 1;
				}
			}
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		// Just play around... not related to this class....
		String a = "a";
		String b = "b";
		String c = "ab";
		String d = a + b;
		String e = "a" + b;
		String f = "ab";
		System.out.println(c==d);
		System.out.println(c==e);
		System.out.println(d==e);
		System.out.println(c==f);
		System.out.println(d.equals(e));
		// Some real stuff...
		Gazette ga = new Gazette(buildListFromFile(new File("Gazette/corp")), "list");
	}

}
