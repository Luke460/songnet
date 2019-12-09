package songnet.model;

import java.util.HashMap;


public class DecodedSong {
	
	private String name;
	private String authorName;
	private HashMap<String,Integer> wordToNumber;
	
	public DecodedSong() {
		super();
	}

	public DecodedSong(String name, String authorName) {
		super();
		this.name = name;
		this.authorName = authorName;
		this.wordToNumber = new HashMap<String,Integer>();
	}
	
	public DecodedSong(String name, String authorName, HashMap<String,Integer> words) {
		super();
		this.name = name;
		this.authorName = authorName;
		this.wordToNumber = words;
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

	public HashMap<String,Integer> getWords() {
		return wordToNumber;
	}

	public void setWords(HashMap<String,Integer> words) {
		this.wordToNumber = words;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((wordToNumber == null) ? 0 : wordToNumber.hashCode());
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
		if (wordToNumber == null) {
			if (other.wordToNumber != null)
				return false;
		} else if (!wordToNumber.equals(other.wordToNumber))
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
		builder.append(wordToNumber);
		builder.append("]");
		return builder.toString();
	}
}