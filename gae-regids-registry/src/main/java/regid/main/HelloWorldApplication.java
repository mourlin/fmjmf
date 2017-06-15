package regid.main;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import regid.network.RegIdService;

public class HelloWorldApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(HelloWorldResource.class);
        
		router.attach("/regId", RegIdService.class);
		router.attach("/regId/{regId}", RegIdService.class);

        return router;
    }
}