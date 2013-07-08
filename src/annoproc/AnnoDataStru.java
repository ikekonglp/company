package annoproc;

import java.util.Hashtable;

public class AnnoDataStru {
	public String companyName;
	public String text;
	public Hashtable<String,String> keytable;
	public Hashtable<String,String> tagtable;
	
	
	
	public AnnoDataStru(String companyName, String text,
			Hashtable<String, String> keytable,
			Hashtable<String, String> tagtable) {
		super();
		this.companyName = companyName;
		this.text = text;
		this.keytable = keytable;
		this.tagtable = tagtable;
	}
	
	public String getMostSpecificLabel(String key){
		if(key.equals("Magna_Bank/ORGANIZATION")){
			System.out.println();
		}
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

	public String toString(){
		String s = companyName + "\n" + text + "\n" ; 
		for(String k : keytable.keySet()){
			s = s + k + ":" + keytable.get(k) + "\n";
		}
		s = s + "\n";
		for(String k : tagtable.keySet()){
			s = s + k + ":" + tagtable.get(k) + "\n";
		}
		return s;
	}

}
