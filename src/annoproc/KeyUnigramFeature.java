package annoproc;

import java.util.List;

import utils.CounterTable;
import utils.Stemmer;

public class KeyUnigramFeature extends BooleanFeature {

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		featureTable = new CounterTable();
		for (SLAnnotation sls : annos) {
			AnnoDataStru ads = (AnnoDataStru) sls;
			
			for (String keyString : ads.keys) {
				String ks = keyString.substring(0, keyString.indexOf('/'));
				for (String w : ks.split("_")) {
					String m = Stemmer.stem(w.trim());
					featureTable.AddCount(m);
				}
			}
		}
		featureTable.removeSingleAndBuildIndex();
		System.out
				.println("Finishing building the KeyUnigramFeature, size is "
						+ featureTable.getSize());
	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru) anno;
		double[][] b = new double[ads.keys.length][featureTable.getSize()];
		for(int i = 0; i < ads.keys.length; i++){
			String key = ((ads.keys)[i]).substring(0, ((ads.keys)[i]).indexOf('/'));
			
			for(String w : key.split("_")){
				String m = Stemmer.stem(w.trim());
				int index = featureTable.getIndex(m);
				if( index >= 0 ){
					b[i][index] = 1;
				}
			}
		}
		return b;
		
	}

	@Override
	public String[] generateFeatureNames() {
		String[] names = new String[featureTable.getSize()];
		for(String fn : featureTable.table.keySet()){
			names[featureTable.getIndex(fn)] = "KUG_" + fn;
		}
		return names;
	}


}
