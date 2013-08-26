package annoproc;

import java.util.List;

import utils.CounterTable;
import utils.Stemmer;
import utils.UtilFunction;

public class TextUnigramFeature extends BooleanFeature {

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		featureTable = new CounterTable();
		for(SLAnnotation sls : annos){
			AnnoDataStru ads = (AnnoDataStru)sls;
			String[] wtext = ads.text.split("\\s+");
			for(String w : wtext){
				w = w.trim();
				if(w.matches("[A-Za-z]+")){
					
					String m = Stemmer.stem(w.trim());
					featureTable.AddCount(m);
				}
			}
		}
		featureTable.removeSingleAndBuildIndex();
		System.out.println("Finishing building the TextUnigramFeature, size is " + featureTable.getSize());
	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru)anno;
		String text = ads.text;
		String[] wtext = text.split("\\s+");
		double[] a = UtilFunction.zeroArray(featureTable.getSize());
		for(String w : wtext){
			w = w.trim();
			String m = Stemmer.stem(w.trim());
			int index = featureTable.getIndex(m);
			if( index >= 0 ){
				//System.out.println(w+"\t"+m);
				a[index] = 1;
			}
		}
		return UtilFunction.duplicateArray(ads.keys.length, a);
	}

	@Override
	public String[] generateFeatureNames() {
		String[] names = new String[featureTable.getSize()];
		for(String fn : featureTable.table.keySet()){
			names[featureTable.getIndex(fn)] = "TUG_" + fn;
		}
		return names;
	}

}
