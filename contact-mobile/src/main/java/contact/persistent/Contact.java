package contact.persistent;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact")
public class Contact implements Serializable {
	private static final long serialVersionUID = -222864131214757024L;
	@DatabaseField(generatedId = true)
	private int contact_id;
	@DatabaseField(columnName = "lastname", canBeNull=false)
	private String lastname;
	@DatabaseField(columnName = "firstname", canBeNull=false)
	private String firstname;
	@DatabaseField(columnName = "email", canBeNull=false)
	private String email;
	@DatabaseField(columnName = "creation", canBeNull=false)
	private Date creation;
	
	public Contact() {
		// used by ORMLite
	}

    public Contact(String firstname, String lastname, String email, java.util.Date date) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.creation = date;
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

	public int getContact_id() {
		return contact_id;
	}

	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Override
	public String toString() {
		return "Contact [contact_id=" + contact_id + ", lastname=" + lastname + ", firstname=" + firstname + ", email="
				+ email + ", creation=" + creation + "]";
	}

}
