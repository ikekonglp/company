package annoproc;

import java.io.File;
import java.util.ArrayList;

import annoproc.firstlayerfeature.GazetteBasedFeature;
import annoproc.firstlayerfeature.NameMatchFeature;
import annoproc.firstlayerfeature.NameSimFeature;

/**
 * This is the class which should responsible for building multiple layers of the model
 * Okay...Okay... I confess some parts of it are kind of like the ones in FeatureBuilder...
 * @author Lingpeng Kong
 */
public class TurboBuilder {

	public static void main(String[] args) {
		ArrayList<SLAnnotation> annos = ReadAnnotation.readFoldAnno(new File("Annotate"));
		
		FeatureBuilder fb = new FeatureBuilder(annos);
		fb.register(new NameSimFeature());
		fb.register(new NameMatchFeature());
		
		Gazette ga1 = new Gazette(Gazette.buildListFromFile(new File("Gazette/corp")),"corp");
		Gazette ga2 = new Gazette(Gazette.buildListFromFile(new File("Gazette/ncorplist")),"noncorp");
		
		
		fb.register(new GazetteBasedFeature(ga1));
		fb.register(new GazetteBasedFeature(ga2));
		
		// Begin to register the features
		//fb.register(new NameSimFeature());
		fb.register(new KeyUnigramFeature());
		fb.register(new TextUnigramFeature());
		fb.register(new DependencyParentUGFeature());
		
		//fb.buildFeatureFile(new File("feature"));
		fb.buildFeatureFile(new File("First_Layer_Feature"));
		fb.buildRepFile(new File("First_Layer_Rep"));
		//fb.buildAnnoList(new File("al"));
		//fb.buildRepFile(new File("rep"));
	}

}
