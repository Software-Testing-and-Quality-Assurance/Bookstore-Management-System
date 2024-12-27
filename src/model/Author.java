package model;
import java.io.Serializable;


public class Author implements Serializable{
	
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
