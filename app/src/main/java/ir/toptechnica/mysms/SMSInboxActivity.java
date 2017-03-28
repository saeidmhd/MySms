package ir.toptechnica.mysms;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SMSInboxActivity extends AppCompatActivity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsinbox);

        ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);

        if(fetchInbox()!=null)
        {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
    }

    public ArrayList fetchInbox()
    {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

           /* System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);*/

            sms.add(""+address+ "\n"+""+ "\n" +body);
        }
        return sms;

    }
}
