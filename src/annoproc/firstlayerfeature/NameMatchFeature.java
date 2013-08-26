package annoproc.firstlayerfeature;

import java.util.List;

import utils.UtilFunction;

import annoproc.AnnoDataStru;
import annoproc.SLAnnotation;
import annoproc.SLFeature;

public class NameMatchFeature extends SLFeature{

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru) anno;
		double[][] array = new double[ads.keys.length][2];
		for(int i = 0; i < ads.keys.length; i++){
			String keyString = (ads.keys)[i];
			String corekey = keyString.substring(0,keyString.indexOf('/'));
			array[i][0] = UtilFunction.matchPrefix(corekey.replaceAll("_", " "), ads.companyName);
			array[i][1] = UtilFunction.matchPrefixAbb(corekey.replaceAll("_", " "), ads.companyName);
		}
		return array;
	}

	@Override
	public String[] generateFeatureNames() {
		return new String[]{"CN_Pre","CN_PreAbb"};
	}

}
