package contact.manager;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Contact {
	@XmlAttribute
	private String lastname;
	@XmlAttribute
	private String firstname;
	@XmlAttribute
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
