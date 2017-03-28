package ir.toptechnica.mysms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

import ir.toptechnica.mysms.dataModel.Message;

/**
 * Created by admin1 on 3/15/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    Message mMessage;

    /**
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";

        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                mMessage = new Message();
                mMessage.setAddress(messages[i].getOriginatingAddress());
                mMessage.setBody(messages[i].getMessageBody());
                mMessage.setDate(messages[i].getTimestampMillis());

                strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                strMessage += "\n";

            }

            // Prepare intent which is triggered if the
            // notification is selected
            Intent mintent = new Intent(context, NotifActivity.class);

            // use System.currentTimeMillis() to have a unique ID for the pending intent
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("message", strMessage);
            PendingIntent prevPendingIntent =
                    PendingIntent.getActivity(context, (int) System.currentTimeMillis(), mintent, 0);

            NotificationManager notif=
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Previous", prevPendingIntent).build();

            Notification notify= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                notify = new Notification.Builder
                        (context.getApplicationContext())
                        .setContentTitle("SMS")
                        .setContentText(strMessage)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(prevPendingIntent)
                        .addAction(R.mipmap.ic_launcher,"Call",prevPendingIntent)
                        .build();
            }

            // hide the notification after its selected
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);

        }


    }
}
