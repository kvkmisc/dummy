package edu.gvsu.masl;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlayListListFragment extends ListFragment {
	
	private SQLiteDatabase mDB = null;
	private RecommendationsDB mDbHelper;
	private SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		// Create a new DatabaseHelper
		mDbHelper = new RecommendationsDB(getActivity().getApplicationContext());
		
		// Get the underlying database for writing
		mDB = mDbHelper.getWritableDatabase();

		// start with an empty database
		clearAll();
		
		// Insert records
		insertArtists();
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	     super.onViewCreated(view, savedInstanceState);
	        
	     // Create a cursor
	     Cursor c = readArtists();
			mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
					R.layout.playlist_item, c, new String[] {RecommendationsDB.TITLE, 
						RecommendationsDB.ARTIST_NAME, RecommendationsDB.DURATION}, 
					new int[] { R.id.title, R.id.artist , R.id.playduration}, 0);

			setListAdapter(mAdapter);
			
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		// Intent used for starting the MusicService
		final Intent musicServiceIntent = new Intent(getActivity().getApplicationContext(),
						MusicPlayerService.class);
		String poString=Long.toString(position);
		String idString=Long.toString(id);
		musicServiceIntent.putExtra("Po", poString);
		musicServiceIntent.putExtra("Id", idString);
		getActivity().getApplicationContext().startService(musicServiceIntent);
	}
	
	// Returns all records in the database
	private Cursor readArtists() {
		return mDB.query(RecommendationsDB.TABLE_NAME,
				RecommendationsDB.columns, null, new String[] {}, null, null,
				null);
	}
	
	// Delete all records
	private void clearAll() {
		
		mDB.delete(RecommendationsDB.TABLE_NAME, null, null);
		
	}
	
	// Insert several artist records
	private void insertArtists() {

		ContentValues values = new ContentValues();
		values.put(RecommendationsDB.ARTIST_NAME, "Avril Lavigne");
		values.put(RecommendationsDB.TITLE, "Complicated");
		values.put(RecommendationsDB.DURATION, "5.3");
		values.put(RecommendationsDB.IMAGE_URL_LOCATION, "NULL");
		values.put(RecommendationsDB.URL_LOCATION, "NULL");
		mDB.insert(RecommendationsDB.TABLE_NAME, null, values);
		
		values.clear();

		values.put(RecommendationsDB.ARTIST_NAME, "Michael Jackson");
		values.put(RecommendationsDB.TITLE, "They don't care about us");
		values.put(RecommendationsDB.DURATION, "5.3");
		values.put(RecommendationsDB.IMAGE_URL_LOCATION, "NULL");
		values.put(RecommendationsDB.URL_LOCATION, "NULL");
		mDB.insert(RecommendationsDB.TABLE_NAME, null, values);

		values.clear();

		values.put(RecommendationsDB.ARTIST_NAME, "Pit Bull");
		values.put(RecommendationsDB.TITLE, "Let it rain over me");
		values.put(RecommendationsDB.DURATION, "5.3");
		values.put(RecommendationsDB.IMAGE_URL_LOCATION, "NULL");
		values.put(RecommendationsDB.URL_LOCATION, "NULL");
		mDB.insert(RecommendationsDB.TABLE_NAME, null, values);

		values.clear();

		values.put(RecommendationsDB.ARTIST_NAME, "shakira");
		values.put(RecommendationsDB.TITLE, "Waka Waka");
		values.put(RecommendationsDB.DURATION, "5.3");
		values.put(RecommendationsDB.IMAGE_URL_LOCATION, "NULL");
		values.put(RecommendationsDB.URL_LOCATION, "NULL");
		mDB.insert(RecommendationsDB.TABLE_NAME, null, values);
	}
		
		// Close database
	@Override
	public void onDestroy() {

		mDB.close();
		mDbHelper.deleteDatabase();

		super.onDestroy();

	}
}
