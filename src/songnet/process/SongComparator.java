package songnet.process;

import static songnet.constants.Constants.FINAL_SCORE_THRESHOLD;
import static songnet.constants.Constants.MAX_OUTPUT_SONGS_NUMBER;
import static songnet.constants.Constants.RECEIVED_TEXT_PATH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import songnet.model.DecodedSong;
import songnet.model.Song;
import songnet.tools.ScoreGenerator;
import songnet.tools.StringCleaner;
import songnet.tools.TextMapper;

public class SongComparator {
	
	public static void executeComparisonWithDataset(List<DecodedSong> dataset) throws IOException {

		String receivedText = new String(Files.readAllBytes(Paths.get(RECEIVED_TEXT_PATH))).toLowerCase();
		receivedText = StringCleaner.clean(receivedText);
		Song unknownSong = new Song("unknown", "unknown", receivedText);
		DecodedSong decodedUnknownSong= TextMapper.generateMap(unknownSong);
		TreeMap<Double,DecodedSong> results = new TreeMap<>(Comparator.reverseOrder());

		for(DecodedSong song:dataset) {
			Double score = ScoreGenerator.generateScore(decodedUnknownSong.getWords(), song.getWords());
			if (score>0.0) results.put(score, song);
		}

		if(results.size()==0) {
			System.out.println("----------------------------------------");
			System.out.println("No resuts...  :(");
		}

		int count = 0;
		ArrayList<Double> values = new ArrayList<>(results.size());

		for(Entry<Double, DecodedSong> entry: results.entrySet()) {
			DecodedSong resultSong = entry.getValue();
			Double score = entry.getKey();
			if(score > 0 && count<MAX_OUTPUT_SONGS_NUMBER) {
				values.add(count, score);
				System.out.println("----------------------------------------");
				if(count==0) System.out.println("########################################");
				System.out.println("TITLE: " + resultSong.getName());
				System.out.println("AUTHOR: " + resultSong.getAuthorName());
				System.out.println("SCORE: " + score);
				if(count==0) System.out.println("########################################");
				count ++;
			}
		}
		Double total = values.stream()
				.collect(Collectors.summingDouble(Double::doubleValue));

		System.out.println("----------------------------------------");
		if(values.size()!=0) {
			int setScore = (int) ((values.get(0)*100.0)/total);
			double scoreAdjustment = values.get(0)<(FINAL_SCORE_THRESHOLD*(1/values.size()))?(values.get(0)/(FINAL_SCORE_THRESHOLD*(1/values.size()))):1d;
			System.out.println("Reliability score: " + (int) (setScore*scoreAdjustment) + "%");
		}

	}

}
