package songnet.tools;

import static songnet.constants.Constants.WORD_NOT_PRESENT_PENALITY_MULTIPLIER;
import static songnet.constants.Constants.WORD_SINGLE_PRESENCE_PENALITY;
import static songnet.constants.Constants.SEQUENCIAL_MULTIPLIER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import songnet.model.PositionDetail;
import songnet.model.Word;

public class ScoreGenerator { 

	public static double generateScore(HashMap<String, PositionDetail> hashMap, HashMap<String, PositionDetail> hashMap2) {
		
		//HashMap<String,Integer> inputKeyPositions = getKeyPositions(input);
		//HashMap<String,Integer> storedKeyPositions = getKeyPositions(realSong);
		
		double globalScore = 0;
		int i = 0;
		HashMap<String,ArrayList<Integer>> wordToLastPosition = new HashMap<>();
		for(Entry<String, PositionDetail> inputEntry:hashMap.entrySet()) {
			//int inputPos = inputKeyPositions.get(inputWord.getText());
			Word inputWord = new Word(inputEntry.getKey(), inputEntry.getValue());
			Word storedWord = null;
			if(hashMap2.containsKey(inputWord.getText())) {
				storedWord = new Word(inputWord.getText(),hashMap2.get(inputWord.getText()));
				
				//int storedPos = storedKeyPositions.get(inputWord.getText());
				
				if(wordToLastPosition.containsKey(storedWord.getText())) {
					wordToLastPosition.get(storedWord.getText()).add(i);
				} else {
					ArrayList<Integer> temp = new ArrayList<>();
					temp.addAll(hashMap.get(inputWord.getText()).getPositions());
					wordToLastPosition.put(storedWord.getText(), temp);
				}
				
				globalScore += getSingleScore(storedWord, inputWord, wordToLastPosition.get(storedWord.getText()));
				
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
	
	private static double getSingleScore(Word stored,Word input, ArrayList<Integer> lastPositions) {
		
		if(!stored.getText().equals(input.getText())) return 0;
		 
		double score = (stored.getPositionDetail().getOccurences() * input.getPositionDetail().getOccurences()) - WORD_SINGLE_PRESENCE_PENALITY;
		
		for(Integer temp : lastPositions) {
			if(stored.getPositionDetail().getPositions().contains(temp+1)) { 
				score = score * SEQUENCIAL_MULTIPLIER;		
			}
		}
		
		
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
