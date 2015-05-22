package com.example.twittersearchandroidauth2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class TweetActivity extends Activity {

	public final static String SEARCH_QUERY = "com.example.twittersearchandroidauth2.TweetActivity.QUERY";
	public String queryString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		
		
		Intent intent = getIntent();
		String text = intent.getStringExtra(TweetListActivity.TWEET_TEXT);
		String iconUrl = intent.getStringExtra(TweetListActivity.USER_IMG);
		queryString = intent.getStringExtra(TweetListActivity.SEARCH_QUERY);
		
		TextView textViewTweet = (TextView) findViewById(R.id.text);
	    textViewTweet.setText(text);    
	    ImageView imageView = (ImageView) findViewById(R.id.icon);
	    try {
			imageView.setImageDrawable(new LoadImageTask().execute(iconUrl).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void goBack(View view){
		Intent intent = new Intent(this, TweetListActivity.class);
		intent.putExtra(SEARCH_QUERY, queryString);
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
