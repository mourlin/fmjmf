package database.main;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class DatabaseResource extends ServerResource {

    @Get
    public String represent() {
    	System.out.println("HelloWorld");
        return "<html><head><title>gae-database-manager</title></head><body>url should be: https://gae-database-manager.appspot.com/&lt;action&gt;</body></html>";
    }

}