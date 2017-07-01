package regid.main;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import regid.network.RegIdService;

public class Server extends Application {
	public static final String TAG	= "fr.fmjmf";
	final static Logger logger = Logger.getLogger(Server.class);
//	private static final String restURL	= "http://192.168.0.38:7172/restlet/manager";
	
	public static void main(String[] args) throws Exception {
		Properties props	= new Properties();
		props.load(Server.class.getResourceAsStream("../../jndi.properties"));
		logger.info("providedServiceURL = "+props.getProperty("providedServiceURL"));
		// Create a new Component.
		final Component component = new Component();
		logger.info("Main server Annuaire de RegID");
		// Add a new HTTP server listening on port 9192.
		component.getServers().add(Protocol.HTTP, 9192);
		 
		final Router router = new Router(component.getContext().createChildContext());
		router.attach("/regId", RegIdService.class);
		router.attach("/regId/{regId}", RegIdService.class);
		// Attach the sample application.
		component.getDefaultHost().attach("/restlet", router);
		 
		// Start the component.
		component.start();
	}

}
