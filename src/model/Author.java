package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

public class Author implements Serializable{
	
	private String authorName;
	private String authorSurname;
	
	public Author(String authorName, String authorSurname) {
		this.authorName=authorName;
		this.authorSurname=authorSurname;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorSurname() {
		return authorSurname;
	}

	public void setAuthorSurname(String authorSurname) {
		this.authorSurname = authorSurname;
	}
	
	
	@Override
	public String toString() {
		return authorName+" "+authorSurname;
	}


}
