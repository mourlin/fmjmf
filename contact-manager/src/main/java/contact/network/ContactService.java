package contact.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import contact.persistence.ContactDAO;
import contact.persistence.IFacade;

public class ContactService extends ServerResource implements IFacade<Contact> {
	final static Logger logger = Logger.getLogger(ContactService.class);

	/** First method in the life cycle of a ServerResource */
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Post("application/json")
	public Representation create(Representation rep) throws Exception {
		JacksonRepresentation<Contact> jackPersonne = new JacksonRepresentation<Contact>(
				rep, Contact.class);
		Contact personne = jackPersonne.getObject();
		boolean resultat = create(personne);
		JSONObject jsonResultat = new JSONObject(new Boolean(resultat));
		logger.info("create " + personne);
		return new JsonRepresentation(jsonResultat);
	}

	@Get("application/json")
	public Representation read(Representation rep) throws Exception {
		String contactId	= super.getAttribute("contactId");
		if (contactId != null) {
			logger.info("read = " + contactId);
			Long id = new Long(contactId);
			Contact resultat = read(id);
			JSONObject jsonResultat = new JSONObject(resultat);
			return new JsonRepresentation(jsonResultat);
		} else {
			JsonRepresentation result	=  (JsonRepresentation) search();
			logger.info("read "+result.getJsonArray().length());
			return result;
		}
	}

	@Put("application/json")
	public Representation update(Representation rep) throws Exception {
		JacksonRepresentation<Contact> jackPersonne = new JacksonRepresentation<Contact>(
				rep, Contact.class);
		Contact personne = jackPersonne.getObject();
		logger.info(personne);
		int number = update(personne);
		JSONObject jsonResultat = new JSONObject(new Integer(number));
		System.out.println("update " + personne);
		return new JsonRepresentation(jsonResultat);
	}

	/** FIXME a bug about the parameter transfer */
	@Delete("application/json")
	public Representation delete(Representation rep) throws Exception {
		Long id = new Long(super.getAttribute("contactId"));
		boolean resultat = delete(id);
		JSONObject jsonResultat = new JSONObject(new Boolean(resultat));
		logger.info("delete " + id);
		return new JsonRepresentation(jsonResultat);
	}

	/** FIXME a bug about the parameter transfer */
	@Get("application/json")
	public Representation search() {
		List<Contact> personnes = searchAll();
		logger.info("search " + personnes.size() + " personne(s)");
		JSONArray jsonResultat = new JSONArray(personnes);
		logger.info("search " + jsonResultat.length() + " json(s)");
		JsonRepresentation jp = new JsonRepresentation(jsonResultat);
		try {
			logger.info("search " + jp.getJsonArray().length());
		} catch (JSONException e) {
			logger.error(e);
		}
		return jp;
	}

	@Override
	public boolean create(Contact p) {
		ContactDAO dao = new ContactDAO();
		contact.persistence.Contact persist = new contact.persistence.Contact(
				p.getNom(), p.getPrenom(), p.getEmail());
		return dao.create(persist);
	}

	@Override
	public Contact read(long id) {
		ContactDAO dao = new ContactDAO();
		contact.persistence.Contact p = dao.read(id);
		Contact resultat = null;
		if (p != null)
			resultat = new Contact(p.getNom(), p.getPrenom(), p.getEmail());
		return resultat;
	}

	@Override
	public int update(Contact p) {
		ContactDAO dao = new ContactDAO();
		contact.persistence.Contact persist = new contact.persistence.Contact(
				p.getNom(), p.getPrenom(), p.getEmail());
		if (p.getId() != null)
			persist.setId(p.getId());
		int number = dao.update(persist);
		return number;
	}

	@Override
	public boolean delete(long id) {
		ContactDAO dao = new ContactDAO();
		boolean resultat = dao.delete(id);
		return resultat;
	}

	@Override
	public List<Contact> searchAll() {
		ContactDAO dao = new ContactDAO();
		List<contact.persistence.Contact> persists = dao.searchAll();
		List<Contact> personnes = new ArrayList<Contact>(persists.size());
		for (contact.persistence.Contact p : persists) {
			Contact personne = new Contact(p.getNom(), p.getPrenom(), p.getEmail());
			personnes.add(personne);
		}
		return personnes;
	}
}
