package com.example.n00465408.midtermapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class EnterContactInfo extends AppCompatActivity
{

    private Button saveButton;
    public static String name;
    private String mFilename = "nameFile.json";
    private ArrayList<String> todoItems = new ArrayList<String>();
    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact_info);


        final EditText mEditText = (EditText) findViewById(R.id.name);
        mEditText.setOnKeyListener(new View.OnKeyListener()
           {
               @Override
               public boolean onKey(View v, int keyCode, KeyEvent event)
               {
                   if (event.getAction() == KeyEvent.ACTION_DOWN)
                       if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                               (keyCode == KeyEvent.KEYCODE_ENTER))
                       {
                           name = mEditText.getText().toString();
                           //todoItems.add(0, name);
                           //aa.notifyDataSetChanged();
                           mEditText.setText("");
                           return true;
                       }
                   return false;
               }
           }

        );

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                todoItems.add(0, name);
                saveToDoItems();
                Intent i = new Intent(EnterContactInfo.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void saveToDoItems()
    {
        JSONArray array = new JSONArray(todoItems);
        Writer writer = null;
        try
        {
            OutputStream out = openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            writer.close();
        } catch (IOException e)
        {
            Log.d("Writer", "IO exception:", e);
        }
    }

}
