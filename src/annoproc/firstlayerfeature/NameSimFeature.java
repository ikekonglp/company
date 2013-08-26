package annoproc.firstlayerfeature;

import java.util.List;

import annoproc.AnnoDataStru;
import annoproc.SLAnnotation;
import annoproc.SLFeature;

import utils.UtilFunction;

public class NameSimFeature extends SLFeature {

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		// nothing to do here

	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru) anno;
		double[][] array = new double[ads.keys.length][1];
		for(int i = 0; i < ads.keys.length; i++){
			String keyString = (ads.keys)[i];
			String corekey = keyString.substring(0,keyString.indexOf('/'));
			array[i][0] = UtilFunction.getSim(ads.companyName, corekey);
		}
		return array;
	}

	@Override
	public String[] generateFeatureNames() {
		return new String[]{"Sim"};
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
