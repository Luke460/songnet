package songnet.tools;

import static songnet.constants.Constants.WORD_NOT_PRESENT_PENALITY_MULTIPLIER;
import static songnet.constants.Constants.WORD_SINGLE_PRESENCE_PENALITY;
import static songnet.constants.Constants.SEQUENCIAL_MULTIPLIER;

import java.util.HashMap;
import java.util.Map.Entry;

import songnet.model.PositionDetail;
import songnet.model.Word;

public class ScoreGenerator { 

	public static double generateScore(HashMap<String, PositionDetail> hashMap, HashMap<String, PositionDetail> hashMap2) {
		
		//HashMap<String,Integer> inputKeyPositions = getKeyPositions(input);
		//HashMap<String,Integer> storedKeyPositions = getKeyPositions(realSong);
		
		double globalScore = 0;
		int i = 1;
		int lastPosition = 0;
		for(Entry<String, PositionDetail> inputEntry:hashMap.entrySet()) {
			//int inputPos = inputKeyPositions.get(inputWord.getText());
			Word inputWord = new Word(inputEntry.getKey(), inputEntry.getValue());
			Word storedWord = null;
			if(hashMap2.containsKey(inputWord.getText())) {
				storedWord = new Word(inputWord.getText(),hashMap2.get(inputWord.getText()));
				
				//int storedPos = storedKeyPositions.get(inputWord.getText());
				
				globalScore += getSingleScore(storedWord, inputWord, lastPosition);
				lastPosition = i;
			} else {			
				globalScore -= inputWord.getPositionDetail().getOccurences() * WORD_NOT_PRESENT_PENALITY_MULTIPLIER;		
			}
			i++;
		}
			
		if(globalScore<0) return 0.0;
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
	
	private static double getSingleScore(Word wd_a,Word wd_b, int last_wd_b_pos) {
		
		if(!wd_a.getText().equals(wd_b.getText())) return 0;
		 
		double score = (wd_a.getPositionDetail().getOccurences() * wd_b.getPositionDetail().getOccurences()) - WORD_SINGLE_PRESENCE_PENALITY;
		
		if(wd_b.getPositionDetail().getPositions().contains(last_wd_b_pos+1)) score = score * SEQUENCIAL_MULTIPLIER;
		
		return score;
	}
	/*
	private static HashMap<String,Integer> getKeyPositions(HashMap<String,Word> map) {
		
		HashMap<String,Integer> wordToPosition = new HashMap<String,Integer>();

		int position = 0;
		for(String string:map.keySet()) {
			wordToPosition.put(string, position);
			position ++;
		}
		
		return wordToPosition;
		
	}
	*/
}
