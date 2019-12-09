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
		HashMap<String,Integer> stringToNumber = new HashMap<>();
		
		for(String temp:words) {
			if(temp.length()>=MINIMAL_WORD_LENGTH) {				
				if(stringToNumber.containsKey(temp)) {
					Word existingWord = new Word(temp, stringToNumber.get(temp));
					existingWord.setOccurences(existingWord.getOccurences()+1);
					stringToNumber.put(temp, existingWord.getOccurences());
				} else { 
					stringToNumber.put(temp, 1);
				}
			}
		}
		
		stringToNumber = resizeMap(stringToNumber);
		
		return new DecodedSong(inputSong.getName(), inputSong.getAuthorName(), stringToNumber);

	}

	private static HashMap<String, Integer> resizeMap(HashMap<String, Integer> stringToNumber) {
		HashMap<String, Integer> sortedMap = new HashMap<>();
		HashMap<String, Integer> result = new HashMap<>();
		sortedMap = (HashMap<String, Integer>) MapUtil.sortByValue(stringToNumber);

		for(String temp:sortedMap.keySet()) {
			result.put(temp, stringToNumber.get(temp));
			if (result.size()>TEXT_SAMPLES_NUMBER) return result;
		}
		return result;
	}
	
}
