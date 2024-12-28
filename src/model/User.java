package model;

import java.io.Serial;
import java.io.Serializable;

public abstract class User implements Serializable{

	@Serial
	private static final long serialVersionUID = -3711616938156923396L;

	private String username;
	private final String password;
	private String name;
	private final String surname;
	private final String email;

    protected User(String username, String password, String name, String surname, String email, String phone ){
    	
    	if(username.isEmpty() || password.isEmpty())
			throw new IllegalArgumentException("Username and password should not be empty.");
    	
    	else if(password.length() < 8)
			throw new IllegalArgumentException("Password has to have 8 or more characters!");
    	
		else if(password.equals(username))
			throw new IllegalArgumentException("Password cannot equal the username.");
    	
		else if (!email.matches(".*@(gmail\\.com|hotmail\\.com|yahoo\\.com)"))
			throw new IllegalArgumentException("Enter valid email address.");
    	
		else if (!String.valueOf(phone).matches("3556[7-9]\\d{7}"))
		    throw new IllegalArgumentException("Enter valid phone number.");
    	this.username = username;
        this.password = password;
        this.name=name;
        this.surname=surname;
        this.email=email;

    }

    @Override
    public boolean equals(Object o) {
		if(this == o)return true;
		if (o == null || getClass() != o.getClass()) return false;
        return ((User) o).getUsername().equals(this.getUsername()) && ((User) o).getPassword().equals(this.getPassword());
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username=username;
    }
    public String getPassword() {
        return this.password;
        }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return email;
	}

}
