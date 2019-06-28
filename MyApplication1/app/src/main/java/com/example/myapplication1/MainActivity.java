package com.example.myapplication1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.util.Objects.isNull;

public class MainActivity extends AppCompatActivity {
    static class StudentsProviderFromMyApllication2{
        static final String _ID = "_id";
        static final String NAME = "name";
        static final String GRADE = "grade";
        static final String PROVIDER_NAME = "com.example.myapplication2.StudentsProvider";
        static final String TABLE_NAME = "students";
        static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
        static final Uri CONTENT_URI = Uri.parse(URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickAddName(View view) {
        String editText1 = ((EditText)findViewById(R.id.editText1)).getText().toString();
        if( !editText1.equals("") ){
        //if( editText1.isEmpty() ){
            ContentValues values = new ContentValues();
            values.put(StudentsProviderFromMyApllication2.NAME, ((EditText)findViewById(R.id.editText1)).getText().toString() );
            values.put(StudentsProviderFromMyApllication2.GRADE, 0);
            Uri uri = getContentResolver().insert(StudentsProviderFromMyApllication2.CONTENT_URI, values);
            ((EditText)findViewById(R.id.editText1)).setText("");
            Toast.makeText(getBaseContext(),uri.toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(),"name cannot be empty", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickRetrieveStudents(View view) {
        String url = "content://" + StudentsProviderFromMyApllication2.PROVIDER_NAME;
        Uri contentProvider = Uri.parse(url);
        //Cursor c = managedQuery(students, null, null, null, "name");
        ContentResolver cr = getContentResolver();
        Cursor c = getContentResolver().query(contentProvider, null, null, null, "name");
        if(c!=null){
            if (c.moveToFirst()) {
                String students = "";
                do{
                    String student = c.getString(c.getColumnIndex(StudentsProviderFromMyApllication2._ID)) +
                            ", " +  c.getString(c.getColumnIndex( StudentsProviderFromMyApllication2.NAME)) +
                            ", " + c.getString(c.getColumnIndex( StudentsProviderFromMyApllication2.GRADE)) + ".\n";
                    students += student;
                    Toast.makeText(this, student, Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
                TextView textView1 = (TextView) findViewById(R.id.textView1);
                textView1.setText(students);
            }
        }else{
            TextView textView1 = (TextView) findViewById(R.id.textView1);
            textView1.setText("Cursor is null");
        }

    }
}
