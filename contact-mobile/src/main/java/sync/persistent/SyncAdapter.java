package sync.persistent;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import contact.network.CreateNewPerson;
import contact.persistent.OrmHelper;
import fr.fmjmf.StaticClass;

/** The role of SyncAdapter is to adapt the local data of the sqlite database to the format of a remote database */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String TAG = "sync.persistent";
	// Global variables
	// Define a variable to contain a content resolver instance
	ContentResolver mContentResolver;

	/**
	 * Set up the sync adapter
	 */
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		/*
		 * If your app uses a content resolver, get an instance of it from the
		 * incoming Context
		 */
		mContentResolver = context.getContentResolver();
		Log.i(TAG, "SyncAdapter cstr" );
	}

	/**
	 * Set up the sync adapter. This form of the constructor maintains
	 * compatibility with Android 3.0 and later platform versions
	 */
	public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		/*
		 * If your app uses a content resolver, get an instance of it from the
		 * incoming Context
		 */
		mContentResolver = context.getContentResolver();
		// TODO
	}

	/*
	 * Specify the code you want to run in the sync adapter. The entire sync
	 * adapter runs in a background thread, so you don't have to set up your own
	 * background processing.
	 */
	@Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
    	// Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }
		Log.i(TAG, "1- Connecting to a server "+sb.toString());
    	// Get shows from the local storage
        ArrayList<contact.network.Contact> contacts = new ArrayList<contact.network.Contact>();
        Log.i(TAG, "1.0- provider "+provider.getClass().getName());
		Cursor contactCursor = mContentResolver.query(Entry.CONTENT_URI, null, null, null, null);
 		if (contactCursor != null) {
			Log.i(TAG, "1.1- contactCursor "+contactCursor.getCount());
			Log.i(TAG, "1.1- contactCursor.moveToFirst() "+contactCursor.moveToFirst());
			if (contactCursor.moveToFirst()) {
			    do {
			    	String lastname	= contactCursor.getString(contactCursor.getColumnIndex(OrmHelper.DATABASE_CONTACT_LASTNAME));
			    	String firstname= contactCursor.getString(contactCursor.getColumnIndex(OrmHelper.DATABASE_CONTACT_FIRSTNAME));
			    	String email	= contactCursor.getString(contactCursor.getColumnIndex(OrmHelper.DATABASE_CONTACT_EMAIL));
			  //  	Date creation 	= new Date(contactCursor.getLong(contactCursor.getColumnIndex(OrmHelper.DATABASE_CONTACT_CREATION))*1000);
			    	contact.network.Contact contact	= new contact.network.Contact(firstname, lastname, email);
					Log.i(TAG, "1.2- contact "+contact.toString());
			    	contacts.add(contact);
			    } while (contactCursor.moveToNext());
			}
		    contactCursor.close();
		    // supprimer les données
		    int howManyContacts = mContentResolver.delete(Entry.CONTENT_URI, null, null);
		    Log.i(TAG, "1.3- howManyContacts "+howManyContacts);
		}
		Log.i(TAG, "2- Downloading and uploading contacts: "+contacts.size());
		try {
			Context con = getContext().createPackageContext("fr.fmjmf", 0);// first app package name is "fr.fmjmf"
			SharedPreferences pref = con.getSharedPreferences("bdPref", Context.MODE_PRIVATE);
			String value = pref.getString("backupUrl", "local");
			StaticClass.setBackupURL(value);
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.toString());
		}
        for (contact.network.Contact c: contacts) {
        	try {
				// mettre à jour la base distante
        		CreateNewPerson task = null;
				Log.i(TAG, "2.0- StaticClass.getBackupURL(): "+StaticClass.getBackupURL());
				URL url = new URL(StaticClass.getBackupURL());
				Log.i(TAG, "2.1- For loop with contacts: "+url.getPath());
				URLConnection connection = url.openConnection();
				Log.i(TAG, "2.2- Connection for contact saving: "+connection.toString());
				if (connection != null) {
					task = new CreateNewPerson();
					Log.i(TAG, "2.3- Task for contact saving: "+connection.toString());
					task.doInBackground(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		Log.i(TAG, "3- Handling data conflicts or determining how fresh the data are");
    }

}
