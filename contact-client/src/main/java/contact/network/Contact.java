package contact.network;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/** Classe Personne est une entity */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Contact {
	@XmlAttribute
	private Long id;
	@XmlAttribute
	private String nom;
	@XmlAttribute
	private String prenom;
	@XmlAttribute
	private String email;
	public Contact() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Contact(String nom, String prenom, String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", email=" + email + "]";
	}
	
}
