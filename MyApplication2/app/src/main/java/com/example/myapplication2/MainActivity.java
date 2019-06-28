package com.example.myapplication2;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickAddName(View view) { // Add Name#button2 is clicked
        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME, ((EditText)findViewById(R.id.editText2)).getText().toString());
        values.put(StudentsProvider.GRADE, ((EditText)findViewById(R.id.editText3)).getText().toString());
        Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(),uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveStudents(View view) { // Retrive student#button is clicked
        String URL = "content://com.example.myapplication2.StudentsProvider";
        Uri students = Uri.parse(URL);
        //Cursor c = managedQuery(students, null, null, null, "name");
        Cursor c = getContentResolver().query(students, null, null, null, "name");
        if (c.moveToFirst()) {
            String strStudents = "";
            do{
                String strStudent = c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                        ", " +  c.getString(c.getColumnIndex( StudentsProvider.NAME)) +
                        ", " + c.getString(c.getColumnIndex( StudentsProvider.GRADE)) + ".\n";
                strStudents += strStudent;
                Toast.makeText(this, strStudent, Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setText(strStudents);
        }
    }
}
