package com.example.twittersearchandroidauth2;


import java.util.List;
import java.util.concurrent.ExecutionException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.*;

public class TweetArrayAdaptor extends ArrayAdapter<Tweet> {

  private final Context context;
  private final List<Tweet> tweets;  

  public TweetArrayAdaptor(Context context, List<Tweet> tweets) {
    super(context, R.layout.tweet, tweets);
    this.context = context;
    this.tweets = tweets;
  }

  @SuppressLint("ViewHolder") @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Tweet tweet = tweets.get(position);
    View tweetView = inflater.inflate(R.layout.tweet, parent, false);
    TextView textViewTweet = (TextView) tweetView.findViewById(R.id.text);
    textViewTweet.setText(tweet.user+": "+tweet.text);    
    ImageView imageView = (ImageView) tweetView.findViewById(R.id.icon);
    try {
		imageView.setImageDrawable(new LoadImageTask().execute(tweet.iconUrl).get());
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return tweetView;
  }

  public Tweet getItem(int position){

	  return tweets.get(position);
  }
  
  
}