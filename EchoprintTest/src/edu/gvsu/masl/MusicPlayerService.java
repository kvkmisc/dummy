package edu.gvsu.masl;

import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;

public class MusicPlayerService extends Service{

	@SuppressWarnings("unused")
	private final String TAG = "MusicService";

	private static final int NOTIFICATION_ID = 1;
	private MediaPlayer mPlayer;
	private int mStartID;
	private  int [] songs=new int[] {R.raw.complicated,R.raw.theydontcareaboutus,R.raw.rainoverme,R.raw.wakawaka};
	String poString="0";
	String idString="0";
	String songName="";
	
	private RecommendationsDB mDbHelper;
	private SQLiteDatabase mDB = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Create a new DatabaseHelper
		mDbHelper = new RecommendationsDB(getApplicationContext());
				
		// Get the underlying database for writing
		mDB = mDbHelper.getWritableDatabase();

		// Set up the Media Player
		int id=Integer.parseInt(poString)%4;
		mPlayer = MediaPlayer.create(this, songs[id]);
		if (null != mPlayer) {

			mPlayer.setLooping(false);

			// Stop Service when music has finished playing
			mPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {

					// stop Service if it was started with this ID
					// Otherwise let other start commands proceed
					stopSelf(mStartID);

				}
			});
		}

	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		
		Bundle b=intent.getExtras();
		poString=b.getString("Po");
		idString=b.getString("Id");
		
		Cursor cursor=mDB.query(RecommendationsDB.TABLE_NAME, RecommendationsDB.columns, "_Id=?", new String[] {idString}, null, null, null);
		cursor.moveToFirst();
		songName=cursor.getString(2);
		

		// Create a notification area notification so the user 
		// can get back to the MusicServiceClient
		
		final Intent notificationIntent = new Intent(getApplicationContext(),
				PlayRecoActivity.class);
		final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		
		final Notification notification = new Notification.Builder(
				getApplicationContext())
				.setSmallIcon(android.R.drawable.ic_media_play)
				.setOngoing(true).setContentTitle("Dhvani Playing")
				.setContentText(songName)
				.setContentIntent(pendingIntent).build();
		
		// Put this Service in a foreground state, so it won't 
		// readily be killed by the system  
		startForeground(NOTIFICATION_ID, notification);

		
		int current_index = (Integer.parseInt(poString))% 4;
        AssetFileDescriptor afd = this.getResources().openRawResourceFd(songs[current_index]);
	    
        try {
        	mPlayer.reset();
			mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getDeclaredLength());
			mPlayer.prepare();
			mPlayer.start();
            afd.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != mPlayer) {

			// ID for this start command
			mStartID = startid;

			if (mPlayer.isPlaying()) {

				// Rewind to beginning of song
				mPlayer.seekTo(0);

			} else {

				// Start playing song
				mPlayer.start();

			}

		}

		// Don't automatically restart this Service if it is killed
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {

		if (null != mPlayer) {

			mPlayer.stop();
			mPlayer.release();

		}
	}

	// Can't bind to this Service
	@Override
	public IBinder onBind(Intent intent) {

		return null;

	}
}
