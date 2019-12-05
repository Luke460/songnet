package songnet.model;

public class Word implements Comparable<Word>{

	private String text;
	private int occurences;
	
	public Word() {
		super();
	}
	
	public Word(String text, int occurences) {
		super();
		this.text = text;
		this.occurences = occurences;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + occurences;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Word other = (Word) obj;
		if (occurences != other.occurences)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Word [text=");
		builder.append(text);
		builder.append(", occurences=");
		builder.append(occurences);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Word arg0) {
		return this.occurences-arg0.occurences;
	}
	
}
