package regid.main;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
    	System.out.println("HelloWorld");
        return "<html><head><title>gae-regids-registry</title></head><body>url should be: https://gae-regids-registry.appspot.com/&lt;action&gt;</body></html>";
    }

}