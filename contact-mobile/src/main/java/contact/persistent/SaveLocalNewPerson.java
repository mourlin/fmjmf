package contact.persistent;

import java.sql.Date;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import fr.fmjmf.StaticClass;

public class SaveLocalNewPerson extends AsyncTask<contact.network.Contact, Void, Boolean> {
	public static final String TAG = "contact.persistent";
	private OrmHelper ormHelper;

	public SaveLocalNewPerson(OrmHelper ormHelper) {
		super();
		this.ormHelper = ormHelper;
	}

	@Override
	protected Boolean doInBackground(contact.network.Contact... params) {
		if (params.length == 1) {
			contact.network.Contact c = params[0];
			Contact contact = new Contact();
			contact.setLastname(c.getLastname());
			contact.setFirstname(c.getFirstname());
			contact.setEmail(c.getEmail());
			java.util.Date today = new java.util.Date();
			contact.setCreation(new Date(today.getTime()));
			try {
				Dao<Contact, Integer> contactDao = ormHelper.getContactDao();
				int info	= contactDao.create(contact);
				Log.i(TAG, "contact sauvegardé localement "+info);
				} catch (SQLException e) {
				e.printStackTrace();
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		TextView contactInfo = StaticClass.getContactInfo();
		if (result.booleanValue())
			contactInfo.setText("Success in local database");
		else
			contactInfo.setText("Failure in local database");
		super.onPostExecute(result);
	}
}
