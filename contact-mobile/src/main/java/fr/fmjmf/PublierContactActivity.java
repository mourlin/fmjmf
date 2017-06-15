package fr.fmjmf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import contact.network.Contact;
import contact.network.CreateNewPerson;
import contact.persistent.OrmHelper;
import contact.persistent.SaveLocalNewPerson;

/**
 * @author fabrice
 *
 */
public class PublierContactActivity extends Activity {
	public static final String TAG = "fr.fmjmf";
	private TextView contactInfo;
	private OrmHelper ormHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_new);
		contactInfo = (TextView) findViewById(R.id.contactInfo);
		StaticClass.setContactInfo(contactInfo);
		ormHelper	= OpenHelperManager.getHelper(this, OrmHelper.class);
	}

	public void sendContact(View v) {
		Log.i(TAG, "Creation d'un nouveau contact");
		// test si on est en local ou en remote
		AsyncTask<Contact, Void, Boolean> task = null;
		if ("local".equals(StaticClass.getBackupURL())) {
			task 		= new SaveLocalNewPerson(ormHelper);
			Log.i("contact.persistent", "creation SaveLocalNewPerson");
		} else {
			try {
				URL url = new URL(StaticClass.getBackupURL());
				URLConnection connection = url.openConnection();
				if (connection != null)
					task = new CreateNewPerson();
				else
					task 	= new SaveLocalNewPerson(ormHelper);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				task 		= new SaveLocalNewPerson(ormHelper);
			} catch (IOException e) {
				e.printStackTrace();
				task 		= new SaveLocalNewPerson(ormHelper);
			}
		}
		Contact newPerson = new Contact();
		EditText nomET = (EditText) findViewById(R.id.nom);
		newPerson.setLastname(nomET.getText().toString());
		EditText prenomET = (EditText) findViewById(R.id.prenom);
		newPerson.setFirstname(prenomET.getText().toString());
		EditText emailET = (EditText) findViewById(R.id.email);
		newPerson.setEmail(emailET.getText().toString());
		task.execute(newPerson);
		nomET.setText("");
		prenomET.setText("");
		emailET.setText("");
	}
	// listerContact
	public void listerContact(View v) {
		Intent intent	= new Intent(getApplicationContext(), AfficheBDContactActivity.class);
		Log.i("contact.persistent", "appel de AfficheBDContactActivity");
		startActivity(intent);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (ormHelper != null) {
			OpenHelperManager.releaseHelper();
			ormHelper	= null;
		}
	}
}
