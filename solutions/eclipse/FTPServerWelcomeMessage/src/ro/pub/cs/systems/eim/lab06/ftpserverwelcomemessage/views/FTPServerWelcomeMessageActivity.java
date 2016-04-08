package ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.views;

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
import android.widget.EditText;
import android.widget.TextView;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.R;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.general.Constants;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.general.Utilities;

public class FTPServerWelcomeMessageActivity extends Activity {

    private EditText FTPServerAddressEditText;
    private Button displayWelcomeMessageButton;
    private TextView welcomeMessageTextView;

    private class FTPServerCommunicationAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Socket socket = null;
            try {
                socket = new Socket(params[0], Constants.FTP_PORT);
                Log.v(Constants.TAG, "Connected to: " + socket.getInetAddress() + ":" + socket.getLocalPort());
                BufferedReader bufferedReader = Utilities.getReader(socket);
                String line = bufferedReader.readLine();
                Log.v(Constants.TAG, "A line has been received from the FTP server: " + line);
                if (line != null && line.startsWith(Constants.FTP_MULTILINE_START_CODE)) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (!Constants.FTP_MULTILINE_END_CODE1.equals(line) && !line.startsWith(Constants.FTP_MULTILINE_END_CODE2)) {
                            Log.v(Constants.TAG, "A line has been received from the FTP server: " + line);
                            publishProgress(line);
                        } else {
                            break;
                        }
                    }
                }
            } catch (UnknownHostException unknownHostException) {
                Log.d(Constants.TAG, unknownHostException.getMessage());
                if (Constants.DEBUG) {
                    unknownHostException.printStackTrace();
                }
            } catch (IOException ioException) {
                Log.d(Constants.TAG, ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException ioException) {
                    Log.d(Constants.TAG, ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
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
            welcomeMessageTextView.append(progress[0] + "\n");
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
