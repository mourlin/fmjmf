package regid.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/** Task which sends a Restlet request to DatabaseManager */
public class DatabaseManagerInfo {
	final static Logger logger = Logger.getLogger(DatabaseManagerInfo.class.getName());
	private static Properties props;
	private RegId regId;

	static {
		props					= new Properties();
		InputStream inStream	= Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			if (inStream != null)
				props.load(inStream);	//FIXME null pointer exception
			else
				props.put("url.to.database.manager", System.getProperty("url.to.database.manager"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DatabaseManagerInfo(RegId ri) {
		this.regId		= ri;
	}
	
	public RegId call() {
		ClientResource cr 	= new ClientResource(props.getProperty("url.to.database.manager"));
		System.out.println("url.to.database.manager = "+props.getProperty("url.to.database.manager"));
		cr.setMethod(Method.POST);
		cr.setRequestEntityBuffering(true);
		logger.info("call to DatabaseManager");
		cr.accept(MediaType.APPLICATION_JSON);
		JacksonRepresentation<RegId> rep 		= new JacksonRepresentation<RegId>(regId);
		Representation answer 					= cr.post(rep, MediaType.APPLICATION_JSON);
		JacksonRepresentation<RegId> regIdJSON	= new JacksonRepresentation<RegId>(answer, RegId.class);
		try {
			RegId regId							= regIdJSON.getObject();
			logger.info("answer == " + regId.getRegId());
			return regId;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
