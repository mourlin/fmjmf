package database.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.restlet.Application;
import org.restlet.Context;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class PublishUrlDatabase {
	final static Logger logger = Logger.getLogger(PublishUrlDatabase.class.getName());
	private static Properties props;
	
	static {
		props					= new Properties();
		InputStream inStream	= Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			if (inStream != null) {
				props.load(inStream);
			} else {	// add all properties from the appengine-web.xml
				props.put("url.for.backup", System.getProperty("url.for.backup"));
				props.put("api.key", System.getProperty("api.key"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	private static final String projectNumber = "423649276134";
//	private static final String API_KEY = "AIzaSyDMO98kjWIAwbYA6Qjj1hgGLh17rG7aVdU";	// AIzaSyDMO98kjWIAwbYA6Qjj1hgGLh17rG7aVdU from Console App Engine pour le projet gcm-hub-exchange
//	private String backupURL	= "http://192.168.0.38:8182/restlet/personne";
	private RegId regId;
	
	public PublishUrlDatabase(RegId regId) {
		super();
		this.regId = regId;
	}

	@SuppressWarnings("unchecked")
	public void notifyURLDatabase() {
		logger.info("DatabaseManagerService create(r=" + regId.toString() + ")");
		final String API_KEY			= props.getProperty("api.key");
		final String backupURL			= props.getProperty("url.for.backup");
		
		Sender sender 					= new Sender(API_KEY);
		ArrayList<String> devicesList	= new ArrayList<String>();
		String newRegId = regId.getRegId();
		devicesList.add(newRegId);
		Message message = new Message.Builder().collapseKey("1")
				.timeToLive(3 * 60).delayWhileIdle(true)
				.addData("backup-url", backupURL).build();
		try {
			MulticastResult result = sender.send(message, devicesList, 1);

			logger.info(result.toString());
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {
					logger.info("canonical RegId = "+canonicalRegId);
				}
			} else {
				int error = result.getFailure();
				logger.info(""+error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
