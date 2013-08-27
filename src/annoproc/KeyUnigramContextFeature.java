package annoproc;

import java.util.ArrayList;
import java.util.List;

import utils.CounterTable;
import utils.Stemmer;

/**
 * 
 * @author Bill
 *
 */
public class KeyUnigramContextFeature extends BooleanFeature {
	private int contextSize;
	
	public KeyUnigramContextFeature(int contextSize) {
		super();
		this.contextSize = contextSize;
	}
	
	// FIXME: Shouldn't we use Stanford's tokenization instead of splitting on whitespace?
	// The splitting whitespace approach has issues (e.g.punctuation is included as tokens unless filtered out...).
	// Maybe it's a good idea to reorganize things a bit so that Stanford's annotations
	// are meshed together with Bryan's.  One useful thing to do would be to find token
	// sequences within Stanford tokenization which correspond to Bryan's keys (also, what
	// are "keys"? 
	@Override
	public void initFeature(List<SLAnnotation> annos) {
		this.featureTable = new CounterTable();
		for (SLAnnotation sls : annos) {
			AnnoDataStru ads = (AnnoDataStru) sls;

			String[] wtext = ads.text.split("\\s+");
			List<String> filteredTokens = new ArrayList<String>();
			for(String w : wtext){
				w = w.trim();
				if(w.matches("[A-Za-z]+")){
					filteredTokens.add(w);
				}
			}
			
			
			for (String keyValueStr : ads.keytable.values()) {
				int partialKeyValueStrIndex = 0;
				int partialOffset = 0;
				List<String> partialTokens = filteredTokens;
				while ((partialKeyValueStrIndex = partialTokens.indexOf(keyValueStr)) >= 0) {
					int keyValueStrIndex = partialKeyValueStrIndex + partialOffset; 
					
					for (int i = Math.max(keyValueStrIndex - this.contextSize, 0); i < Math.min(filteredTokens.size(), keyValueStrIndex + this.contextSize + 1); i++) {
						if (i != keyValueStrIndex)
							featureTable.AddCount(Stemmer.stem(filteredTokens.get(i)));
					}
					
					partialTokens = partialTokens.subList(partialKeyValueStrIndex + 1, partialTokens.size());
					partialOffset += partialKeyValueStrIndex + 1;
				}
			}
		}
		featureTable.removeSingleAndBuildIndex();
		System.out
				.println("Finishing building the KeyUnigramContextFeature, size is "
						+ featureTable.getSize());
	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru) anno;
		double[][] b = new double[ads.keys.length][featureTable.getSize()];
		
		String[] wtext = ads.text.split("\\s+");
		List<String> filteredTokens = new ArrayList<String>();
		for(String w : wtext){
			w = w.trim();
			if(w.matches("[A-Za-z]+")){
				filteredTokens.add(w);
			}
		}
		
		
		for (String keyValueStr : ads.keytable.values()) {
			int partialKeyValueStrIndex = 0;
			int partialOffset = 0;
			List<String> partialTokens = filteredTokens;
			while ((partialKeyValueStrIndex = partialTokens.indexOf(keyValueStr)) >= 0) {
				int keyValueStrIndex = partialKeyValueStrIndex + partialOffset; 
				
				for (int i = Math.max(keyValueStrIndex - this.contextSize, 0); i < Math.min(filteredTokens.size(), keyValueStrIndex + this.contextSize + 1); i++) {
					if (i != keyValueStrIndex) {
						String m = Stemmer.stem(filteredTokens.get(i));
						int index = this.featureTable.getIndex(m);
						if( index >= 0 ){
							b[i][index] = 1;
						}
					}
				}
				
				partialTokens = partialTokens.subList(partialKeyValueStrIndex + 1, partialTokens.size());
				partialOffset += partialKeyValueStrIndex + 1;
			}
		}
		
		return b;
	}

	@Override
	public String[] generateFeatureNames() {
		String[] names = new String[featureTable.getSize()];
		for(String fn : featureTable.table.keySet()){
			names[featureTable.getIndex(fn)] = "KUGC_" + fn;
		}
		return names;
	}


}
