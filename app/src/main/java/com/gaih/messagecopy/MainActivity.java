package com.gaih.messagecopy;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){

        try {
            XmlSerializer serializer = Xml.newSerializer();
            File file = new File(Environment.getExternalStorageDirectory().getPath(),"smsback.xml");
            FileOutputStream fos = new FileOutputStream(file);
            serializer.setOutput(fos,"utf-8");
            serializer.startDocument("utf-8",true);
            serializer.startTag(null,"smss");

            Uri uri = Uri.parse("content://sms/");

            Cursor cursor = getContentResolver().query(uri, new String[]{"address", "date", "body"}, null, null, null);
            while (cursor.moveToNext()){
                String address = cursor.getString(0);
                String date = cursor.getString(1);
                String body = cursor.getString(2);

                serializer.startTag(null,"sms");

                serializer.startTag(null,"address");
                serializer.text(address);
                serializer.endTag(null,"address");

                serializer.startTag(null,"date");
                serializer.text(date);
                serializer.endTag(null,"date");

                serializer.startTag(null,"body");
                serializer.text(body);
                serializer.endTag(null,"body");

                serializer.endTag(null,"sms");

                Log.d("电话"+address,"date"+date+"body"+body);
            }

            serializer.endTag(null,"smss");
            serializer.endDocument();
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
