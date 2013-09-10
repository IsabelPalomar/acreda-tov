package com.acreda.testorientacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends Activity{
	private static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_main);	    
	    
	 // Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	            handleSendText(intent); // Handle text being sent
	        } else if (type.startsWith("image/")) {
	            handleSendImage(intent); // Handle single image being sent
	        }
	    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendMultipleImages(intent); // Handle multiple images being sent
	        }
	    } else {
	        // Handle other intents, such as being started from the home screen
	    }
	    
	}
	

	private void handleSendText(Intent intent) {
		TextView textView = (TextView)findViewById(R.id.hello_world);
		
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    if (sharedText != null) {
	        // Update UI to reflect text being shared
	    	 textView.setText(sharedText);
	    }
	}
	

	private void handleSendImage(Intent intent) {
		
		Button btn =  (Button)findViewById(R.id.filter_btn);
		
		//show the other ui item
		btn.setVisibility(View.VISIBLE);
		
		//get the image view
		ImageView picView = (ImageView)findViewById(R.id.first_shared_image);
		
		//get the uri of the received image
		Uri receivedUri = (Uri)intent.getParcelableExtra(Intent.EXTRA_STREAM);
		//check we have a uri
		if (receivedUri != null) {
		    //set the picture
		    //RESAMPLE YOUR IMAGE DATA BEFORE DISPLAYING
		    picView.setImageURI(receivedUri); //just for demonstration (http://bit.ly/15O8dru)
		}
		
	}
	
	private void handleSendMultipleImages(Intent intent) {
		ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	    if (imageUris != null) {
	        // Update UI to reflect multiple images being shared
	    	//do something funny here iza ñ.ñ
	    }
		
	}


	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.hello_world);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Gray button */
	public void grayImage(View view) {
		
		//get the image view
	    ImageView picView = (ImageView)findViewById(R.id.first_shared_image);
		
		ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);

	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
	    picView.setColorFilter(filter);
		
	}
	
	/** Called when the user clicks the Save button */
	public void saveImage(View view) {
		//get the image view
	    ImageView imageview = (ImageView)findViewById(R.id.first_shared_image);
	    
		imageview.buildDrawingCache();
	    Bitmap bm=imageview.getDrawingCache();
	    
	    OutputStream fOut = null;
	    Uri outputFileUri;
	     try {
	    File root = new File(Environment.getExternalStorageDirectory()
	      + File.separator + "Camera" + File.separator);
	    root.mkdirs();
	   File sdImageMainDirectory = new File(root, "myPicName.jpg");
	    outputFileUri = Uri.fromFile(sdImageMainDirectory);
	    fOut = new FileOutputStream(sdImageMainDirectory);
	   } catch (Exception e) {
	      Toast.makeText(this, "Error occured. Please try again later.",
	      Toast.LENGTH_SHORT).show();
	   }

	   try {
	    bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
	    fOut.flush();
	    fOut.close();
	   } catch (Exception e) {
	   }
	    
		
	}
	

}
