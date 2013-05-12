package nlpprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.stanford.nlp.pipeline.Annotation;

import stanfordnlp.StanfordNLPTool;
import utils.SerializeTool;

/**
 * This NLPPipeLine, like the Tokenizer, is used to do the 
 * @author konglingpeng
 *
 */
public class NLPPipeLine {
	public static StanfordNLPTool snt = new StanfordNLPTool();
	
	public static void doNLP(File inputfile, File outputfile){
		Annotation annotation = StanfordNLPTool.getAnnotation(inputfile);
		SerializeTool.writeAnnotation(new File(outputfile.getAbsolutePath()+".obj"), annotation);
		//Annotation document = SerializeTool.readAnnotation(new File("temp"));
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(outputfile.getAbsolutePath()+".readable");
			StanfordNLPTool.pipeline.prettyPrint(annotation, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
		
	}
	
	
	public static String rootInputDir = "";
	public static void process(File inputdir, File outputdir) {
		
		if (inputdir.isDirectory()) {
			//File outf = new File(outputdir+'/'+f.getName());
			//outf.m
			File[] fs = inputdir.listFiles();
			for (File sf : fs) {
				process(sf, outputdir);
			}
		} else {
			if(!inputdir.getName().endsWith(".toked")) return;
			System.out.println(inputdir.getAbsolutePath());
			File outputD = new File(getOutputPath(inputdir,outputdir));
			
			if(!outputD.exists()){
				outputD.mkdirs();
			}
			
			File outputf = new File(getOutputPath(inputdir,outputdir)+"/"+inputdir.getName()+".nlp");
			
			try {
				doNLP(inputdir, outputf);
			} catch (Exception e) {
				File f1 = new File(outputf.getAbsolutePath()+".readable");
				File f2 = new File(outputf.getAbsolutePath()+".obj");
				if(f1.exists()){
					f1.delete();
				}
				if(f2.exists()){
					f2.delete();
				}
				e.printStackTrace();
				return;
			}
		
		}
	}
	
	public static String getOutputPath(File f, File outputdir){
		return outputdir.getAbsolutePath() + '/' + f.getAbsolutePath().substring(rootInputDir.length(),f.getAbsolutePath().lastIndexOf('/'));
	}
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("What?");
			System.err
					.println("This first arg is the input dir and the second is the output dir");
		}
		rootInputDir = (new File(args[0])).getAbsolutePath();
		System.out.println(rootInputDir);
		process(new File(args[0]), new File(args[1]));
	}

}
