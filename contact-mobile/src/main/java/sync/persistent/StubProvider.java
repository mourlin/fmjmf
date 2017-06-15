package sync.persistent;

import java.sql.SQLException;
import java.util.Set;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import contact.persistent.Contact;
import contact.persistent.OrmHelper;

/*
 * Define an implementation of ContentProvider that stubs out all methods
 */
public class StubProvider extends ContentProvider {
	private static final String TAG = "sync.persistent";
	private OrmHelper ormHelper;
	private Dao<Contact, Integer> contactDao;
	/**
	 * Content authority for this provider.
	 */
	private static final String AUTHORITY = "fmjmf.fr";
	/**
	 * URI ID for route: /entries
	 */
	public static final int ROUTE_ENTRIES = 1;
	/**
	 * URI ID for route: /entries/{ID}
	 */
	public static final int ROUTE_ENTRIES_ID = 2;
	/**
	 * UriMatcher, used to decode incoming URIs.
	 */
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sUriMatcher.addURI(AUTHORITY, "entries", ROUTE_ENTRIES);
		sUriMatcher.addURI(AUTHORITY, "entries/*", ROUTE_ENTRIES_ID);
	}

	/*
	 * Always return true, indicating that the provider loaded correctly.
	 */
	@Override
	public boolean onCreate() {
		ormHelper = new OrmHelper(getContext());
		try {
			contactDao = ormHelper.getContactDao();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * Return no type for MIME type
	 */
	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case ROUTE_ENTRIES:
			return Entry.CONTENT_TYPE;
		case ROUTE_ENTRIES_ID:
			return Entry.CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	/*
	 * query() always returns no results
	 *
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "StubProvider.query "+ uri.toString());
		int uriMatch = sUriMatcher.match(uri);
		Log.d(TAG, "StubProvider uriMatch "+ uriMatch);
		switch (uriMatch) {
		case ROUTE_ENTRIES_ID:
			String id = uri.getLastPathSegment();
			try {
				CloseableIterator<Contact> first = contactDao.iterator(contactDao.queryBuilder().where().eq(Entry.COLUMN_NAME_ENTRY_ID, id).prepare());
				AndroidDatabaseResults result    = (AndroidDatabaseResults) first.getRawResults();
				return result.getRawCursor();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		case ROUTE_ENTRIES:
			Log.d(TAG, "StubProvider ROUTE_ENTRIES");
			QueryBuilder<Contact, Integer> qb = contactDao.queryBuilder();

			CloseableIterator<Contact> iterator = null;
			Cursor cursor = null;
			try {
				iterator = contactDao.iterator(qb.prepare());
				Log.d(TAG, "StubProvider iterator ");
				AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
				cursor = results.getRawCursor();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//iterator.closeQuietly();
			}
			Log.d(TAG, "StubProvider cursor ");
			cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
			Log.d(TAG, "StubProvider return "+cursor.getCount());
			return cursor;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	/*
	 * insert() always returns null (no URI)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		UpdateBuilder<Contact, Integer> insertBuilder = contactDao.updateBuilder();
		try {
			for (String key: values.keySet()) {
				String value = values.getAsString(key);
				insertBuilder.updateColumnValue(key, value);
			}
			insertBuilder.update();
			return uri;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * delete() always returns "no rows affected" (0)
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		try {
			DeleteBuilder<Contact, Integer> deleteBuilder = contactDao.deleteBuilder();
			return deleteBuilder.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * update() always returns "no rows affected" (0)
	 */
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		UpdateBuilder<Contact, Integer> updateBuilder = contactDao.updateBuilder();
		try {
			for (String key: values.keySet()) {
				String value = values.getAsString(key);
				updateBuilder.updateColumnValue(key, value);
			}
			// actually perform the update
			return updateBuilder.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}