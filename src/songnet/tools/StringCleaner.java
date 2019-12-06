package songnet.tools;

public class StringCleaner {

	public static String clean(String dirtyString) {
		dirtyString = dirtyString.replaceAll("[^a-zA-Z0-9]", " ");
		return dirtyString.replaceAll(" +", " ");
	}
	
}
