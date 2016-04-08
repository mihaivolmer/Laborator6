package ro.pub.cs.systems.eim.lab06.daytimeprotocol.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ro.pub.cs.systems.eim.lab06.daytimeprotocol.R;
import ro.pub.cs.systems.eim.lab06.daytimeprotocol.general.Constants;
import ro.pub.cs.systems.eim.lab06.daytimeprotocol.general.Utilities;

public class DayTimeProtocolActivity extends Activity {
	
	private TextView dayTimeProtocolTextView = null;
	private Button getInformationButton = null;
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	
	private class NISTCommunicationAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String dayTimeProtocol = null;
			try {
				Socket socket = new Socket(Constants.NIST_SERVER_HOST, Constants.NIST_SERVER_PORT);
				BufferedReader bufferedReader = Utilities.getReader(socket);
				bufferedReader.readLine();
				dayTimeProtocol = bufferedReader.readLine();
				Log.d(Constants.TAG, "The server returned: " + dayTimeProtocol);
			} catch (UnknownHostException unknownHostException) {
				Log.e(Constants.TAG, "An exception has occurred: " + unknownHostException.getMessage());
				if (Constants.DEBUG) {
					unknownHostException.printStackTrace();
				}
			} catch (IOException ioException) {
				Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			}
			return dayTimeProtocol;
		}
		
		@Override
		protected void onPostExecute(String result) {
			dayTimeProtocolTextView.setText(result);
		}
		
	}
	
	private class ButtonClickListener implements Button.OnClickListener {
		@Override
		public void onClick(View view) {
			NISTCommunicationAsyncTask nistCommunicationAsynckTask = new NISTCommunicationAsyncTask();
			nistCommunicationAsynckTask.execute();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_time_protocol);
		
		dayTimeProtocolTextView = (TextView)findViewById(R.id.daytime_protocol_text_view);
		getInformationButton = (Button)findViewById(R.id.get_information_button);
		getInformationButton.setOnClickListener(buttonClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_time_protocol, menu);
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
