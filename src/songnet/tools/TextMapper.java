package songnet.tools;

import static songnet.constants.Constants.MINIMAL_WORD_LENGTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import songnet.model.DecodedSong;
import songnet.model.Song;
import songnet.model.Word;
import songnet.model.PositionDetail;

public class TextMapper {
	
	public static DecodedSong generateMap(Song inputSong) {
		
		//String cleanFullText = inputSong.getFullText().replaceAll("[^a-zA-Z0-9]","");
		String str = inputSong.getFullText().toLowerCase();
		str.replaceAll("[^a-zA-Z0-9]", " "); 
		
		List<String> words = Arrays.asList(str.split(" "));
		HashMap<String,PositionDetail> stringToPD = new HashMap<>();
		
		
		int i = 0;
		for(String temp:words) {
			if(temp.length()>=MINIMAL_WORD_LENGTH) {				
				if(stringToPD.containsKey(temp)) {
					Word existingWord = new Word(temp, stringToPD.get(temp));
					existingWord.getPositionDetail().setOccurences(existingWord.getPositionDetail().getOccurences()+1);
					existingWord.getPositionDetail().getPositions().add(i);
					stringToPD.put(temp, existingWord.getPositionDetail());
				} else { 
					ArrayList<Integer> newPos =  new ArrayList<Integer>(0);
					newPos.add(i);
					stringToPD.put(temp, new PositionDetail(1, newPos));
				}
			}
			i++;
		}
		
		return new DecodedSong(inputSong.getName(), inputSong.getAuthorName(), stringToPD);

	}
	
}
