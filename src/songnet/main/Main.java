package songnet.main;

import java.io.IOException;
import java.util.ArrayList;

import songnet.model.DecodedSong;
import songnet.process.MusicReader;
import songnet.process.SongComparator;

public class Main {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Updating dataset...");
		ArrayList<DecodedSong> dataset = MusicReader.updateDataset();
		System.out.println("Dataset updated.");
		
		System.out.println("Searching...");
		SongComparator.executeComparisonWithDataset(dataset);
		System.out.println("Done.");
		

	}

}
