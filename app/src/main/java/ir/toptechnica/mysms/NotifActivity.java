package ir.toptechnica.mysms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotifActivity extends AppCompatActivity {

    TextView textView;
    String imessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        if (getIntent()!=null) {
            imessage = getIntent().getStringExtra("message");
            textView.setText(imessage);
        }

    }

}
