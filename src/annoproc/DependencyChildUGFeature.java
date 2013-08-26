package annoproc;

import java.util.List;

import stanfordnlp.StanfordNLPTool;
import utils.CounterTable;
import utils.UtilFunction;
import edu.stanford.nlp.util.CoreMap;

public class DependencyChildUGFeature extends BooleanFeature{
	public static boolean useReln = false;

	public static void setUseReln(boolean useReln) {
		DependencyParentUGFeature.useReln = useReln;
	}

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		featureTable = new CounterTable();

		for (SLAnnotation sls : annos) {

			CoreMap cm = sls.StanfrodNLPForSentence();
			

			AnnoDataStru ads = (AnnoDataStru) sls;
			System.out.println("Build Dependency for " + ads.fileName +" (init)");
			for (String keyString : ads.keys) {
				keyString = keyString.substring(0, keyString.indexOf('/'));
				String[] kspl = keyString.split("_");
				for (String w : kspl) {
					List<String> list = StanfordNLPTool.getDependencyParents(
							cm, w, useReln);
					for (String s : list) {
						if (!UtilFunction.inArray(kspl, s)) {
							featureTable.AddCount(s);
						}
					}
				}
			}

		}
		featureTable.removeSingleAndBuildIndex();
		System.out
				.println("Finishing building the DependencyChildUGFeature, size is "
						+ featureTable.getSize());

	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		CoreMap cm = anno.StanfrodNLPForSentence();
		AnnoDataStru ads = (AnnoDataStru) anno;
		System.out.println("Build Dependency for " + ads.fileName +" (get)");
		double[][] array = new double[ads.keys.length][featureTable.getSize()];
		for (int i = 0; i < ads.keys.length; i++) {
			String keyString = (ads.keys)[i];
			keyString = keyString.substring(0, keyString.indexOf('/'));
			String[] kspl = keyString.split("_");
			for (String w : kspl) {
				List<String> list = StanfordNLPTool.getDependencyChildren(
						cm, w, useReln);
				for (String s : list) {
					if (!UtilFunction.inArray(kspl, s)) {
						int index = featureTable.getIndex(s);
						if(index >=0){
							array[i][index] = 1;
						}
					}
				}
			}
		}
		return array;
	}

	@Override
	public String[] generateFeatureNames() {
		String[] names = new String[featureTable.getSize()];
		for(String fn : featureTable.table.keySet()){
			names[featureTable.getIndex(fn)] = "DCUG_" + fn;
		}
		return names;
	}

}
