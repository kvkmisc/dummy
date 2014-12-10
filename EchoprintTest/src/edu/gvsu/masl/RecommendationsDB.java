package edu.gvsu.masl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RecommendationsDB extends SQLiteOpenHelper{
	
	public final static String TABLE_NAME = "artists";
	public final static String ARTIST_NAME = "name";
	public final static String TITLE = "title";
	public final static String DURATION = "duration";
	public final static String LOCAL_LOCATION = "location";
	public final static String URL_LOCATION = "urlLocation";
	public final static String IMAGE_LOCAL_LOCATION = "imageLocal";
	public final static String IMAGE_URL_LOCATION = "imageUrl";
	
	final static String _ID = "_id";
	public final static String[] columns = { _ID, ARTIST_NAME , TITLE, DURATION, 
		LOCAL_LOCATION, URL_LOCATION, IMAGE_LOCAL_LOCATION, IMAGE_URL_LOCATION };

	final private static String CREATE_CMD =

	"CREATE TABLE artists (" + _ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ARTIST_NAME + " TEXT NOT NULL, " + TITLE + " TEXT NOT NULL, " + DURATION +
					" TEXT NOT NULL, " + LOCAL_LOCATION + " TEXT, " + URL_LOCATION + 
					" TEXT NOT NULL, " + IMAGE_LOCAL_LOCATION + " TEXT, " 
					+ IMAGE_URL_LOCATION + " TEXT NOT NULL)";

	final private static String NAME = "artist_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public RecommendationsDB(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	public void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}
}
