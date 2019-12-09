package songnet.constants;

public interface Constants {

	public static final int MINIMAL_WORD_LENGTH = 3;
	public static final int TEXT_SAMPLES_NUMBER = -1;  // -1 = uncompressed
	public static final boolean FORCE_DATASET_UPDATE = false;
	
	public static final double WORD_NOT_PRESENT_PENALITY_MULTIPLIER = 5.0;
	public static final double WORD_SINGLE_PRESENCE_PENALITY = 0.5;
	public static final double FINAL_SCORE_THRESHOLD = 10;  
	
	public static final int MAX_OUTPUT_SONGS_NUMBER = 3;

	public static final String SONG_LYRICS_PATH = "songs/lyrics.json";
	public static final String MAPPED_SONGS_PATH = "songs/map.json";
	public static final String RECEIVED_TEXT_PATH = "quick-test/received-text.txt";
	
}
