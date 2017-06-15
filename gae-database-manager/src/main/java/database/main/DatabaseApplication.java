package database.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import database.manager.DatabaseChangeService;
import database.manager.DatabaseManagerService;

public class DatabaseApplication extends Application {
	public static final int TIMEOUT = 5 * 1000;
	public static final String LOCAL = "local";

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		// Defines only one route
		router.attachDefault(DatabaseResource.class);

		router.attach("/database/manager", DatabaseManagerService.class);
		router.attach("/database/manager/{regId}", DatabaseManagerService.class);
		router.attach("/database/contact/change", DatabaseChangeService.class);
		// lecture des propriétés
		final Properties props = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			if (inStream != null) {
				props.load(inStream);
			} else { // add all properties from the appengine-web.xml
				props.put("url.for.backup.0", System.getProperty("url.for.backup.0"));
				props.put("url.for.backup.1", System.getProperty("url.for.backup.1"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// creation d'un thread pour la mise à jour de l'url de backup
// 30 avril 2016
//		final AtomicReference<String> backupURL = new AtomicReference<String>(LOCAL);
//
//		Thread thread = ThreadManager.currentRequestThreadFactory().newThread(new Runnable() {
//			public void run() {
//				long i = System.currentTimeMillis() % 2;
//
//				Map<String, Object> attrs	= new HashMap<>();
//				try {
//					while (true) {
//						backupURL.set(props.getProperty("url.for.backup."+(i%2)));
//						i = i+1;
//						attrs.put("url.for.backup", backupURL);
//						getContext().setAttributes(attrs);
//						Thread.sleep(100);
//					}
//				} catch (InterruptedException ex) {
//					throw new RuntimeException("Interrupted in loop:", ex);
//				}
//			}
//		});
//		thread.start();

		return router;
	}
}