package contact.main;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import contact.manager.ContactManagerService;

public class DatabaseApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(DatabaseResource.class);
        
		router.attach("/contact/manager", ContactManagerService.class);
		router.attach("/contact/manager/{contactId}", ContactManagerService.class);

        return router;
    }
}