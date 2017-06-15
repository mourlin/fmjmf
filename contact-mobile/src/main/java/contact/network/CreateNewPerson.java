package contact.network;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import fr.fmjmf.StaticClass;

public class CreateNewPerson extends AsyncTask<Contact, Void, Boolean> {
	public static final String TAG = "contact.network";

	@Override
	public Boolean doInBackground(Contact... params) {
		String backupUrl = StaticClass.getBackupURL();
		Log.i(TAG, "barckUrl = " + backupUrl);
		Boolean result = false;
		TrafficStats.setThreadStatsTag(0x0080);
		try {
			// Make network request using your http client.
			if (params.length == 1) {
				ClientResource cr = new ClientResource(backupUrl);
				cr.setMethod(Method.POST);
				cr.setEntityBuffering(true);
				cr.accept(MediaType.APPLICATION_JSON);
				JacksonRepresentation<Contact> contact = new JacksonRepresentation<Contact>(params[0]);
				Log.i(TAG, "contact = " + params[0]);
				Representation answer = cr.post(contact, MediaType.APPLICATION_JSON);
				Log.i(TAG, "answer = " + answer);
				JacksonRepresentation<Boolean> answerIdJSON = new JacksonRepresentation<Boolean>(answer, Boolean.class);
				Log.i(TAG, "answerIdJSON = " + answerIdJSON);
				try {
					result = answerIdJSON.getObject();
					Log.i(TAG, "result = " + result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			TrafficStats.clearThreadStatsTag();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		TextView contactInfo = StaticClass.getContactInfo();
		if (result.booleanValue())
			contactInfo.setText("Success in remote database");
		else
			contactInfo.setText("Failure in remote database");
		super.onPostExecute(result);
	}
}
