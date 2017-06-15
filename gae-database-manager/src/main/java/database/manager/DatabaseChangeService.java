package database.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class DatabaseChangeService extends ServerResource {
	final static Logger logger = Logger.getLogger(PublishUrlDatabase.class.getName());
	private static Properties props;
	private static int index	= 0;
	static {
		props					= new Properties();
		InputStream inStream	= Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			if (inStream != null) {
				props.load(inStream);
			} else {	// add all properties from the appengine-web.xml
				props.put("url.for.backup", System.getProperty("url.for.backup"));
				props.put("url.for.backup.0", System.getProperty("url.for.backup.0"));
				props.put("url.for.backup.1", System.getProperty("url.for.backup.1"));
				props.put("api.key", System.getProperty("api.key"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** First method in the lifecycle of a ServerResource */
	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Get
	public void change() throws Exception {
		// 1- mettre à jour l'url dans l'objet props
		index = (index + 1) % 2;
		final String backupURL	= props.getProperty("url.for.backup."+index);
		// 2- notifier les smartphones à la PublisURLDatabase
		logger.info("DatabaseChangeService");
		final String API_KEY			= props.getProperty("api.key");
//		il faut les regId de tous les devices à mettre à jour	-> envoyer une requete à gae-regids-registry la methode searchAll
		RegIdInfo regIdInfo				= new RegIdInfo();
		List<RegId> regIdList			= regIdInfo.call();
//																	-> ajouter les regId à devicesList
		Sender sender 					= new Sender(API_KEY);
		ArrayList<String> devicesList	= new ArrayList<String>();
		String newRegId					= "";
		for (RegId regId: regIdList) {
			newRegId = regId.getRegId();
			devicesList.add(newRegId);
		}
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
