package annoproc;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public abstract class SLAnnotation {
	public abstract Annotation StanfordNLPForPara();
	
	// It should be the implementation classes responsibility to find the line number or related information
	public abstract CoreMap StanfrodNLPForSentence();
	
	public abstract String[] generateRepVector();
}
