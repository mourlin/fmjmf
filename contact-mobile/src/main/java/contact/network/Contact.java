package contact.network;

import java.io.Serializable;

public class Contact implements Serializable {
	private String lastname;
	private String firstname;
	private String email;
	public Contact() {
		// TODO Auto-generated constructor stub
	}
	public Contact(String firstname, String lastname, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	@Override
	public String toString() {
		return "Contact [lastname=" + lastname + ", firstname=" + firstname + ", email=" + email + "]";
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
