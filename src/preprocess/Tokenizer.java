package preprocess;

import java.io.StringReader;
import java.util.List;

import utils.LineReader;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;

/**
 * This is the class for tokenize the articles
 * @author Lingpeng Kong
 * 
 */

public class Tokenizer {
	// public static String tokenize(String s){
	// }

	public static String getNextSpan(LineReader lr) {
		String span = "";

		// First we will run through the thing and
		while (lr.hasNextLine()) {
			String line = lr.readNextLine().trim();
			if (line.equals("")) {
				continue;
			} else {
				span = span + line + " ";
				break;
			}

		}
		while (lr.hasNextLine()) {
			String line = lr.readNextLine().trim();
			if (line.equals("")) {
				break;
			} else {
				span = span + line + " ";
			}
		}
		return span.trim();

	}

	public static void main(String[] args) {
		LineReader lr = new LineReader("sample.txt");
		/*
		 * while (lr.hasNextLine()) { String line = lr.readNextLine().trim();
		 * if(line.equals("")) { System.out.print("Blank "); }else{
		 * System.out.print("Not Blank "); } System.out.println(line);
		 * 
		 * }
		 */
		int i = 0;
		while (lr.hasNextLine()) {
			String span = getNextSpan(lr);
			// System.out.println(span);
			StringReader reader = new StringReader(span);
			PTBTokenizer tokenizer = PTBTokenizer.newPTBTokenizer(reader);
			List list = tokenizer.tokenize();
			WordToSentenceProcessor wsp = new WordToSentenceProcessor();
			List<List<Word>> sentences = wsp.wordsToSentences(list);
			for (List<Word> sen : sentences) {
				for (Word word : sen) {
					System.out.print(word + " ");
				}
				System.out.println();
			}
		}
		lr.closeAll();
	}
}
