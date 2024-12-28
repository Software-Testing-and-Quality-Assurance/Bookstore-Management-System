package model;
import java.io.Serial;
import java.io.Serializable;


public class Author implements Serializable{

	@Serial
	private static final long serialVersionUID = 6034064482866087452L;
	private final String authorName;
	private final String authorSurname;
	
	public Author(String authorName, String authorSurname) {
		this.authorName=authorName;
		this.authorSurname=authorSurname;
	}
	@Override
	public String toString() {
		return authorName+" "+authorSurname;
	}


}
