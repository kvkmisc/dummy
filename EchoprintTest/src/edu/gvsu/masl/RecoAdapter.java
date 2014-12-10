package edu.gvsu.masl;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecoAdapter extends BaseAdapter {
	
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    public RecoAdapter(Activity a, ArrayList<HashMap<String, String>> d){
    	activity=a;
    	data=d;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
        if(convertView==null){
            vi = inflater.inflate(R.layout.recommendation_item, null);
        }
        TextView title=(TextView)vi.findViewById(R.id.recotitle);
        TextView artist=(TextView)vi.findViewById(R.id.recoartist);
        TextView duration=(TextView)vi.findViewById(R.id.recoduration);
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        title.setText(song.get("title"));
        artist.setText(song.get("artist"));
        duration.setText(song.get("duration"));
        
        
        
		return vi;
	}

}
