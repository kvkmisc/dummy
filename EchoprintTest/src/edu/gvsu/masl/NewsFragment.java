package edu.gvsu.masl;

import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class NewsFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";

    /**
     * @return a new instance of {@link NewsFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static NewsFragment newInstance(CharSequence title, int indicatorColor,
            int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      final WebView myWebView = (WebView) getActivity().findViewById(R.id.webview);
      myWebView.setWebViewClient(new WebViewClient());
      WebSettings webSettings = myWebView.getSettings();
      webSettings.setJavaScriptEnabled(true);
      String[] urls={"http://www.stereogum.com/1720716/watch-weezer-play-go-away-acoustic-with-ms-mrs-lizzy-plapinger/video/",
      		"http://blog.kexp.org/2014/11/19/wednesday-music-news-136/",
      		"http://music-news.com/shownews.asp?H=Lavigne-still-not-good-with-husband&nItemID=84599",
      		"http://www.wonderingsound.com/lorde-insults-diplo-comet-singing/",
      		"http://www.virginmedia.com/music/news/story/2014/11/24/one-direction-lead-ama-winners/"
      		};
      Random random=new Random();
      myWebView.loadUrl(urls[Math.abs(random.nextInt())%5]);
      myWebView.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				// Check if the key event was the Back button and if there's history
		        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
		            myWebView.goBack();
		            return true;
		        }
		        // If it wasn't the Back key or there's no web page history, bubble up to the default
		        // system behavior (probably exit the activity)
				return false;
			}
		});
    }
}

