package edu.gvsu.masl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class RecommendationsFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";
    ListView listview;
    RecoAdapter adapter;
    /**
     * @return a new instance of {@link RecommendationsFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static RecommendationsFragment newInstance(CharSequence title, int indicatorColor,
            int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        RecommendationsFragment fragment = new RecommendationsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recommendations_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "Jelly Bean");
        map.put("artist", "Michael Jackson");
        map.put("duration", "4.53");
        songsList.add(map);

        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("title", "Losing Grip");
        map1.put("artist", "Avril Lavigne");
        map1.put("duration", "3.54");
        songsList.add(map1);
        
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("title", "Thriller");
        map2.put("artist", "Michael Jackson");
        map2.put("duration", "5.57");
        songsList.add(map2);
        
        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("title", "Tomorrow");
        map3.put("artist", "Avril Lavigne");
        map3.put("duration", "4.53");
        songsList.add(map3);
        
        HashMap<String, String> map4 = new HashMap<String, String>();
        map4.put("title", "Give Me Everything!");
        map4.put("artist", "Pit Bull");
        map4.put("duration", "3.43");
        songsList.add(map4);
        
        HashMap<String, String> map5 = new HashMap<String, String>();
        map5.put("title", "Shut it Down");
        map5.put("artist", "Pit Bull");
        map5.put("duration", "3.45");
        songsList.add(map5);
        
        HashMap<String, String> map6 = new HashMap<String, String>();
        map6.put("title", "Sk8er Boi");
        map6.put("artist", "Avril Lavigni");
        map6.put("duration", "3.24");
        songsList.add(map6);
        
        HashMap<String, String> map7 = new HashMap<String, String>();
        map7.put("title", "Friday");
        map7.put("artist", "Rebecca Black");
        map7.put("duration", "3.30");
        songsList.add(map7);
        
        HashMap<String, String> map8 = new HashMap<String, String>();
        map8.put("title", "La La La");
        map8.put("artist", "Shakira");
        map8.put("duration", "3.17");
        songsList.add(map8);
        
        HashMap<String, String> map9 = new HashMap<String, String>();
        map9.put("title", "She wolf");
        map9.put("artist", "Shakira");
        map9.put("duration", "3.08");
        songsList.add(map9);
        
        HashMap<String, String> map10 = new HashMap<String, String>();
        map10.put("title", "In Your Words!");
        map10.put("artist", "Rebecca Black");
        map10.put("duration", "3.06");
        songsList.add(map10);
        
        HashMap<String, String> map11 = new HashMap<String, String>();
        map11.put("title", "Beat It");
        map11.put("artist", "Michael Jackson");
        map11.put("duration", "4.18");
        songsList.add(map11);
        
        listview=(ListView)getActivity().findViewById(R.id.recolist);
        
        //Getting adapter by passing xml data arraylist
        adapter=new RecoAdapter(getActivity(), songsList);
        listview.setAdapter(adapter);
    }
}

