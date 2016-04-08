package ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.views;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.R;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.general.Constants;

public class FTPServerWelcomeMessageActivity extends Activity {

    private EditText FTPServerAddressEditText;
    private Button displayWelcomeMessageButton;
    private TextView welcomeMessageTextView;

    private class FTPServerCommunicationAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                // TODO: exercise 4
                // open socket with FTPServerAddress (taken from params[0]) and port (Constants.FTP_PORT = 21)
                // get the BufferedReader attached to the socket (call to the Utilities.getReader() method)
                // should the line start with Constants.FTP_MULTILINE_START_CODE, the welcome message is processed
                // read lines from server while
                // - the value is different from Constants.FTP_MULTILINE_END_CODE1
                // - the value does not start with Constants.FTP_MULTILINE_START_CODE2
                // append the line to the welcomeMessageTextView text view content (on the UI thread!!!) - publishProgress(...)
                // close the socket
            } catch (Exception exception) {
                Log.d(Constants.TAG, exception.getMessage());
                if (Constants.DEBUG) {
                	exception.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            welcomeMessageTextView.setText("");
        }

        @Override
        protected void onProgressUpdate(String... progress) {
        	// TODO: exercise 4
            // append the progress[0] to the FTPServerAddressEditText edit text
        }

        @Override
        protected void onPostExecute(Void result) { }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            FTPServerCommunicationAsyncTask ftpServerCommunicationAsyncTask = new FTPServerCommunicationAsyncTask();
            ftpServerCommunicationAsyncTask.execute(FTPServerAddressEditText.getText().toString());
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftpserver_welcome_message);
		
        FTPServerAddressEditText = (EditText)findViewById(R.id.ftp_server_address_edit_text);

        displayWelcomeMessageButton = (Button)findViewById(R.id.display_welcome_message_button);
        displayWelcomeMessageButton.setOnClickListener(buttonClickListener);

        welcomeMessageTextView = (TextView)findViewById(R.id.welcome_message_text_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ftpserver_welcome_message, menu);
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
