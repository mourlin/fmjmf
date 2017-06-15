package sync.persistent;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import contact.persistent.OrmHelper;

public class Entry implements BaseColumns {
	/**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "fmjmf.fr";

    /**
     * Base URI. (content://com.example.android.network.sync.basicsyncadapter)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Path component for "entry"-type resources..
     */
    private static final String PATH_ENTRIES = "entries";

    /**
     * MIME type for lists of entries.
     */
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mobile.contact.entries";
    /**
     * MIME type for individual entries.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mobile.contact.entry";

    /**
     * Fully qualified URI for "entry" resources.
     */
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

    /**
     * Table name where records are stored for "entry" resources.
     */
    public static final String TABLE_NAME = OrmHelper.DATABASE_TABLE;
    /**
     * Atom ID. (Note: Not to be confused with the database primary key, which is _ID.
     */
    public static final String COLUMN_NAME_ENTRY_ID = OrmHelper.DATABASE_TABLE_CONTACT_ID;
    /**
     * Article title
     */
    public static final String COLUMN_NAME_TITLE = "title";
    /**
     * Article hyperlink. Corresponds to the rel="alternate" link in the
     * Atom spec.
     */
    public static final String COLUMN_NAME_LINK = "link";
    /**
     * Date article was published.
     */
    public static final String COLUMN_NAME_PUBLISHED = "published";
}
