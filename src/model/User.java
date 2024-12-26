package model;

import java.io.Serializable;

public abstract class User implements Serializable{
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;

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
        this.phone=phone;

    }
    
    @Override
    public boolean equals(Object o) {
    	
   	 if (((User)o).getUsername().equals(this.getUsername())&&((User)o).getPassword().equals(this.getPassword()))
   		 return true;
   	 return false;
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

    public void setPassword(String password) {
        this.password=password;
    }
   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname=surname;;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
