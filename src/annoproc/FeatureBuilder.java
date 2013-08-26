package annoproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.LineWriter;

// A feature builder should be able to register the feature 
// class we need and build the features
public class FeatureBuilder {
	public FeatureBuilder(List<SLAnnotation> annos){
		this.annos = annos;
		features = new ArrayList<SLFeature>();
	}
	
	// A feature builder should at least know where its annotations are
	private List<SLAnnotation> annos;
	
	// A feature builder should know what features it is going to build
	private List<SLFeature> features;
	
	public final void register(SLFeature f){
		features.add(f);
	}
	
	public final void buildFeatureFile(File ff){
		LineWriter fw = new LineWriter(ff);
		
		ArrayList<ArrayList<Double>> featureListAll = new ArrayList<ArrayList<Double>>();
		
		ArrayList<String> featureNameList = new ArrayList<String>();
		
		boolean firstTime = true;
		for(SLFeature slf : features){
			slf.initFeature(annos);
			
			for(String feaName : slf.generateFeatureNames()){
				featureNameList.add(feaName);
			}
			int ind = 0;
			for(SLAnnotation anno : annos){
				double[][] fv = slf.generateVector(anno);
				for(int i = 0; i < fv.length; i++){
					ArrayList<Double> onefl;
					if(firstTime){
						onefl = new ArrayList<Double>();
					}else{
						onefl = featureListAll.get(ind);
					}
					
					for(int j = 0; j < (fv[i]).length; j++){
						onefl.add(fv[i][j]);
					}
					
					if(firstTime){
						featureListAll.add(onefl);
						
					}
					ind++;
				}
				
			}
			firstTime = false;
		}
		
		for (int instanceInd = 0; instanceInd < featureListAll.size(); instanceInd++) {
//			System.out.print(instanceInd + "\t{");
			fw.write(instanceInd + "\t{");
			ArrayList<Double> featureList = featureListAll.get(instanceInd);
			System.out.println("NameListLength: "+ featureNameList.size());
			System.out.println("FeatureListLength: "+ featureList.size());
			for (int fi = 0; fi < featureList.size() - 1; fi++) {
//				System.out.print("\"" + featureNameList.get(fi) + "\": "
//						+ featureList.get(fi) + ", ");
				fw.write("\"" + featureNameList.get(fi) + "\": "
						+ featureList.get(fi) + ", ");
			}
//			System.out.print("\"" + featureNameList.get(featureList.size() - 1)
//					+ "\": " + featureList.get(featureList.size() - 1) + "}");
//			System.out.println();
			fw.writeln("\"" + featureNameList.get(featureList.size() - 1)
					+ "\": " + featureList.get(featureList.size() - 1) + "}");

		}
		fw.closeAll();
	}
	
	public final void buildRepFile(File rf){
		LineWriter rw = new LineWriter(rf);
	    ArrayList<String> repList = new ArrayList<String>();
		for(SLAnnotation anno : annos){
			String[] reps = anno.generateRepVector();
			for(int i = 0; i < reps.length; i++){
				repList.add(reps[i]);
			}
		}
		for(int instanceInd = 0; instanceInd < repList.size(); instanceInd++){
//			System.out.println(instanceInd + "\t" + repList.get(instanceInd));
			rw.writeln(instanceInd + "\t" + repList.get(instanceInd));
		}
		rw.closeAll();
	}
	
	public void buildAnnoList(File al){
		LineWriter rw = new LineWriter(al);
		int ind = 0;
	    //ArrayList<String> repList = new ArrayList<String>();
		for(SLAnnotation anno : annos){
			AnnoDataStru ads = (AnnoDataStru) anno;
			for(String k : ads.keys){
				
				//rw.writeln(ind+"\t"+ k + "\t" + ads.getMostGenerallLabel(k) + "\t" + ads.fileName+"\t" + ads.getMostSpecificLabel(k) +"\t" +ads.text);
				rw.writeln(ind+"\t"+ k + "\t" + ads.getMostGenerallLabel(k) +"\t"+ ads.companyName );
				
				ind++;
			}
		}
		
		rw.closeAll();
	}
	
	public static void main(String[]  args){
		ArrayList<SLAnnotation> annos = ReadAnnotation.readFoldAnno(new File("Annotate"));
		
		FeatureBuilder fb = new FeatureBuilder(annos);
		
		// Begin to register the features
		//fb.register(new NameSimFeature());
		//fb.register(new KeyUnigramFeature());
		//fb.register(new TextUnigramFeature());
		//fb.register(new DependencyParentUGFeature());
		
		//fb.buildFeatureFile(new File("feature"));
		fb.buildAnnoList(new File("al"));
		//fb.buildRepFile(new File("rep"));
		
		
		System.out.println();
	}

}
