package com.example.twittersearchandroidauth2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class LoadImageTask extends AsyncTask<String, Void, Drawable>{

	@Override
	protected Drawable doInBackground(String... params) {
		Drawable drawable = null;
	    String url = params[0];
		try {
	      InputStream is = (InputStream) new URL(url).getContent();
	      drawable = Drawable.createFromStream(is, "srcname");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return drawable;
	}
}