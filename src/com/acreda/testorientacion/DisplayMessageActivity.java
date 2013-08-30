package com.acreda.testorientacion;

import java.util.Calendar;

import org.apache.http.protocol.HTTP;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        
        //get message from intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        /*TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView); */      

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	// Handle presses on the action bar items
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        /*case R.id.action_search:
            openSearch();
            return true;
        case R.id.action_settings:
            openSettings();
            return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * Open a map when the user click a button
     * @param view
     */
    public void openMap(View view){
    	// Map point based on address
    	//Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
    	// Or map point based on latitude/longitude
    	Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
    	Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
    	startActivity(mapIntent);
    }
    
    /**
     * Open a web page
     * @param view
     */
    public void openWeb(View view){
    	Uri webpage = Uri.parse("http://www.isabelpalomar.com");
    	Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
    	startActivity(webIntent);
    }
    
    /**
     * Send an email
     * @param view
     */
    public void sendEmail(View view){
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
    	// The intent does not have a URI, so declare the "text/plain" MIME type
    	emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
    	emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"isabel.palomarb@gmail.com"}); // recipients
    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
    	emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
    	//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
    	// You can also attach multiple items by passing an ArrayList of Uris
    	startActivity(emailIntent);
    	
    }
    
    @SuppressLint("NewApi")
	public void createEvent(View view){
    	Intent calendarIntent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
    	Calendar beginTime = Calendar.getInstance();
    	Calendar endTime = Calendar.getInstance();
    	calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
    	calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
    	calendarIntent.putExtra(Events.TITLE, "Ninja class");
    	calendarIntent.putExtra(Events.EVENT_LOCATION, "Secret dojo");
    	startActivity(calendarIntent);
    }
    
    

}
