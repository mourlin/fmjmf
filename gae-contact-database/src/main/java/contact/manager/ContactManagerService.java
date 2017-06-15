package contact.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import contact.persistance.ContactDAO;
import contact.persistance.IFacade;


public class ContactManagerService extends ServerResource implements IFacade<Contact>, ContactManagerResource {
	final static Logger logger = Logger.getLogger(ContactManagerService.class.getName());

	/** First method in the lifecycle of a ServerResource */
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Override
	@Post("application/json")
	public Representation create(Representation rep) throws Exception {
		JacksonRepresentation<Contact> contactJSON	= new JacksonRepresentation<Contact>(rep, Contact.class);
		Contact contact								= contactJSON.getObject();
		logger.info("ContactManagerService create("+rep.toString()+")");
		// 1- create a new record into the table RegId
		boolean resultat							= create(contact);
		logger.info("create "+contact);
		JacksonRepresentation<Boolean> result= new JacksonRepresentation<Boolean>(MediaType.APPLICATION_JSON, resultat);
		return result;
	}

	@Override
	@Get("application/json")
	public Representation read(Representation rep) throws Exception {
		String contactId	= super.getAttribute("contactId");
		if (contactId != null) {
			JacksonRepresentation<Long> idRegId		= new JacksonRepresentation<Long>(rep, Long.class);
			Long id									= idRegId.getObject();
			Contact resultat						= read(id);
			logger.info("read = "+id);
			JacksonRepresentation<Contact> result= new JacksonRepresentation<Contact>(MediaType.APPLICATION_JSON, resultat);
			return result;
		} else {
			JacksonRepresentation<Contact> result		=  (JacksonRepresentation<Contact>) search();
			logger.info("read "+result.getText());
			return result;
		}
	}

	@Override
	@Put("application/json")
	public Representation update(Representation rep) throws Exception {
		JacksonRepresentation<Contact> jackContact	= new JacksonRepresentation<Contact>(rep, Contact.class);
		Contact contact								= jackContact.getObject();
		int number									= update(contact);
		logger.info("update "+contact);
		JacksonRepresentation<Integer> result= new JacksonRepresentation<Integer>(MediaType.APPLICATION_JSON, number);
		return result;
	}

	@Override
	@Delete("application/json")
	public Representation delete(Representation rep) throws Exception {
		JacksonRepresentation<Long> idContact	= new JacksonRepresentation<Long>(rep, Long.class);
		Long id									= idContact.getObject();
		boolean resultat						= delete(id);
		logger.info("delete "+id);
		JacksonRepresentation<Boolean> result= new JacksonRepresentation<Boolean>(MediaType.APPLICATION_JSON, resultat);
		return result;
	}

	@Override
	@Get("application/json")
	public Representation search() {
		List<Contact> contacts			= searchAll();
		logger.info("search "+contacts.size() + " regId(s)");
		return new JacksonRepresentation<List<Contact>>(MediaType.APPLICATION_JSON, contacts);
	}

	@Override
	public boolean create(Contact c) {
		ContactDAO dao								= new ContactDAO();
		contact.persistance.Contact persist			= new contact.persistance.Contact(c.getLastname(), c.getFirstname(), c.getEmail());
		return dao.create(persist);
	}

	@Override
	public Contact read(long id) {
		ContactDAO dao						= new ContactDAO();
		contact.persistance.Contact p		= dao.read(id);
		Contact resultat					= null;
		if (p != null)
			resultat						= new Contact(p.getLastname(), p.getFirstname(), p.getEmail());
		return resultat;
	}

	@Override
	public int update(Contact p) {
		ContactDAO dao						= new ContactDAO();
		contact.persistance.Contact persist	= new contact.persistance.Contact(p.getLastname(), p.getFirstname(), p.getEmail());
		int number							= dao.update(persist);
		return number;
	}

	@Override
	public boolean delete(long id) {
		ContactDAO dao					= new ContactDAO();
		boolean resultat				= dao.delete(id);
		return resultat;
	}

	@Override
	public List<Contact> searchAll() {
		ContactDAO dao							= new ContactDAO();
		List<contact.persistance.Contact> persists	= dao.searchAll();
		List<Contact> contacts						= new ArrayList<Contact>(persists.size());
		for (contact.persistance.Contact r: persists) {
			Contact contact							= new Contact(r.getLastname(), r.getFirstname(), r.getEmail());
			contacts.add(contact);
		}
		return contacts;	}
	
}
