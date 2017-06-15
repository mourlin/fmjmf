package database.manager;

import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/** REST service : it transfers RegId to mobiel phone and provide a database URL */
public class DatabaseManagerService extends ServerResource implements DatabaseManagerResource {
	final static Logger logger = Logger.getLogger(DatabaseManagerService.class.getName());
	
	/** First method in the lifecycle of a ServerResource */
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Override
	@Post("application/json")
	public Representation create(Representation rep) throws Exception {
		JacksonRepresentation<RegId> jacksonRegId = new JacksonRepresentation<RegId>(rep, RegId.class);
		RegId regId = jacksonRegId.getObject();
		boolean resultat = create(regId);	// BUG ??
		// 1- provide an URL to mobile phone
		logger.info("DatabaseManagerService create(rep=" + rep.toString() + ")");
		JacksonRepresentation<Boolean> result= new JacksonRepresentation<Boolean>(MediaType.APPLICATION_JSON, resultat);
		return result;
	}
	
	@Override
	@Get("application/json")
	public Representation read(Representation rep) throws Exception {
		System.out.println("DatabaseManagerService");
		String regIdId	= super.getAttribute("regId");
		if (regIdId != null) {
			JacksonRepresentation<Long> idRegId		= new JacksonRepresentation<Long>(rep, Long.class);
			Long id									= idRegId.getObject();
			RegId resultat							= read(id);
			JacksonRepresentation<RegId> result= new JacksonRepresentation<RegId>(MediaType.APPLICATION_JSON, resultat);
			return result;
		} else {
			JacksonRepresentation<RegId> result		=  (JacksonRepresentation<RegId>) search();
			return result;
		}
	}
	@Override
	public Representation update(Representation rep) throws Exception {
		return null;
	}
	@Override
	public Representation delete(Representation rep) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public JacksonRepresentation<database.manager.RegId> search() {
		// TODO pas fini
		return null;
	}
	
	public boolean create(RegId r) {
		PublishUrlDatabase request	= new PublishUrlDatabase(r);
		request.notifyURLDatabase();
		return true;
	}

	public database.manager.RegId read(Long id) {
		// TODO pas fini
		return null;
	}
}
