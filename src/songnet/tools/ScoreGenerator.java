package songnet.tools;

import java.util.HashMap;
import java.util.TreeMap;

import songnet.model.Word;
import static songnet.constants.Constants.TEXT_SAMPLES_NUMBER;

public class ScoreGenerator { 

	public static double generateScore(HashMap<String,Word> input, HashMap<String,Word> realSong) {
		
		HashMap<String,Integer> inputKeyPositions = getKeyPositions(input);
		HashMap<String,Integer> storedKeyPositions = getKeyPositions(realSong);
		
		double globalScore = 0;
		
		for(Word inputWord:input.values()) {
			int inputPos = inputKeyPositions.get(inputWord.getText());
			
			Word storedWord = null;
			if(realSong.containsKey(inputWord.getText())) {
				storedWord = realSong.get(inputWord.getText());
				
				int storedPos = storedKeyPositions.get(inputWord.getText());
				
				globalScore += getSingleScore(storedWord, inputWord);
				
			} else {			
				// globalScore += 0;		
			}
	
		}
			
		return globalScore;
		
	}
	
	/*
	private static double getSingleScore(Word wd_a, int pos_a, Word wd_b, int pos_b) {
		
		if(!wd_a.getText().equals(wd_b.getText())) return 0;
		
		double coefficient = 0.5/TEXT_SAMPLES_NUMBER;
		 
		double score = getValueScore(pos_a, pos_b) * coefficient + getValueScore(wd_a.getOccurences(), wd_b.getOccurences()) * coefficient;
		
		return score;
	}
*/
	
	private static double getSingleScore(Word wd_a,Word wd_b) {
		
		if(!wd_a.getText().equals(wd_b.getText())) return 0;
		 
		double score = ((wd_a.getOccurences() * wd_b.getOccurences()) - 0.75) * 4;
		
		return score;
	}
	
	private static HashMap<String,Integer> getKeyPositions(HashMap<String,Word> map) {
		
		HashMap<String,Integer> wordToPosition = new HashMap<String,Integer>();

		int position = 0;
		for(String string:map.keySet()) {
			wordToPosition.put(string, position);
			position ++;
		}
		
		return wordToPosition;
		
	}
	
}
