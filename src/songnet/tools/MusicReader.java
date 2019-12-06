package songnet.tools;

import static songnet.constants.Constants.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import songnet.model.DecodedSong;
import songnet.model.Song;

public class MusicReader {

	public static ArrayList<DecodedSong> updateDataset() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String storedSongsFileContent = new String(Files.readAllBytes(Paths.get(SONG_LYRICS_PATH)));

		String mappedSongsFileContent = new String(Files.readAllBytes(Paths.get(MAPPED_SONGS_PATH)));

		JSONArray songs = new JSONArray(storedSongsFileContent);
		JSONArray mappedSongs = new JSONArray(mappedSongsFileContent);

		HashSet<String> storedSongNamesSet = new HashSet<>();

		ArrayList<DecodedSong> dataset = new ArrayList<>();


		// check stored songs
		for (int i = 0; i < mappedSongs.length(); i++) {
			String string = mappedSongs.getString(i);
			DecodedSong decodedSong = mapper.readValue(string, DecodedSong.class);

			String name = decodedSong.getName();
			storedSongNamesSet.add(name);
			dataset.add(decodedSong);

		}


		// check for updates
		for (int i = 0; i < songs.length(); i++) {
			JSONObject jsonSong = (JSONObject) songs.get(i);

			String name = (jsonSong.getString("name"));

			if(!storedSongNamesSet.contains(name)) {

				String authorName = (jsonSong.getString("authorName"));
				String fullText = StringCleaner.clean((jsonSong.getString("fullText")));

				Song song = new Song(name, authorName, fullText);

				DecodedSong decodedSong = TextMapper.generateMap(song);

				String serializedSong = mapper.writeValueAsString(decodedSong);
				mappedSongs.put(serializedSong);

				System.out.println(serializedSong);

				dataset.add(decodedSong);

			}

		}

		Files.write(Paths.get(MAPPED_SONGS_PATH), mappedSongs.toString().getBytes());

		System.out.println("Dataset size: " + dataset.size());

		return dataset;

	}

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
		double firstValue = 0.01;
		double secondValue = 0.01;

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
