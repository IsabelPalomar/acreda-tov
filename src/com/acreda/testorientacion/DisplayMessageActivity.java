package com.acreda.testorientacion;

import java.io.Console;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.http.protocol.HTTP;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends Activity {

	private static final int PICK_CONTACT_REQUEST = 1;
	private static final int CAMERA_PIC_REQUEST = 1;




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
    
    public void makeCall(View view){
    	
       //get the number
       TextView textview = (TextView)findViewById(R.id.number); 
       String numberStr = (String) textview.getText();
    	
    	//make a call
    	Uri number = Uri.parse("tel:" + numberStr);
    	Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
    	
    	if(isIntentSafe(callIntent)){
    		startActivity(callIntent);
    	}
    	
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
    	
    	PackageManager packagemanager = getPackageManager();
    	List<ResolveInfo> activities = packagemanager.queryIntentActivities(mapIntent, 0);
    	boolean isIntentSafe = activities.size() > 0;
    	
    	if(isIntentSafe){
    		startActivity(mapIntent);
    	}
    	
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
    
    public void useChooser(View view){
    	
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	
    	// Always use string resources for UI text.
    	// This says something like "Share this photo with"
    	//String title = getResources().getString(R.string.chooser_title);
    	String title = "Share this photo with";
    	
    	// Create and start the chooser
    	Intent chooser = Intent.createChooser(intent, title);
    	
    	if(isIntentSafe(chooser)){
    		startActivity(chooser);
    	}
    	
    }
    
    public void pickContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    
    public void shareText(View view){
    	//Share text
    	Intent sendIntent = new Intent();
    	sendIntent.setAction(Intent.ACTION_SEND);
    	sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
    	sendIntent.setType("text/plain");
    	
    	if(isIntentSafe(sendIntent)){
    		//set the chooser text
    		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_text)));
    	}    	
    }
    
    public void shareImage(View view){
    	Intent shareIntent = new Intent();
    	shareIntent.setAction(Intent.ACTION_SEND);
    	shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + getUriToLastImage() ));
    	shareIntent.setType("image/jpeg");
    	
    	startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_image)));

    }
    
    /*
     * Return the last image path
     */
    
    public String getUriToLastImage(){
    	
        final ContentResolver cr = getContentResolver();
        final String[] p1 = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };
        
        Cursor c1 = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, p1, null, null,
                p1[1] + " DESC");
    	
        String path = ""; 
        int    idx  = 0;
       
       if(c1.moveToFirst()){
    	   idx = c1.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
    	   path = c1.getString(idx);
       }
       
       return path;
        	
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(Phone.NUMBER);
                String number = cursor.getString(column);
                
                TextView textview = (TextView)findViewById(R.id.number); 
                textview.setText(number);
                

                // Do something with the phone number...
            }
        } 
        
        
    }
    

    /**
     * general functions for activity
     * @return 
     */
    
    
    public boolean isIntentSafe(Intent intent){
    	PackageManager packagemanager = getPackageManager();
    	List<ResolveInfo> activities = packagemanager.queryIntentActivities(intent, 0);
    	boolean isIntentSafe = activities.size() > 0;
    	
    	return isIntentSafe;
    	
    }
    
    

}
