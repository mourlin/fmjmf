package regid.persistance;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class RegIdDAO implements IFacade<RegId> {
	final static Logger logger = Logger.getLogger(RegIdDAO.class.getName());

	static {
		ObjectifyService.register(RegId.class);
	}

	private Objectify ofy;

	public RegIdDAO() {
		ofy = ObjectifyService.ofy();
	}
	@Override
	public boolean create(RegId ri) {
		ofy.save().entity(ri).now();
		return true;
	}

	@Override
	public RegId read(long id) {
		RegId fetched = ofy.load().type(RegId.class).filter("id", id).first().now();
		return fetched;
	}

	@Override
	public int update(RegId p) {
		Key<RegId> key	= ofy.save().entity(p).now();
		return (int) key.getId();
	}

	@Override
	public boolean delete(long id) {
		RegId deleted	= read(id);
		if (deleted != null) {
			ofy.delete().entity(deleted).now();
			return true;
		}
		else return false;
	}

	@Override
	public List<RegId> searchAll() {
		List result = ofy.load().type(RegId.class).list();
		return (List<RegId>) result;
	}
}
