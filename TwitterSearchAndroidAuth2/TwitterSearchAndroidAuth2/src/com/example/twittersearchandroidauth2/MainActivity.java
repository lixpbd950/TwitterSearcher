package com.example.twittersearchandroidauth2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;



public class MainActivity extends Activity {

	public final static String SEARCH_QUERY = "com.example.twittersearchandroidauth2.MainActivity.QUERY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String qs = intent.getStringExtra(TweetListActivity.SEARCH_QUERY);
        if(qs != null){
        	EditText editText = (EditText) findViewById(R.id.search_message);
        	editText.setText(qs);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void searchTweets(View view){
    	Intent intent = new Intent(this, TweetListActivity.class);
    	EditText editText = (EditText) findViewById(R.id.search_message);
    	String searchQuery = editText.getText().toString();
    	intent.putExtra(SEARCH_QUERY, searchQuery);
        startActivity(intent);
    }

}
