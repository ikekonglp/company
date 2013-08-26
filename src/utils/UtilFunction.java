package utils;

public class UtilFunction {

	public static double[] zeroArray(int size){
		double[] a = new double[size];
		for(int i = 0; i < a.length; i++){
			a[i] = 0;
		}
		return a;
	}
	
	public static double[][] duplicateArray(int fsize, double[] oay){
		double[][] a = new double[fsize][oay.length]; 
		for(int i = 0; i < fsize; i++){
			for(int j = 0; j < oay.length; j++){
				a[i][j] = oay[j];
			}
		}
		return a;
	}
	
	public static boolean inArray(String[] a, String s){
		for(String m : a){
			if(s.equalsIgnoreCase(m)) return true;
		}
		return false;
	}

	public static String returnString(String str) {
		return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)").replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
	}
	
	public static double getSim(String companyName, String key){
		double d = Algorithm.computeLevenshteinDistance(companyName.toLowerCase(), key.toLowerCase());
		return d/(((double)companyName.length()+ (double)key.length())/2.0 + 1.0);
	}
	
	public static int matchPrefix(String s1, String s2){
		String[] ss1 = s1.trim().split(" ");
		String[] ss2 = s2.trim().split(" ");
		int i = 0; int j = 0;
		while(i< ss1.length && j < ss2.length){
			if(!ss1[i].equalsIgnoreCase(ss2[j])){
				return 0;
			}
			i++;
			j++;
		}
		return 1;
	}
	
	public static int  matchPrefixAbb(String s1, String s2){
		if(!s1.trim().matches("[A-Z]+")){
			return 0;
		}
		String[] ss2 = s2.trim().split(" ");
		int i = 0; int j = 0;
		while(i< s1.trim().length() && j < ss2.length){
			if(ss2[j].length()==0){
				j++; 
				continue;
			}
			if((s1.trim().charAt(i) != ss2[j].toUpperCase().charAt(0))){
				return 0;
			}
			i++;
			j++;
		}
		return 1;
	}
	
	public static void main(String[] args){
		System.out.println(matchPrefix("NCS", "NATIONAL COMPUTER SYSTEMS INC"));
		System.out.println(matchPrefixAbb("NCS", "NATIONAL COMPUTER SYSTEMS INC"));
		
		System.out.println(matchPrefix("FCNB", "FIRST CHARTER CORP /NC/"));
		System.out.println(matchPrefixAbb("FCNB", "FIRST CHARTER CORP /NC/"));
		
		System.out.println(matchPrefix("National Steel", "NATIONAL STEEL CORP"));
		System.out.println(matchPrefixAbb("National Steel", "NATIONAL STEEL CORP"));
		
	}
}
