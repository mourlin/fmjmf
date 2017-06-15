package contact.main;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class DatabaseResource extends ServerResource {

    @Get
    public String represent() {
    	System.out.println("HelloWorld");
        return "<html><head><title>gae-contact-database</title></head><body>url should be: https://gae-contact-database.appspot.com/&lt;action&gt;</body></html>";
    }

}