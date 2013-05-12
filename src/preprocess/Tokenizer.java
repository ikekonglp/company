package preprocess;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import utils.LineReader;
import utils.LineWriter;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;

/**
 * This is the class for tokenize the articles
 * 
 * @author Lingpeng Kong
 * 
 */

public class Tokenizer {
	
	public static String getNextSpan(LineReader lr) {
		String span = "";

		// First we will run through the thing and go over the blanks
		while (lr.hasNextLine()) {
			String line = lr.readNextLine().trim();
			if (line.equals("")) {
				continue;
			} else {
				span = span + line + " ";
				break;
			}

		}
		boolean suffer = true;
		while (lr.hasNextLine()) {
			String line = lr.readNextLine().trim();
			if (line.equals("")) {
				if(!suffer){
					break;
				}else{
					suffer = false;
					continue;
				}
			} else {
				String[] strs = line.split(" +");
				if(strs[strs.length-1].matches("[A-Z]+")){
					suffer = false;
				}else{
					suffer = true;
				}
				span = span + line + " ";
			}
		}
		return span.trim();

	}

	public static void processDocument(File inputfile, File outputfile) {
		LineReader lr = new LineReader(inputfile);
		LineWriter lw = new LineWriter(outputfile);
		List<String> tsens = new ArrayList<String>();
		while (lr.hasNextLine()) {
			String span = getNextSpan(lr);
			// System.out.println(span);
			StringReader reader = new StringReader(span);
			PTBTokenizer tokenizer = PTBTokenizer.newPTBTokenizer(reader);
			List list = tokenizer.tokenize();
			WordToSentenceProcessor wsp = new WordToSentenceProcessor();
			List<List<Word>> sentences = wsp.wordsToSentences(list);
			for (List<Word> sen : sentences) {
				String sl = "";
				for (Word word : sen) {
					sl = sl + word + " ";
					//System.out.print(word + " ");
				}
				tsens.add(sl);
				//System.out.println();
			}
		}
		lr.closeAll();

		//System.out.println("********************************************");
		List<String> nsens = SentenceSelector.filterSentences(tsens);
		for (String s : nsens) {
			//System.out.println(s);
			lw.writeln(s);
		}
		
		lw.closeAll();
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
			if(!inputdir.getName().endsWith(".press")) return;
			System.out.println(inputdir.getAbsolutePath());
			File outputD = new File(getOutputPath(inputdir,outputdir));
			
			if(!outputD.exists()){
				outputD.mkdirs();
			}
			
			File outputf = new File(getOutputPath(inputdir,outputdir)+"/"+inputdir.getName()+".toked");
			processDocument(inputdir, outputf);
		
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
