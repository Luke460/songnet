package songnet.model;

import java.util.HashMap;
import java.util.TreeMap;


public class DecodedSong {
	
	private String name;
	private String authorName;
	private HashMap<String,Word> words;
	
	public DecodedSong() {
		super();
	}

	public DecodedSong(String name, String authorName) {
		super();
		this.name = name;
		this.authorName = authorName;
		this.words = new HashMap<String, Word>();
	}
	
	public DecodedSong(String name, String authorName, HashMap<String, Word> words) {
		super();
		this.name = name;
		this.authorName = authorName;
		this.words = words;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public HashMap<String, Word> getWords() {
		return words;
	}

	public void setWords(HashMap<String, Word> words) {
		this.words = words;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DecodedSong other = (DecodedSong) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DecodedSong [name=");
		builder.append(name);
		builder.append(", authorName=");
		builder.append(authorName);
		builder.append(", words=");
		builder.append(words);
		builder.append("]");
		return builder.toString();
	}
}