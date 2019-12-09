package songnet.tools;

import static songnet.constants.Constants.MINIMAL_WORD_LENGTH;
import static songnet.constants.Constants.TEXT_SAMPLES_NUMBER;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import songnet.model.DecodedSong;
import songnet.model.Song;
import songnet.model.Word;

public class TextMapper {
	
	public static DecodedSong generateMap(Song inputSong) {
		
		//String cleanFullText = inputSong.getFullText().replaceAll("[^a-zA-Z0-9]","");
		String str = inputSong.getFullText().toLowerCase();
		str.replaceAll("[^a-zA-Z0-9]", " "); 
		
		List<String> words = Arrays.asList(str.split(" "));
		HashMap<String,Word> stringToWord = new HashMap<>();
		HashMap<String,Integer> stringToNumber = new HashMap<>();
		
		for(String temp:words) {
			if(temp.length()>=MINIMAL_WORD_LENGTH) {				
				if(stringToWord.containsKey(temp)) {
					Word existingWord = stringToWord.get(temp);
					existingWord.setOccurences(existingWord.getOccurences()+1);
					stringToNumber.put(temp, existingWord.getOccurences());
					stringToWord.put(temp, existingWord);
				} else { 
					Word word = new Word(temp, 1);
					stringToWord.put(temp, word);
					stringToNumber.put(temp, 1);
				}
			}
		}
		
		stringToWord = resizeMap(stringToNumber, stringToWord);
		
		return new DecodedSong(inputSong.getName(), inputSong.getAuthorName(), stringToWord);

	}

	private static HashMap<String, Word> resizeMap(HashMap<String, Integer> stringToNumber, HashMap<String, Word> stringToWord) {
		HashMap<String, Integer> sortedMap = new HashMap<>();
		HashMap<String, Word> result = new HashMap<>();
		sortedMap = (HashMap<String, Integer>) MapUtil.sortByValue(stringToNumber);

		for(String temp:sortedMap.keySet()) {
			result.put(temp, stringToWord.get(temp));
			if (result.size()>TEXT_SAMPLES_NUMBER) return result;
		}
		return result;
	}
	
}
