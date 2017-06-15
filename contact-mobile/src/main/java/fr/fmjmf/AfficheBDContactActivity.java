package fr.fmjmf;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import contact.persistent.Contact;
import contact.persistent.OrmHelper;

public class AfficheBDContactActivity extends Activity {
	private TableLayout contactLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affiche_bd_contact);
		Log.i("contact.persistent", "AfficheBDContactActivity onCreate ");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		contactLayout = (TableLayout) findViewById(R.id.contactLayout);
		// lire tous les contacts en local
		OrmHelper ormHelper	= OpenHelperManager.getHelper(this, OrmHelper.class);
		try {
			Log.i("contact.persistent", "AfficheBDContactActivity onResume 1");
			Dao<Contact, Integer> contactDao	= ormHelper.getContactDao();
			Log.i("contact.persistent", "AfficheBDContactActivity onResume 2 "+contactDao);
			List<Contact> contactList			= contactDao.queryForAll();		// Pb à trouver
			Log.i("contact.persistent", "AfficheBDContactActivity onResume 3");
			SimpleDateFormat sdf				= new SimpleDateFormat("dd/MM hh:mm:ss");
			Log.i("contact.persistent", "nombre de contacts = "+contactList.size());
			Log.i("contact.persistent", "AfficheBDContactActivity onResume 4");
			for (Contact temp: contactList) {
				TableRow row	= new TableRow(this);
				TextView lastnameTV	= new TextView(this);
				lastnameTV.setText(temp.getLastname());
				lastnameTV.setPadding(10, 10, 10, 10);
				row.addView(lastnameTV);
				TextView firstnameTV	= new TextView(this);
				firstnameTV.setText(temp.getFirstname());
				firstnameTV.setPadding(10, 10, 10, 10);
				row.addView(firstnameTV);
				TextView emailTV	= new TextView(this);
				emailTV.setText(temp.getEmail());
				emailTV.setPadding(10, 10, 10, 10);
				row.addView(emailTV);
//				TextView creationTV	= new TextView(this);
//				creationTV.setText(sdf.format(temp.getCreation()));
//				row.addView(creationTV);
				contactLayout.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
