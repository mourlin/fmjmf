package contact.main;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import contact.network.Contact;

/** Code client du projet ExampleDB */
public class Client {
	private static final String RESTLET_URL_SERVICE = "http://localhost:8182/restlet/contact";
	final static Logger logger = Logger.getLogger(Client.class);
	public static void main(final String[] args) throws Exception {
		//create();
		read();
		//update();
		//delete();
		//search();
	}
	private static void search() throws IOException, JSONException {
		// SEARCH_ALL
		ClientResource cr2 = new ClientResource(RESTLET_URL_SERVICE);
		cr2.accept(MediaType.APPLICATION_JSON);
		Representation representation2 = cr2.get();
		JsonRepresentation expected = new JsonRepresentation(representation2);
		logger.info("search all "+expected.getJsonArray());
	}
	private static void delete() throws IOException, JSONException {
		// DELETE
		Long identifier	= new Long(13);
		ClientResource cr = new ClientResource(RESTLET_URL_SERVICE+"/"+identifier);
		cr.accept(MediaType.APPLICATION_JSON);
		Representation representation2 = cr.delete();
		JsonRepresentation deleted = new JsonRepresentation(representation2);
		logger.info(deleted.getJsonObject());
	}
	private static void update() {
		// UPDATE
		ClientResource cr3 = new ClientResource(RESTLET_URL_SERVICE);
		Contact modified = new Contact("DeChevre", "Tom", "tom.dechevre@paris.fr");
		modified.setId(19L);
		JacksonRepresentation<Contact> jackModified	= new JacksonRepresentation<Contact>(modified);
		cr3.accept(MediaType.APPLICATION_JSON);
		Representation representation3 = cr3.put(jackModified, MediaType.APPLICATION_JSON);
		logger.info(representation3.toString());
	}
	private static void read() throws IOException, JSONException {
		// READ
		Long identifier	= new Long(10);
		ClientResource cr2 = new ClientResource(RESTLET_URL_SERVICE+"/"+identifier);
		cr2.accept(MediaType.APPLICATION_JSON);
		Representation representation2 = cr2.get();
		JsonRepresentation expected = new JsonRepresentation(representation2);
		logger.info(expected.getJsonObject());
	}
	private static void create() {
		// CREATE
		final ClientResource cr1 = new ClientResource(RESTLET_URL_SERVICE);
		Contact personne = new Contact("DeChevre", "Tom", "tom.dechevre@paris.fr");
		JacksonRepresentation<Contact> jackPersonne	= new JacksonRepresentation<Contact>(personne);
		cr1.accept(MediaType.APPLICATION_JSON);
		Representation representation = cr1.post(jackPersonne, MediaType.APPLICATION_JSON);
		logger.info(representation.toString());
	}

}
