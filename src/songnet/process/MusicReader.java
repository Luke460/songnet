package songnet.process;

import static songnet.constants.Constants.MAPPED_SONGS_PATH;
import static songnet.constants.Constants.SONG_LYRICS_PATH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import songnet.model.DecodedSong;
import songnet.model.Song;
import songnet.tools.StringCleaner;
import songnet.tools.TextMapper;

public class MusicReader {

	public static ArrayList<DecodedSong> updateDataset() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String storedSongsFileContent = new String(Files.readAllBytes(Paths.get(SONG_LYRICS_PATH)));

		String mappedSongsFileContent = new String(Files.readAllBytes(Paths.get(MAPPED_SONGS_PATH)));

		JSONArray songs = new JSONArray(storedSongsFileContent);
		
		if(mappedSongsFileContent == null || mappedSongsFileContent.equals("")) {
			mappedSongsFileContent = "[]";
		}
		
		JSONArray mappedSongs = new JSONArray(mappedSongsFileContent);

		HashSet<String> storedSongNamesSet = new HashSet<>();

		ArrayList<DecodedSong> dataset = new ArrayList<>();


		// check stored songs
		for (int i = 0; i < mappedSongs.length(); i++) {
			String string = mappedSongs.getJSONObject(i).toString();
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

				JSONObject serializedSong = new JSONObject(mapper.writeValueAsString(decodedSong));
				mappedSongs.put(serializedSong);

				System.out.println(serializedSong);

				dataset.add(decodedSong);

			}

		}

		Files.write(Paths.get(MAPPED_SONGS_PATH), mappedSongs.toString().getBytes());

		System.out.println("Dataset size: " + dataset.size());

		return dataset;

	}

}
