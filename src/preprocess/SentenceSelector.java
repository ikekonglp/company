package preprocess;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation that select sentences from the original document.
 * The main thing here is to filter sentences which is obviously not in our
 * interest by rules.
 * 
 * @author Lingpeng Kong
 * 
 */
public class SentenceSelector {
	public static List<String> filterSentences(List<String> sentences){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < sentences.size();i++){
			if(isDeleted(i,sentences)){
				continue;
			}else{
				list.add(cleanWithInSentence(sentences.get(i)));
			}
		}
		return list;
	}
	
	private static String cleanWithInSentence(String sen){
		if(!isSentence(sen)){
			sen = sen.trim() + " ."; // Make the parser happy
		}
		String[] tos = sen.split(" +");
		String fin = tos[0];
		for(int i = 1; i < tos.length; i++){
			String s = tos[i];
			if(s.equalsIgnoreCase("trademark") || s.equalsIgnoreCase("logo")){
				if(tos[i-1].matches("[A-Z].+")){
					continue;
				}
			}
			fin = fin + " " + s;
			
		}
		return fin.trim();
	}
	
	private static boolean isDeleted(int index, List<String> sentences){
		
		// The first and last sentence will be removed
		if(index == 0 || index == sentences.size()-1){
			return true;
		}
		
		
		String sen = sentences.get(index).trim();
		
		if(sen.split(" ").length < 4){
			return true;
		}
		
		String[] rules = new String[]{
				".+\\d{3}-\\d{4}", // should be the telephone number thing
				"\\W+", // non characters should be removed
				"-.+-", // start and end with "-" should not be considered
				"Press release .+",
				"PRESS RELEASE .+",
				"Press Release .+",
				"press release ."
		};
		
		for(String rule : rules){
			if(sen.matches(rule)){
				return true;
			}
		}
		
		if(!isSentence(sen)){
			if(!isSentence(sentences.get(index+1).trim())){
				return true; // Keep the possible title there.
			}
			if(sen.matches(".*\\d{3}-\\d{4}.*")){
				return true;
			}
			
		}
		return false;
	}
	
	private static boolean isSentence(String s){
		s = s.trim();
		char l = s.charAt(s.length()-1);
		if(l == '.' || l == '!' || l == '?'|| l=='\''){
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {

	}

}
