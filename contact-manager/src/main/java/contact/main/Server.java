package contact.main;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import contact.network.ContactService;

public class Server extends Application {
	final static Logger logger = Logger.getLogger(Server.class);

	public static void main(String[] args) throws Exception {
		Properties props	= new Properties();
		props.load(Server.class.getResourceAsStream("../../jndi.properties"));
		logger.info("providedServiceURL = "+props.getProperty("providedServiceURL"));
		// Create a new Component.
		final Component component = new Component();
		logger.info("Main server de Personne");
		// Add a new HTTP server listening on port 8182.
		component.getServers().add(Protocol.HTTP, 8182);
		 
		final Router router = new Router(component.getContext().createChildContext());
		router.attach("/contact", ContactService.class);
		router.attach("/contact/{contactId}", ContactService.class);
		 
		// Attach the sample application.
		component.getDefaultHost().attach("/restlet", router);
		 
		// Start the component.
		component.start();
	}

}
