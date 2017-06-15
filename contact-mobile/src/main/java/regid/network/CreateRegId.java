package regid.network;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import fr.fmjmf.FromCloudActivity;
import fr.fmjmf.commons.AssetsPropertyReader;
import fr.fmjmf.commons.MobileApplication;

/** Post request to the service called regIdService */
public class CreateRegId extends AsyncTask<String, Void, Boolean> {
	private Properties properties;
	private Button publishRegIdBtn;
	private Button newContact;

	public CreateRegId(Button publishRegIdBtn, Button newContact) {
		this.publishRegIdBtn = publishRegIdBtn;
		this.newContact = newContact;
		AssetsPropertyReader asr = new AssetsPropertyReader(MobileApplication.getContext());
		this.properties = asr.getProperties("config.properties");
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Log.i(FromCloudActivity.TAG, "params.length == " + params.length);
		if (params.length == 1) {
			Calendar calendrier = Calendar.getInstance();
			RegId ri = new RegId(params[0], calendrier.getTime());
			TrafficStats.setThreadStatsTag(0xF00D);
			try {
				// Make network request using your http client.
				ClientResource cr = new ClientResource(properties.getProperty("url.to.regId.server"));
				cr.setMethod(Method.POST);
				cr.setRequestEntityBuffering(true);
				Log.i(FromCloudActivity.TAG, "params[0] == " + params[0]);
				cr.accept(MediaType.APPLICATION_JSON);
				JacksonRepresentation<RegId> rep = new JacksonRepresentation<RegId>(ri);
				Representation answer = cr.post(rep, MediaType.APPLICATION_JSON);
				// affichage de DEBUG
				Log.i(FromCloudActivity.TAG, "answer != null ? " + (answer != null));
				JacksonRepresentation<Boolean> regIdJSON = new JacksonRepresentation<Boolean>(answer, Boolean.class);
				try {
					Boolean result = regIdJSON.getObject();
					// RegId regId = regIdJSON.getObject(); // FIXME
					Log.i(FromCloudActivity.TAG, "answer == " + result);
					return result;
				} catch (IOException e) {
					Log.i(FromCloudActivity.TAG, e.getMessage());
					return Boolean.FALSE;
				}
			} finally {
				TrafficStats.clearThreadStatsTag();
			}
		} else
			return Boolean.FALSE;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result.booleanValue()) {
			newContact.setEnabled(true);
			publishRegIdBtn.setEnabled(false);
		} else {
			publishRegIdBtn.setEnabled(true);
		}
	}
}
