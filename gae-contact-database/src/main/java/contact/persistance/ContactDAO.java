package contact.persistance;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class ContactDAO implements IFacade<Contact> {
	final static Logger logger = Logger.getLogger(ContactDAO.class.getName());

	static {
		ObjectifyService.register(Contact.class);
	}

	private Objectify ofy;
	
	public ContactDAO() {
		ofy = ObjectifyService.ofy();
	}

	@Override
	public boolean create(Contact p) {
		ofy.save().entity(p).now();
		return true;
	}

	@Override
	public Contact read(long id) {
		Contact fetched = ofy.load().type(Contact.class).filter("id", id).first().now();
		return fetched;
	}

	@Override
	public int update(Contact p) {
		Key<Contact> key	= ofy.save().entity(p).now();
		return (int) key.getId();
	}

	@Override
	public boolean delete(long id) {
		Contact deleted	= read(id);
		if (deleted != null) {
			ofy.delete().entity(deleted).now();
			return true;
		}
		else return false;
	}

	@Override
	public List<Contact> searchAll() {
		List result = ofy.load().type(Contact.class).list();
		return (List<Contact>) result;
	}

}
