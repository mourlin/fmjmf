package contact.persistent;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OrmHelper extends OrmLiteSqliteOpenHelper {
	public static final String TAG = "contact.persistent";
	public static final String DATABASE_NAME = "contact.db";
	public static final String DATABASE_TABLE = "contact";
	public static final String DATABASE_TABLE_CONTACT_ID = "contact_id";
	public static final String DATABASE_CONTACT_LASTNAME = "lastname";
	public static final String DATABASE_CONTACT_FIRSTNAME = "firstname";
	public static final String DATABASE_CONTACT_EMAIL = "email";
	public static final String DATABASE_CONTACT_CREATION = "creatoion";
	private static final int DATABASE_VERSION = 1;
	private Dao<Contact, Integer> contactDao; 

	public OrmHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "Tables created in " + DATABASE_NAME);
			TableUtils.createTable(connectionSource, Contact.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Contact.class, true);
	//		onCreate(db);
		} catch (SQLException e) {
			Log.e(TAG, "Impossible to drop database", e);
			throw new RuntimeException(e);
		}
	}

	// Generic: Class and Id type
	public Dao<Contact, Integer> getContactDao() throws SQLException {
		if (contactDao == null)
			contactDao = super.getDao(Contact.class);
		return contactDao;
	}

}
