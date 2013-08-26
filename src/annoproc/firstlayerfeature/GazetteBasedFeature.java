package annoproc.firstlayerfeature;

import java.util.List;

import annoproc.AnnoDataStru;
import annoproc.Gazette;
import annoproc.SLAnnotation;
import annoproc.SLFeature;

public class GazetteBasedFeature extends SLFeature{
	
	private Gazette ga;
	
	public GazetteBasedFeature(Gazette g){
		ga = g;
	}

	@Override
	public void initFeature(List<SLAnnotation> annos) {
		
	}

	@Override
	public double[][] generateVector(SLAnnotation anno) {
		AnnoDataStru ads = (AnnoDataStru) anno;
		double[][] array = new double[ads.keys.length][2];
		for(int i = 0; i < ads.keys.length; i++){
			String keyString = (ads.keys)[i];
			String corekey = keyString.substring(0,keyString.indexOf('/')).replaceAll("_", " ");
			System.out.println(corekey);
			array[i][0] = ga.inGazette(corekey);
			array[i][1] = ga.getMaxiamSim(corekey);
			//array[i][2] = ga.mPrefix(corekey);
			//array[i][3] = ga.mPrefixAbb(corekey);
		}
		return array;
	}

	@Override
	public String[] generateFeatureNames() {
		return new String[]{"Gazeette_" + ga.getName()+"_match", "Gazeette_" + ga.getName() + "_maxsim"
				//, "Gazeette_" + ga.getName() + "_prefix","Gazeette_" + ga.getName() + "_pfabb"
				};
	}

}
