package contact.persistance;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Contact {
	@Id
	private Long id;
	@Index
	private String lastname;
	@Index
	private String firstname;
	@Index
	private String email;
	public Contact() {
		lastname = "";
		firstname = "";
		email = "";
	}
	public Contact(String lastname, String firstname, String email) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
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
