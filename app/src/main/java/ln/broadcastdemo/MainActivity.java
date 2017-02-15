package ln.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = (TextView) findViewById(R.id.txt_status);

        txtStatus.setText("Demo");

        internetCheck(this);

        registerReceivers();

    }

    public void internetCheck(Context context)
    {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        String connectionInfo = String.valueOf(isConnected);

        /**
         * to check connection type

        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
         Log.v("status",String.valueOf(isWiFi));
         */

        if (connectionInfo.equals("true"))
        {
            txtStatus.setTextColor(Color.RED);
            txtStatus.setText(R.string.internet_is_on);
        }
        else {
            txtStatus.setTextColor(Color.BLUE);
            txtStatus.setText(R.string.internet_is_off);
        }

    }

    private void registerReceivers() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                internetCheck(context);

            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("activity","onStop");
        if(broadcastReceiver!=null) {
            unRegisterReceivers();
            broadcastReceiver = null;
        }
    }

    private void unRegisterReceivers() {
        unregisterReceiver(broadcastReceiver);
    }
}
