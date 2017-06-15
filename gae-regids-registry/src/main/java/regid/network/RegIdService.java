package regid.network;

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

import regid.persistance.IFacade;
import regid.persistance.RegIdDAO;

/** REST service : it exposes the datasource on the http protocol.
 *  A CRUDS implementation of the t_RegId table is provided by RegIdDAO class. 
 */
public class RegIdService extends ServerResource implements IFacade<RegId>, RegIdResource {
	final static Logger logger = Logger.getLogger(RegIdService.class.getName());

	/** First method in the lifecycle of a ServerResource */
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}
	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#create(org.restlet.representation.Representation)
	 */
	@Override
	@Post("application/json")
	public Representation create(Representation rep) throws Exception {
		JacksonRepresentation<RegId> regIdJSON	= new JacksonRepresentation<RegId>(rep, RegId.class);
		RegId regId								= regIdJSON.getObject();
		logger.info("RegIdService create("+rep.toString()+")");
		// 1- create a new record into the table RegId
		boolean resultat						= create(regId);
		logger.info("create "+regId);
		// 2- TODO Update JEE Application -> call DatabaseManager
		DatabaseManagerInfo info				= new DatabaseManagerInfo(regId);
		info.call();
		JacksonRepresentation<Boolean> result= new JacksonRepresentation<Boolean>(MediaType.APPLICATION_JSON, resultat);
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#read(org.restlet.representation.Representation)
	 */
	@Override
	@Get("application/json")
	public Representation read(Representation rep) throws Exception {
		String regIdId	= super.getAttribute("regId");
		if (regIdId != null) {
			JacksonRepresentation<Long> idRegId		= new JacksonRepresentation<Long>(rep, Long.class);
			Long id									= idRegId.getObject();
			RegId resultat							= read(id);
			logger.info("read = "+id);
			JacksonRepresentation<RegId> result= new JacksonRepresentation<RegId>(MediaType.APPLICATION_JSON, resultat);
			return result;
		} else {
			JacksonRepresentation<RegId> result		=  (JacksonRepresentation<RegId>) search();
			logger.info("read "+result.getText());
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#update(org.restlet.representation.Representation)
	 */
	@Override
	@Put("application/json")
	public Representation update(Representation rep) throws Exception {
		JacksonRepresentation<RegId> jackRegId	= new JacksonRepresentation<RegId>(rep, RegId.class);
		RegId regId								= jackRegId.getObject();
		int number								= update(regId);
		logger.info("update "+regId);
		JacksonRepresentation<Integer> result= new JacksonRepresentation<Integer>(MediaType.APPLICATION_JSON, number);
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#delete(org.restlet.representation.Representation)
	 */
	@Override
	@Delete("application/json")
	public Representation delete(Representation rep) throws Exception {
		JacksonRepresentation<Long> idRegId		= new JacksonRepresentation<Long>(rep, Long.class);
		Long id									= idRegId.getObject();
		boolean resultat						= delete(id);
		logger.info("delete "+id);
		JacksonRepresentation<Boolean> result= new JacksonRepresentation<Boolean>(MediaType.APPLICATION_JSON, resultat);
		return result;
	}

	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#search()
	 */
	@Override
	@Get("/all")
	public Representation search() {
		List<RegId> regIds			= searchAll();
		logger.info("search "+regIds.size() + " regId(s)");
		return new JacksonRepresentation<List<RegId>>(MediaType.APPLICATION_JSON, regIds);
	}
	
	@Override
	public boolean create(RegId r) {
		RegIdDAO dao								= new RegIdDAO();
		regid.persistance.RegId persist				= new regid.persistance.RegId(r.getRegId(), r.getDate());
		return dao.create(persist);
	}
	@Override
	public RegId read(long id) {
		RegIdDAO dao						= new RegIdDAO();
		regid.persistance.RegId p			= dao.read(id);
		RegId resultat						= null;
		if (p != null)
			resultat						= new RegId(p.getRegId(), p.getDate());
		return resultat;
	}
	@Override
	public int update(RegId r) {
		RegIdDAO dao						= new RegIdDAO();
		regid.persistance.RegId persist		= new regid.persistance.RegId(r.getRegId(), r.getDate());
		int number							= dao.update(persist);
		return number;
	}
	@Override
	public boolean delete(long id) {
		RegIdDAO dao					= new RegIdDAO();
		boolean resultat				= dao.delete(id);
		return resultat;
	}
	@Override
	public List<regid.network.RegId> searchAll() {
		RegIdDAO dao							= new RegIdDAO();
		List<regid.persistance.RegId> persists	= dao.searchAll();
		List<regid.network.RegId> regIds		= new ArrayList<regid.network.RegId>(persists.size());
		for (regid.persistance.RegId r: persists) {
			regid.network.RegId regId			= new regid.network.RegId(r.getRegId(), r.getDate());
			regIds.add(regId);
		}
		return regIds;
	}
}
