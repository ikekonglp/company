package annoproc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class RepFilter {

	public static void main(String[] args) {
		HashMap<String, Integer> aset = new HashMap<String, Integer>();
		
		ArrayList<SLAnnotation> annos = ReadAnnotation.readFoldAnno(new File("Annotate"));
		for(SLAnnotation sla : annos){
			AnnoDataStru ads = (AnnoDataStru) sla;
			
			for(String key : ads.keys){
				String label = ads.getMostSpecificLabel(key);
				if(label!= null && label.startsWith("OCorp")){
					System.out.println(ads.getAnnoIndex(key) + "\t" + key + "\t" + label);
				}
				aset.put(label, aset.get(label) == null ? 1 : aset.get(label)+1 );
			}
			
		}
		for(String s : aset.keySet()){
			System.out.println(s + "\t" + aset.get(s));
		}

	}

}
