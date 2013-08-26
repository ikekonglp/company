package stanfordnlp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import utils.SerializeTool;
import utils.Stemmer;
import utils.UtilFunction;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class StanfordNLPTool {
	public static StanfordCoreNLP pipeline;
	
	public StanfordNLPTool(){
		Properties props = new Properties();
		
		props.put("annotators",
				"tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		props.setProperty("tokenize.whitespace", "true");
		pipeline = new StanfordCoreNLP(props);
	}

	public static Annotation getAnnotation(File inFile){
		Annotation annotation = new Annotation(IOUtils.slurpFileNoExceptions(inFile));
		pipeline.annotate(annotation);
		return annotation;
	}
	
	public static List<String> getDependencyParents(CoreMap cm, String w, boolean useReln){
		ArrayList<String> res = new ArrayList<String>();
		SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
		//System.out.println(w + "\t" + UtilFunction.returnString(w));
		List<IndexedWord> ws = dependencies.getAllNodesByWordPattern(UtilFunction.returnString(w));
		for(IndexedWord iw : ws){
			Collection<IndexedWord> rs2 = dependencies.getParents(iw);
			for(IndexedWord iww: rs2){
				
				String m = Stemmer.stem(iww.originalText().trim());
				if(useReln){
					GrammaticalRelation gr = dependencies.reln(iww, iw);
					m = gr.getShortName() + "_" + m;
				}
				res.add(m);
			}	
		}
		return res;
	}
	
	public static void main(String[] args) {
		StanfordNLPTool scnlp = new StanfordNLPTool();
		Annotation annotation = scnlp.getAnnotation(new File("sample_after.txt"));
		
		
		SerializeTool.writeAnnotation(new File("temp"), annotation);
		Annotation document = SerializeTool.readAnnotation(new File("temp"));
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	    
	    for(CoreMap sentence: sentences) {
	      // traversing the words in the current sentence
	      // a CoreLabel is a CoreMap with additional token-specific methods
	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	        // this is the text of the token
	        String word = token.get(TextAnnotation.class);
	        // this is the POS tag of the token
	        String pos = token.get(PartOfSpeechAnnotation.class);
	        // this is the NER label of the token
	        String ne = token.get(NamedEntityTagAnnotation.class);  
	        //System.out.println(word+"/"+pos+"/"+ne);
	      }

	      // this is the parse tree of the current sentence
	      Tree tree = sentence.get(TreeAnnotation.class);

	      // this is the Stanford dependency graph of the current sentence
	      SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
	    }

	    // This is the coreference link graph
	    // Each chain stores a set of mentions that link to each other,
	    // along with a method for getting the most representative mention
	    // Both sentence and token offsets start at 1!
	    Map<Integer, CorefChain> graph = 
	      document.get(CorefChainAnnotation.class);
		
	}

	public static List<String> getDependencyChildren(CoreMap cm, String w,
			boolean useReln) {
		ArrayList<String> res = new ArrayList<String>();
		SemanticGraph dependencies = cm.get(CollapsedCCProcessedDependenciesAnnotation.class);
		//System.out.println(w + "\t" + UtilFunction.returnString(w));
		List<IndexedWord> ws = dependencies.getAllNodesByWordPattern(UtilFunction.returnString(w));
		for(IndexedWord iw : ws){
			Collection<IndexedWord> rs2 = dependencies.getChildren(iw);
			for(IndexedWord iww: rs2){
				String m = Stemmer.stem(iww.originalText().trim());
				if(useReln){
					GrammaticalRelation gr = dependencies.reln(iw, iww);
					m = gr.getShortName() + "_" + m;
				}
				res.add(m);
			}	
		}
		return res;
	}

}
