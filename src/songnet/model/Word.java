package songnet.model;

public class Word implements Comparable<Word>{

	private String text;
	private PositionDetail positionDetail;
	
	public Word() {
		super();
	}

	public Word(String text, PositionDetail positionDetail) {
		super();
		this.text = text;
		this.positionDetail = positionDetail;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PositionDetail getPositionDetail() {
		return positionDetail;
	}

	public void setPositionDetail(PositionDetail positionDetail) {
		this.positionDetail = positionDetail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((positionDetail == null) ? 0 : positionDetail.hashCode());
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
		if (positionDetail == null) {
			if (other.positionDetail != null)
				return false;
		} else if (!positionDetail.equals(other.positionDetail))
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
		builder.append(", positionDetail=");
		builder.append(positionDetail);
		builder.append("]");
		return builder.toString();
	}
	

	@Override
	public int compareTo(Word arg0) {
		return this.getPositionDetail().getOccurences()-arg0.getPositionDetail().getOccurences();
	}
	
}
