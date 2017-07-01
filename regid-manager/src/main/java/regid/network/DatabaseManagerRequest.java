package regid.network;

import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.resource.ClientResource;

public class DatabaseManagerRequest implements Runnable {
	private static final String restURL	= "http://192.168.0.38:7172/restlet/manager";
	final static Logger logger = Logger.getLogger(DatabaseManagerRequest.class);
	private RegId regId;
	
	public DatabaseManagerRequest(RegId regId) {
		super();
		this.regId = regId;
	}

	@Override
	public void run() {
		ClientResource cr 	= new ClientResource(restURL);
		logger.info("create ClientResource "+restURL);
		cr.setMethod(Method.POST);
		cr.setRequestEntityBuffering(true);
		logger.info("2");	
		cr.accept(MediaType.APPLICATION_JSON);
		logger.info("3");		
		JacksonRepresentation<RegId> regIdJSON = new JacksonRepresentation<RegId>(regId);
		logger.info("4");	
		cr.post(regIdJSON, MediaType.APPLICATION_JSON);
		logger.info("5");	
	}

}
