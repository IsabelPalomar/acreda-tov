package com.acreda.testorientacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ShareActivity extends Activity{
	private static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_main);

	    // Get the intent that started this activity
	    Intent intent = getIntent();
	    Uri data = intent.getData();

	    // Figure out what to do based on the intent type
	    if (intent.getType().indexOf("image/") != -1) {
	        // Handle intents with image data ...
	    } else if (intent.getType().equals("text/plain")) {
	        // Handle intents with text ...
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

}
