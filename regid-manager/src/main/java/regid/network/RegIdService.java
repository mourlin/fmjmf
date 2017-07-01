package regid.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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

import regid.persistance.IFacade;
import regid.persistance.RegIdDAO;

/** REST service : it exposes the datasource on the http protocol.
 *  A CRUDS implementation of the t_RegId table is provided by RegIdDAO class. 
 */
public class RegIdService extends ServerResource implements IFacade<RegId>, RegIdResource {
	final static Logger logger = Logger.getLogger(RegIdService.class);

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
		JacksonRepresentation<RegId> jackPersonne	= new JacksonRepresentation<RegId>(rep, RegId.class);
		RegId regId									= jackPersonne.getObject();
		logger.info("RegIdService create("+rep.toString()+")");
		// 1- create a new record into the table RegId
		boolean resultat							= create(regId);
		JSONObject jsonResultat						= new JSONObject(new Boolean(resultat));
		logger.info("create "+regId);
// FIXMe Bug a service Manager is missing 2- Update JEE Application -> call PersonDatabaseManager
//		DatabaseManagerRequest request		= new DatabaseManagerRequest(regId);
//		Thread thread						= new Thread(request);
//		thread.start();
//		thread.join();
		return new JsonRepresentation(jsonResultat);
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
			JSONObject jsonResultat					= new JSONObject(resultat);
			logger.info("read = "+id);
			return new JsonRepresentation(jsonResultat);
		} else {
			JsonRepresentation result	=  (JsonRepresentation) search();
			logger.info("read "+result.getJsonArray().length());
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
		JSONObject jsonResultat					= new JSONObject(new Integer(number));
		logger.info("update "+regId);
		return new JsonRepresentation(jsonResultat);
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
		JSONObject jsonResultat					= new JSONObject(new Boolean(resultat));
		logger.info("delete "+id);
		return new JsonRepresentation(jsonResultat);
	}

	/* (non-Javadoc)
	 * @see fr.paris.regid.network.RegIdResource#search()
	 */
	@Override
	@Get("json")
	public Representation search() {
		List<RegId> regIds			= searchAll();
		JSONArray jsonResultat		= new JSONArray(regIds);
		logger.info("search "+regIds.size() + " regId(s)");
		return new JsonRepresentation(jsonResultat);
	}
	
	@Override
	public boolean create(RegId r) {
		RegIdDAO dao								= new RegIdDAO();
		regid.persistance.RegId persist				= new regid.persistance.RegId(r.getRegId());
		return dao.create(persist);
	}
	@Override
	public RegId read(long id) {
		RegIdDAO dao						= new RegIdDAO();
		regid.persistance.RegId p			= dao.read(id);
		RegId resultat						= null;
		if (p != null)
			resultat						= new RegId(p.getRegId());
		return resultat;
	}
	@Override
	public int update(RegId r) {
		RegIdDAO dao						= new RegIdDAO();
		regid.persistance.RegId persist		= new regid.persistance.RegId(r.getRegId());
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
	public List<RegId> searchAll() {
		RegIdDAO dao							= new RegIdDAO();
		List<regid.persistance.RegId> persists	= dao.searchAll();
		List<RegId> regIds						= new ArrayList<RegId>(persists.size());
		for (regid.persistance.RegId r: persists) {
			RegId regId							= new RegId(r.getRegId());
			regIds.add(regId);
		}
		return regIds;
	}
}
