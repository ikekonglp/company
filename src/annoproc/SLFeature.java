package annoproc;

import java.util.List;

public abstract class SLFeature {
	
	
	public abstract void initFeature(List<SLAnnotation> annos);
	
	
	// Generate a two-dimension array (the SLAnnoation contains a set of small annotations in) 
	// Of course, on the other hand, we should have a function inside the SLAnnotation which
	// generate the "rep" vector for the input annotation, the return value should only be a one
	// dimension vector
	public abstract double[][] generateVector(SLAnnotation anno);
	
	// Okay, Okay, I know you want to generate names for the feature 
	// so that you know what these strange numbers stands for
	public abstract String[] generateFeatureNames();
}
