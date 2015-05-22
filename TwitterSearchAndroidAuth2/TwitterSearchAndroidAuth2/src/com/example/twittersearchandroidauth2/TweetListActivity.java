package com.example.twittersearchandroidauth2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import twitter4j.*;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


public class TweetListActivity extends Activity {

	private ConfigurationBuilder builder;
	private Twitter twitter;
	private static final String CONSUMER_KEY = "Ki2F9wVgmCmuEvtVbUUDA";
	private static final String CONSUMER_SECRET = "N9QmCnb8mzwjtuFJFGvUxvsQjrpOsHJGNc4B4VtiYs";
	public final static String SEARCH_QUERY = "com.example.twittersearchandroidauth2.TweetListActivity.QUERY";
	public final static String TWEET_TEXT = "com.example.twittersearchandroidauth2.TweetListActivity.TWEET_TEXT";
	public final static String USER_IMG = "com.example.twittersearchandroidauth2.TweetListActivity.USER_IMG";
	private String queryString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_list);
		
		ListView lv = (ListView) findViewById(R.id.tweetsListView);
		
		Intent intent = getIntent();
		String queryStringBack = intent.getStringExtra(TweetActivity.SEARCH_QUERY);;
		String queryStringSearch = intent.getStringExtra(MainActivity.SEARCH_QUERY);
		
		if(queryStringBack == null)
			queryString = queryStringSearch;
		else
			queryString = queryStringBack;
		
		TweetArrayAdaptor adapter = new TweetArrayAdaptor(this, searchTweets(queryString));
	    lv.setAdapter(adapter);    
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?>adapter,View v, int pos, long l){

		    	Tweet t = ((TweetArrayAdaptor) adapter.getAdapter()).getItem(pos);
		    		
		    	Intent intent = new Intent(TweetListActivity.this, TweetActivity.class);
		    	//based on item add info to intent
		    	intent.putExtra(TWEET_TEXT, t.user+": "+t.text);
		    	intent.putExtra(USER_IMG, t.iconUrl);
		    	intent.putExtra(SEARCH_QUERY, queryString);
		    	
		    	startActivity(intent);

	    	}
		});
	    	
	    	

	}
	
	private List<Tweet> searchTweets(String qs){
		
		List<Tweet> tweets = null;
		
		builder = new ConfigurationBuilder();
		builder.setApplicationOnlyAuthEnabled(true);
		
		
		builder.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET);
		twitter = new TwitterFactory(builder.build()).getInstance();
		try {
			
			OAuth2Token token = new TokenTask().execute(twitter).get();
			tweets = new SearchTeewtsTask().execute(qs, token, twitter).get();
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return tweets;
	}
	
	public void goBack(View view){
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(SEARCH_QUERY, queryString);
    	startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet_list, menu);
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
	
	private class TokenTask extends AsyncTask<Twitter, Void, OAuth2Token>{

		@Override
		protected OAuth2Token doInBackground(Twitter... params) {
			OAuth2Token bearerToken = null;
			try {
				bearerToken = params[0].getOAuth2Token();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			return bearerToken;
		}

	}
	
	private class SearchTeewtsTask extends AsyncTask<Object, Void, List<Tweet>>{

		@Override
		protected List<Tweet> doInBackground(Object... params) {
			
			String queryString = (String) params[0];
			OAuth2Token token = (OAuth2Token) params[1];
			Twitter tw = (Twitter) params[2];
			
			tw.setOAuth2Token(token);
			List<Tweet> tweets = new ArrayList<Tweet>();
			
			Query query = new Query(queryString);
		    QueryResult result;
		
			try {
				result = tw.search(query);
				for (twitter4j.Status status : result.getTweets()) {
					
					tweets.add(new Tweet("@" + status.getUser().getScreenName(), status.getText(), status.getUser().getMiniProfileImageURL()));
			    }
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return tweets;
		}

	}
	
	
}




